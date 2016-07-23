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

import com.google.gwt.user.client.rpc.IsSerializable;
import ilarkesto.core.KunagiProperties;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class is for transporting data from the scrum server to the GWT client.
 */
public abstract class ADataTransferObject implements Serializable, IsSerializable {

    /**
     *
     */
    public String entityIdBase;

    /**
     *
     */
    public Boolean developmentMode;
    private List<ErrorWrapper> errors;

    /**
     *
     */
    public Integer conversationNumber;

    private String userId;
    private Set<String> deletedEntities;
    private HashMap<String, KunagiProperties> entities;

    // dummys required for gwt-serialization
    private int dummyI;
    private Integer dummyInteger;
    private float dummyF;
    private Float dummyFloat;

    /**
     *
     */
    public void clear() {
        entities = null;
        deletedEntities = null;
    }

    /**
     *
     * @param error
     */
    public void addError(ErrorWrapper error) {
        if (errors == null) {
            errors = new ArrayList<ErrorWrapper>(1);
        }
        errors.add(error);
    }

    /**
     *
     * @return
     */
    public List<ErrorWrapper> getErrors() {
        return errors;
    }

    /**
     *
     * @param user
     */
    public void setUserId(String user) {
        this.userId = user;
    }

    /**
     *
     * @return
     */
    public String getUserId() {
        return userId;
    }

    /**
     *
     * @return
     */
    public boolean isUserSet() {
        return userId != null;
    }

    /**
     *
     * @return
     */
    public final boolean containsEntities() {
        return entities != null && !entities.isEmpty();
    }

    /**
     *
     * @param entityId
     * @return
     */
    public final boolean containsEntity(String entityId) {
        return entities.containsKey(entityId);
    }

    /**
     *
     * @param data
     */
    public final void addEntity(KunagiProperties data) {
        if (entities == null) {
                        entities = new HashMap<>();
        }
        entities.put(data.getId(), data);
    }

    /**
     *
     * @return
     */
    public final Collection<KunagiProperties> getEntities() {
        return entities.values();
    }

    /**
     *
     * @return
     */
    public final boolean containsDeletedEntities() {
        return deletedEntities != null && !deletedEntities.isEmpty();
    }

    /**
     *
     * @param entityId
     */
    public final void addDeletedEntity(String entityId) {
        if (deletedEntities == null) {
            deletedEntities = new HashSet<>();
        }
        deletedEntities.add(entityId);
    }

    /**
     *
     * @return
     */
    public final Set<String> getDeletedEntities() {
        return deletedEntities;
    }

}
