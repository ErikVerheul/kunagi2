package ilarkesto.webapp;

import static java.lang.String.valueOf;

public class GwtConversationDoesNotExist extends Exception {
    private static final long serialVersionUID = 1L;

	public GwtConversationDoesNotExist(int conversationNumber) {
		super(valueOf(conversationNumber));
	}

}
