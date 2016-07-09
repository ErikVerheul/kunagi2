// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.search;

import static ilarkesto.core.logging.ClientLog.DEBUG;

public class SearchResultsChangedEvent extends ilarkesto.core.event.AEvent {

    public  SearchResultsChangedEvent() {
    }

    public void tryToGetHandled(Object handler) {
        if (handler instanceof SearchResultsChangedHandler) {
            DEBUG("    " + handler.getClass().getName() + ".onSearchResultsChanged(event)");
            ((SearchResultsChangedHandler)handler).onSearchResultsChanged(this);
        }
    }

}

