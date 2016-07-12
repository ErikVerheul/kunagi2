// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.search;

public class SearchResultsChangedEvent extends ilarkesto.core.event.AEvent {

    public  SearchResultsChangedEvent() {
    }

    public void tryToGetHandled(Object handler) {
        if (handler instanceof SearchResultsChangedHandler) {
            ((SearchResultsChangedHandler)handler).onSearchResultsChanged(this);
        }
    }

}

