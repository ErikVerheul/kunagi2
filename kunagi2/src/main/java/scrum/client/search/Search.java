/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>, Artjom Kochtchi
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package scrum.client.search;

import ilarkesto.core.base.Str;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import scrum.client.common.AScrumGwtEntity;

public class Search extends GSearch implements SearchResultsChangedHandler {

	private final SearchResults results = new SearchResults();
	private SearchResultsWidget resultsWidget;
	private String searchText;

	public void search(String text) {
		if (text != null) text = text.toLowerCase();
		this.searchText = text;
		LOG.info("Searching:", searchText);
		results.clear();
		if (Str.isBlank(searchText)) return;

		new SearchServiceCall(searchText).execute(new Runnable() {

			@Override
			public void run() {
				searchClient(searchText);
				getResultsWidget().update();
			}
		});

		navigator.gotoCurrentProjectSearch();

		searchClient(searchText);
	}

	private void searchClient(String text) {
		String[] keys = parseKeys(text);

		results.addEntities(getMatching(project.getRequirements(), keys));
		results.addEntities(getMatching(project.getQualitys(), keys));
		results.addEntities(getMatching(project.getTasks(), keys));
		results.addEntities(getMatching(project.getWikipages(), keys));
		results.addEntities(getMatching(project.getFiles(), keys));
		results.addEntities(getMatching(project.getIssues(), keys));
		results.addEntities(getMatching(project.getImpediments(), keys));
		results.addEntities(getMatching(project.getRisks(), keys));

	}

	private <T extends AScrumGwtEntity> List<T> getMatching(Collection<T> entities, String[] keys) {
		List<T> ret = new ArrayList<T>();
		for (T entity : entities) {
			if (matchesKeys(entity, keys)) ret.add(entity);
		}
		return ret;
	}

	public static boolean matchesQuery(AScrumGwtEntity entity, String query) {
		return matchesKeys(entity, parseKeys(query));
	}

	public static boolean matchesKeys(AScrumGwtEntity entity, String[] keys) {
		for (String key : keys) {
			if (!entity.matchesKey(key)) return false;
		}
		return true;
	}

	public static String[] parseKeys(String text) {
		List<String> ret = new ArrayList<String>();
		char sep = ' ';
		int idx = text.indexOf(sep);
		while (idx > 0) {
			ret.add(text.substring(0, idx));
			text = text.substring(idx + 1);
			idx = text.indexOf(sep);
		}
		ret.add(text);
		return ret.toArray(new String[ret.size()]);
	}

	public SearchResults getResults() {
		return results;
	}

	@Override
	public void onSearchResultsChanged(SearchResultsChangedEvent event) {
		getResultsWidget().update();
	}

	public SearchResultsWidget getResultsWidget() {
		if (resultsWidget == null) resultsWidget = new SearchResultsWidget();
		return resultsWidget;
	}

}