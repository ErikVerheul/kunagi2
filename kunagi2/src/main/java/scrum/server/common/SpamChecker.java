package scrum.server.common;

import ilarkesto.webapp.RequestWrapper;
import scrum.server.WebSession;

public class SpamChecker {

	public static void check(RequestWrapper<WebSession> req) {
		String spamPreventionCode = req.get("spamPreventionCode");
		if (!"no-spam".equals(spamPreventionCode)) throw new RuntimeException("HTTP Request identified as SPAM.");
	}

}
