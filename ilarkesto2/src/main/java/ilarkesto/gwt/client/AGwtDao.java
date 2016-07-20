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
package ilarkesto.gwt.client;
import static ilarkesto.core.logging.ClientLog.DEBUG;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *
 * @author erik
 */
public abstract class AGwtDao extends AComponent {

	private String entityIdBase;
	private int entityIdCounter;

    /**
     *
     * @return
     */
    protected abstract Collection<Map<String, ? extends AGwtEntity>> getEntityMaps();

    /**
     *
     * @param type
     * @param data
     * @return
     */
    protected abstract AGwtEntity updateLocalEntity(String type, Map<String, String> data);

    /**
     *
     * @param entity
     */
    protected abstract void onEntityModifiedRemotely(AGwtEntity entity);

    /**
     *
     * @param entity
     */
    protected abstract void onEntityDeletedRemotely(AGwtEntity entity);

    /**
     *
     * @param entity
     * @param successAction
     */
    protected abstract void onEntityCreatedLocaly(AGwtEntity entity, Runnable successAction);

    /**
     *
     * @param entity
     */
    protected abstract void onEntityDeletedLocaly(AGwtEntity entity);

    /**
     *
     * @param entity
     * @param property
     * @param value
     */
    protected abstract void onEntityPropertyChangedLocaly(AGwtEntity entity, String property, Object value);

    /**
     *
     * @return
     */
    public abstract Map<String, Integer> getEntityCounts();

    /**
     *
     * @return
     */
    public String getEntityIdBase() {
		return entityIdBase;
	}

    /**
     *
     * @return
     */
    public int getEntityIdCounter() {
		return entityIdCounter;
	}

	String getNewEntityId() {
		if (entityIdBase == null) {
                        throw new RuntimeException("No entityIdBase received yet.");
                }
		return entityIdBase + "-" + ++entityIdCounter;
	}

    /**
     *
     * @param data
     */
    public void handleDataFromServer(ADataTransferObject data) {
		if (data.entityIdBase != null) {
			entityIdBase = data.entityIdBase;
			DEBUG("entityIdBase received:", data.entityIdBase);
		}
		List<AGwtEntity> modifiedEntities = null;
		if (data.containsEntities()) {
			modifiedEntities = new ArrayList<AGwtEntity>(data.getEntities().size());
			for (Map<String, String> entityData : data.getEntities()) {
				AGwtEntity entity = updateLocalEntity(entityData.get("@type"), entityData);
				modifiedEntities.add(entity);
			}
		}
		if (data.containsDeletedEntities()) {
			List<String> deletedEntities = new ArrayList<String>(data.getDeletedEntities());
			for (Map<String, ? extends AGwtEntity> map : getEntityMaps()) {
				for (String entityId : new ArrayList<String>(deletedEntities)) {
					AGwtEntity entity = map.remove(entityId);
					if (entity != null) {
						deletedEntities.remove(entityId);
						DEBUG("deleted:", entity.getEntityType() + ":", entity);
						onEntityDeletedRemotely(entity);
						entity.updateLocalModificationTime();
					}
				}
			}
		}
		if (modifiedEntities != null) {
			for (AGwtEntity entity : modifiedEntities) {
				onEntityModifiedRemotely(entity);
			}
		}
	}

    /**
     *
     * @param entity
     * @param successAction
     */
    protected final void entityCreated(AGwtEntity entity, Runnable successAction) {
		entity.setCreated();
		onEntityCreatedLocaly(entity, successAction);
	}

    /**
     *
     * @param entity
     */
    protected final void entityDeleted(AGwtEntity entity) {
		onEntityDeletedLocaly(entity);
		entity.updateLocalModificationTime();
	}

    /**
     *
     * @param entity
     * @param property
     * @param value
     */
    public final void entityPropertyChanged(AGwtEntity entity, String property, Object value) {
		onEntityPropertyChangedLocaly(entity, property, value);
	}

    /**
     *
     * @param id
     * @return
     * @throws EntityDoesNotExistException
     */
    public final AGwtEntity getEntity(String id) throws EntityDoesNotExistException {
		for (Map<String, ? extends AGwtEntity> entityMap : getEntityMaps()) {
			AGwtEntity entity = entityMap.get(id);
			if (entity != null) {
                                return entity;
                        }
		}
                DEBUG("EntityDoesNotExistException thrown in " + this.getClass().getName());
		throw new EntityDoesNotExistException(id);
	}

}
