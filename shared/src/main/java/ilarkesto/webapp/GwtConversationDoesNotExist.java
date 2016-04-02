package ilarkesto.webapp;

import static java.lang.String.valueOf;

public class GwtConversationDoesNotExist extends RuntimeException {

	public GwtConversationDoesNotExist(int conversationNumber) {
		super(valueOf(conversationNumber));
	}

}
