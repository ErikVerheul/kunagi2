package scrum.server.pr;

import generated.scrum.server.pr.GSubscriptionDao;
import ilarkesto.persistence.AEntity;

public class SubscriptionDao extends GSubscriptionDao {

	public Subscription postSubscription(AEntity subject) {
		Subscription subscription = newEntityInstance();
		subscription.setSubject(subject);

		saveEntity(subscription);

		return subscription;
	}

}