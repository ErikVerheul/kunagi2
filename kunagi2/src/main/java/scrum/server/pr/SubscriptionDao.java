package scrum.server.pr;

import ilarkesto.persistence.AEntity;

public class SubscriptionDao extends GSubscriptionDao {

	public Subscription postSubscription(AEntity subject) {
		Subscription subscription = newEntityInstance();
		subscription.setSubject(subject);

		saveEntity(subscription);

		return subscription;
	}

}