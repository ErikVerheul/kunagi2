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
package scrum.client.journal;

import static ilarkesto.core.logging.ClientLog.DEBUG;
import static ilarkesto.core.logging.ClientLog.INFO;
import java.util.List;
import scrum.client.common.AScrumGwtEntity;

/**
 *
 * @author erik
 */
public class ChangeHistoryManager extends GChangeHistoryManager {

	private AScrumGwtEntity currentChangeHistoryParent;

    /**
     *
     * @param entity
     */
    public void activateChangeHistory(AScrumGwtEntity entity) {
		if (currentChangeHistoryParent == entity) {
			DEBUG("ChangeHistory already active for", entity);
			return;
		}
		deactivateChangeHistory();
		currentChangeHistoryParent = entity;
		INFO("ChangeHistory activated for", entity);
		if (currentChangeHistoryParent != null) {
                    new RequestChangesServiceCall(currentChangeHistoryParent.getId()).execute();
        }
	}

    /**
     *
     */
    public void deactivateChangeHistory() {
		currentChangeHistoryParent = null;
		// dao.clearChanges();
	}

    /**
     *
     * @param entity
     * @return
     */
    public boolean isChangeHistoryActive(AScrumGwtEntity entity) {
		return currentChangeHistoryParent == entity;
	}

    /**
     *
     * @param entityId
     * @return
     */
    public boolean isChangeHistoryActive(String entityId) {
		if (currentChangeHistoryParent == null) {
                    return false;
        }
		return currentChangeHistoryParent.getId().equals(entityId);
	}

    /**
     *
     * @param entity
     * @return
     */
    public List<Change> getChanges(AScrumGwtEntity entity) {
		return dao.getChangesByParent(entity);
	}

}
