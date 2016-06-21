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
package scrum.client.admin;

import ilarkesto.core.base.Str;
import ilarkesto.core.base.Utl;
import ilarkesto.core.scope.Scope;
import ilarkesto.core.time.DateAndTime;
import ilarkesto.core.time.TimePeriod;
import ilarkesto.gwt.client.editor.AFieldModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import scrum.client.common.ThemesContainer;

public class ProjectUserConfig extends GProjectUserConfig {

	private transient AFieldModel<String> themesAsStringModel;
	private transient PblFilter pblFilter = new PblFilter();

	public ProjectUserConfig() {}

	public ProjectUserConfig(Map data) {
		super(data);
	}

	public boolean isIdle() {
		if (!isOnline()) return false;
		DateAndTime time = getLastActivityDateAndTime();
		if (time == null) return true;
		TimePeriod idle = time.getPeriodToNow();
		return (idle.toSeconds() > 180);
	}

	public String getIdleTimeAsString() {
		TimePeriod idle = getIdleTime();
		return idle == null ? "-" : idle.toShortestString();
	}

	public TimePeriod getIdleTime() {
		if (!isOnline()) return null;
		DateAndTime time = getLastActivityDateAndTime();
		if (time == null) return null;
		TimePeriod idle = time.getPeriodToNow();
		if (idle.toSeconds() < 10) return null;
		return idle;
	}

	public boolean addSelectedEntityId(String entityId) {
		// List<String> ids = getSelectedEntitysIds();
		// if (ids.contains(entityId)) return false;
		// ids.add(entityId);
		// setSelectedEntitysIds(ids);
		setSelectedEntitysIds(Utl.toList(entityId));
		return true;
	}

	public boolean removeSelectedEntityId(String entityId) {
		// List<String> ids = getSelectedEntitysIds();
		// if (!ids.contains(entityId)) return false;
		// ids.remove(entityId);
		// setSelectedEntitysIds(ids);
		setSelectedEntitysIds(new ArrayList<String>(0));
		return true;
	}

	@Override
	public boolean isMisconductsEditable() {
		return getProject().isScrumMaster(Scope.get().getComponent(Auth.class).getUser());
	}

	@Override
	public String toString() {
		return getProject() + " " + getUser();
	}

	public PblFilter getPblFilter() {
		return pblFilter;
	}

	public String getPblFilterThemesAsString() {
		return Str.concat(getPblFilterThemes(), ", ");
	}

	public AFieldModel<String> getThemesAsStringModel() {
		if (themesAsStringModel == null) themesAsStringModel = new AFieldModel<String>() {

			@Override
			public String getValue() {
				return getPblFilterThemesAsString();
			}
		};
		return themesAsStringModel;
	}

	public class PblFilter implements ThemesContainer {

		@Override
		public List<String> getThemes() {
			return getPblFilterThemes();
		}

		@Override
		public void setThemes(java.util.List<String> editorSelectedItems) {
			setPblFilterThemes(editorSelectedItems);
		}

		@Override
		public List<String> getAvailableThemes() {
			return getProject().getThemes();
		}

		@Override
		public boolean isThemesEditable() {
			return true;
		}

		@Override
		public boolean isThemesCreatable() {
			return false;
		}

	}
}
