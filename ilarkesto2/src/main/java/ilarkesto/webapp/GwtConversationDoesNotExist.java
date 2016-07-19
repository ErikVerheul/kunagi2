package ilarkesto.webapp;

import static java.lang.String.valueOf;

/**
 *
 * @author erik
 */
public class GwtConversationDoesNotExist extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     *
     * @param conversationNumber
     */
    public GwtConversationDoesNotExist(int conversationNumber) {
		super(valueOf(conversationNumber));
	}

}
