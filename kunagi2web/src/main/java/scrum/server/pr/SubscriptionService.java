/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this program. If not,
 * see <http://www.gnu.org/licenses/>.
 */
package scrum.server.pr;

import ilarkesto.auth.PasswordHasher;
import ilarkesto.concurrent.ATask;
import ilarkesto.core.base.Str;
import ilarkesto.core.logging.Log;
import ilarkesto.core.scope.In;
import ilarkesto.core.time.DateAndTime;
import ilarkesto.persistence.AEntity;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import scrum.client.common.LabelSupport;
import scrum.client.common.ReferenceSupport;
import scrum.server.admin.SystemConfig;
import scrum.server.project.Project;

public class SubscriptionService {

	private static final Log log = Log.get(SubscriptionService.class);

	@In
	private SubscriptionDao subscriptionDao;

	@In
	private EmailSender emailSender;

	@In
	private SystemConfig systemConfig;

	private List<Notification> notifications = new LinkedList<Notification>();

	public void subscribe(String email, AEntity subject) {
		if (!Str.isEmail(email)) throw new RuntimeException("Invalid email: " + email);
		email = email.toLowerCase();
		Subscription subscription = subscriptionDao.getSubscriptionBySubject(subject);
		if (subscription == null) subscription = subscriptionDao.postSubscription(subject);
		subscription.addSubscribersEmail(email);
		log.info(email, "subscribed to", subject);
	}

	public void unsubscribe(String email, AEntity subject, String key) throws InvalidKeyException {
		email = email.toLowerCase();
		if (!createKey(email).equals(key)) throw new InvalidKeyException(email);
		if (subject == null) {
			unsubscribeAll(email, key);
			return;
		}
		Subscription subscription = subscriptionDao.getSubscriptionBySubject(subject);
		if (subscription == null || !subscription.containsSubscribersEmail(email)) {
			log.debug(email, "is not subscribed to", subject);
			return;
		}
		subscription.removeSubscribersEmail(email);
		log.info(email, "unsubscribed from", subject);
	}

	private void unsubscribeAll(String email, String key) {
		email = email.toLowerCase();
		Set<Subscription> subscriptions = subscriptionDao.getSubscriptionsBySubscribersEmail(email);
		if (subscriptions.isEmpty()) {
			log.debug(email, "is not subscribed to anything");
			return;
		}
		for (Subscription subscription : subscriptions) {
			subscription.removeSubscribersEmail(email);
		}
		log.info(email, "unsubscribed from", subscriptions.size(), "entities");
	}

	public void notifySubscribers(AEntity subject, String message, Project project, String exceptionEmail) {
		Subscription subscription = subscriptionDao.getSubscriptionBySubject(subject);
		if (subscription == null || subscription.isSubscribersEmailsEmpty()) {
			log.debug("No subscribers for", subject);
			return;
		}

		Set<String> subscribersEmails = subscription.getSubscribersEmails();
		if (exceptionEmail != null) subscribersEmails.remove(exceptionEmail.toLowerCase());
		if (subscribersEmails.isEmpty()) {
			log.debug("No subscribers for", subject);
			return;
		}

		synchronized (notifications) {
			for (Notification notification : notifications) {
				if (!notification.subject.equals(subject)) continue;
				notification.merge(message, subscribersEmails);
				return;
			}
			notifications.add(new Notification(subject, message, project, subscribersEmails));
		}
	}

	public void processNotifications() {
		processNotifications(false);
	}

	public void flush() {
		processNotifications(true);
	}

	private void processNotifications(boolean ignoreActionTime) {
		int total = 0;
		int count = 0;
		synchronized (notifications) {
			if (notifications.isEmpty()) return;
			total = notifications.size();
			Iterator<Notification> iterator = notifications.iterator();
			while (iterator.hasNext()) {
				Notification notification = iterator.next();
				if (!ignoreActionTime && notification.actionTime.isFuture()) {
					log.debug("Notification pending until",
						notification.actionTime.getPeriodToNow().toShortestString(), "->", notification);
					continue;
				}
				sendEmails(notification);
				iterator.remove();
				count++;
			}
		}
		log.info(count, "of", total, "notifications sent to subscribers");
	}

