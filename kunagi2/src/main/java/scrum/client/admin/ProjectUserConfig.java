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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import scrum.client.common.ThemesContainer;

/**
 *
 *
 */
public class ProjectUserConfig extends GProjectUserConfig {

	private transient AFieldModel<String> themesAsStringModel;
	private transient PblFilter pblFilter = new PblFilter();

    /**
     *
     */
    public ProjectUserConfig() {}

    /**
     *
     * @param data
     */
    public ProjectUserConfig(HashMap<String, Object> data) {
		super(data);
	}

    /**
     *
     * @return
     */
    public boolean isIdle() {
		if (!isOnline()) {
                    return false;
        }
		DateAndTime time = getLastActivityDateAndTime();
		if (time == null) {
                    return true;
        }
		TimePeriod idle = time.getPeriodToNow();
		return (idle.toSeconds() > 180);
	}

    /**
     *
     * @return
     */
    public String getIdleTimeAsString() {
		TimePeriod idle = getIdleTime();
		return idle == null ? "-" : idle.toShortestString();
	}

    /**
     *
     * @return
     */
    public TimePeriod getIdleTime() {
        if (!isOnline()) {
            return null;
        }
		DateAndTime time = getLastActivityDateAndTime();
                if (time == null) {
                    return null;
        }
                TimePeriod idle = time.getPeriodToNow();
                if (idle.toSeconds() < 10) {
            return null;
        }
		return idle;
	}

    /**
     *
     * @param entityId
     * @return
     */
    public boolean addSelectedEntityId(String entityId) {
		// List<String> ids = getSelectedEntitysIds();
		// if (ids.contains(entityId)) return false;
		// ids.add(entityId);
		// setSelectedEntitysIds(ids);
		setSelectedEntitysIds(Utl.toList(entityId));
		return true;
	}

    /**
     *
     * @param entityId
     * @return
     */
    public boolean removeSelectedEntityId(String entityId) {
		// List<String> ids = getSelectedEntitysIds();
		// if (!ids.contains(entityId)) return false;
		// ids.remove(entityId);
		// setSelectedEntitysIds(ids);
		setSelectedEntitysIds(new ArrayList<String>(0));
		return true;
	}

    /**
     *
     * @return
     */
    @Override
	public boolean isMisconductsEditable() {
		return getProject().isScrumMaster(Scope.get().getComponent(Auth.class).getUser());
	}

	@Override
	public String toString() {
		return getProject() + " " + getUser();
	}

    /**
     *
     * @return
     */
    public PblFilter getPblFilter() {
		return pblFilter;
	}

    /**
     *
     * @return
     */
    public String getPblFilterThemesAsString() {
		return Str.concat(getPblFilterThemes(), ", ");
	}

    /**
     *
     * @return
     */
    public AFieldModel<String> getThemesAsStringModel() {
        if (themesAsStringModel == null) {
            themesAsStringModel = new AFieldModel<String>() {
                
                @Override
                public String getValue() {
				return getPblFilterThemesAsString();
			}
		};
        }
		return themesAsStringModel;
	}

    /**
     *
     */
    public class PblFilter implements ThemesContainer {

        /**
         *
         * @return
         */
        @Override
		public List<String> getThemes() {
			return getPblFilterThemes();
		}

        /**
         *
         * @param editorSelectedItems
         */
        @Override
		public void setThemes(java.util.List<String> editorSelectedItems) {
			setPblFilterThemes(editorSelectedItems);
		}

        /**
         *
         * @return
         */
        @Override
		public List<String> getAvailableThemes() {
			return getProject().getThemes();
		}

        /**
         *
         * @return
         */
        @Override
		public boolean isThemesEditable() {
			return true;
		}

        /**
         *
         * @return
         */
        @Override
		public boolean isThemesCreatable() {
			return false;
		}

	}
}
