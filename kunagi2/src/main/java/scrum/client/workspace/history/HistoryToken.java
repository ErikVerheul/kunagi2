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
package scrum.client.workspace.history;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import ilarkesto.core.base.Str;
import ilarkesto.core.logging.Log;
import ilarkesto.gwt.client.AGwtEntity;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HistoryToken {

	private static final Log log = Log.get(HistoryToken.class);

	public static final String START_PAGE = "Dashboard";

	private final HistoryTokenObserver observer;

	private String projectId;
	private String page;
	private String entityId;
	private boolean toggle;

	public HistoryToken(HistoryTokenObserver observer) {
		this.observer = observer;
		History.addValueChangeHandler(new TokenChangeHandler());
	}

	public void evalHistoryToken() {
		evalHistoryToken(History.getToken());
	}

	private void evalHistoryToken(String token) {
		log.debug("Evaluating history token:", token);
		Map<String, String> props = parseHistoryToken(token);

		String oldProjectId = projectId;
		String oldPage = page;
		String oldEntityId = entityId;
		boolean oldToggle = toggle;

		projectId = props.get("project");
		page = props.get("page");
		entityId = props.get("entity");
		toggle = Str.isTrue(props.get("toggle"));

		if (projectId == null || 
                        (oldProjectId == null ? projectId != null : !oldProjectId.equals(projectId))) {
			observer.onProjectChanged();
		} else if ((oldPage == null ? page != null : !oldPage.equals(page)) || 
                        (oldEntityId == null ? entityId != null : !oldEntityId.equals(entityId)) || 
                        (toggle != oldToggle)) {
			observer.onPageOrEntityChanged();
		} else {
			log.debug("Nothing changed");
		}
	}

	public static Map<String, String> parseHistoryToken(String token) {
		if (token == null || token.length() == 0) return Collections.emptyMap();
		Map<String, String> map = new HashMap<String, String>();
		char separator = '|';
		int idx = token.indexOf(separator);
		while (idx > 0) {
			String subtoken = token.substring(0, idx);
			parseHistorySubToken(subtoken, map);
			token = token.substring(idx + 1);
			idx = token.indexOf(separator);
		}
		parseHistorySubToken(token, map);
		return map;
	}

	private static void parseHistorySubToken(String token, Map<String, String> map) {
		int idx = token.indexOf('=');
		if (idx < 0) {
			map.put(token, token);
			return;
		}
		String key = token.substring(0, idx);
		String value = token.substring(idx + 1);
		map.put(key, value);
	}

	public void update(String projectId) {
		History.newItem(projectId == null ? "projectSelector" : "project=" + projectId + "|page=" + START_PAGE, true);
	}

	public void updatePage(String page) {
		History.newItem("project=" + projectId + "|page=" + page, true);
	}

	public void updatePageAndEntity(String page, AGwtEntity entity, boolean event) {
		StringBuilder sb = new StringBuilder();
		sb.append("project=").append(getProjectId());

		if (!Str.isBlank(page)) sb.append("|page=").append(page);
		this.page = page;

		if (entity != null) sb.append("|entity=").append(entity.getId());
		this.entityId = entity == null ? null : entity.getId();

		String token = sb.toString();
		if (token.equals(History.getToken())) return;
		History.newItem(token, event);
	}

	public String getProjectId() {
		return projectId;
	}

	public boolean isProjectIdSet() {
		return projectId != null;
	}

	public String getPage() {
		return page;
	}

	public String getEntityId() {
		return entityId;
	}

	public boolean isToggle() {
		return toggle;
	}

	class TokenChangeHandler implements ValueChangeHandler<String> {

		@Override
		public void onValueChange(ValueChangeEvent<String> event) {
			evalHistoryToken(event.getValue());
		}

	}

}