	private void sendEmails(Notification notification) {
		String subjectText = EmailHelper.createSubject(notification.project, notification.subject.toString());
		for (String email : notification.emails) {
			String text = createText(email, notification);
			emailSender.sendEmail(notification.project, email, subjectText, text);
		}
	}

	public void copySubscribers(AEntity from, AEntity to) {
		Subscription fromSubscription = subscriptionDao.getSubscriptionBySubject(from);
		if (fromSubscription == null || fromSubscription.isSubscribersEmailsEmpty()) return;
		Subscription toSubscription = subscriptionDao.getSubscriptionBySubject(to);
		if (toSubscription == null) toSubscription = subscriptionDao.postSubscription(to);
		toSubscription.addSubscribersEmails(fromSubscription.getSubscribersEmails());
	}

	private String createText(String email, Notification notification) {
		String text = notification.project.getSubscriberNotificationTemplate();
		if (notification.subject instanceof ReferenceSupport) {
			text = text.replace("${entity.reference}", ((ReferenceSupport) notification.subject).getReference());
		}
		if (notification.subject instanceof LabelSupport) {
			text = text.replace("${entity.label}", ((LabelSupport) notification.subject).getLabel());
		}
		text = text.replace("${change.message}", notification.message);
		text = text.replace("${project.label}", notification.project.getLabel());
		text = text.replace("${project.id}", notification.project.getId());
		if (notification.project.isProductLabelSet())
			text = text.replace("${product.label}", notification.project.getProductLabel());
		if (notification.project.isHomepageUrlSet())
			text = text.replace("${homepage.url}", notification.project.getHomepageUrl());
		text = text.replace("${unsubscribe.url}", createUnsubscribeUrl(email, notification));
		text = text.replace("${unsubscribeall.url}", createUnsubscribeUrl(email, notification));
		text = text.replace("${kunagi.instance}", systemConfig.getInstanceNameWithApplicationLabel());
		text = text.replace("${kunagi.url}", systemConfig.getUrl());
		return text;
	}

	private String createUnsubscribeUrl(String email, Notification notification) {
		StringBuilder sb = new StringBuilder();
		String baseUrl = systemConfig.getUrl();
		sb.append(baseUrl);
		if (!baseUrl.endsWith("/")) sb.append("/");
		sb.append("unsubscribe");
		sb.append("?email=").append(Str.encodeUrlParameter(email));
		if (notification.subject != null)
			sb.append("&subject=").append(Str.encodeUrlParameter(notification.subject.getId()));
		sb.append("&key=").append(Str.encodeUrlParameter(createKey(email)));
		return sb.toString();
	}

	private String createKey(String email) {
		return PasswordHasher.hashPassword(email, systemConfig.getSubscriptionKeySeed());
	}

	public class Task extends ATask {

		@Override
		protected void perform() throws InterruptedException {
			processNotifications();
		}

	}

	private static class Notification {

		private AEntity subject;
		private String message;
		private Project project;
		private Set<String> emails;
		private DateAndTime actionTime;

		public Notification(AEntity subject, String message, Project project, Set<String> emails) {
			super();
			this.subject = subject;
			this.message = message;
			this.project = project;
			this.emails = emails;
			updateActionTime();
		}

		public void merge(String newMessage, Set<String> newEmails) {
			emails.addAll(newEmails);
			message += "; " + newMessage;
			updateActionTime();
		}

		private void updateActionTime() {
			actionTime = DateAndTime.now().addMinutes(10);
		}

		@Override
		public String toString() {
			return message + " -> " + Str.concat(emails, ", ");
		}
	}
}
