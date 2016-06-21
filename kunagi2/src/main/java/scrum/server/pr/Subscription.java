package scrum.server.pr;

import ilarkesto.auth.Auth;
import ilarkesto.core.time.TimePeriod;
import scrum.server.admin.User;

public class Subscription extends GSubscription {

	private static final TimePeriod TTL = TimePeriod.hours(12);

	@Override
	public boolean isVisibleFor(User user) {
		return Auth.isVisible(getSubject(), user);
	}

	@Override
	public void ensureIntegrity() {
		super.ensureIntegrity();
		if (isSubscribersEmailsEmpty() && getLastModified().getPeriodToNow().isGreaterThen(TTL)) {
			getDao().deleteEntity(this);
		}
	}

}