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
package ilarkesto.persistence;

import ilarkesto.auth.AUserDao;
import ilarkesto.base.OverrideExpectedException;
import ilarkesto.core.base.KunagiProperties;
import ilarkesto.core.base.Str;
import ilarkesto.search.Searchable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Base class for classes with persistent data.
 */
public abstract class ADatob implements Searchable {

    // --- dependencies ---
    /**
     *
     */
    protected static AUserDao userDao;

    /**
     *
     * @param userDao
     */
    public static void setUserDao(AUserDao userDao) {
        ADatob.userDao = userDao;
    }

    // --- ---
    
    /**
     *
     * @return
     */
    protected abstract ADatobManager getManager();
    
    /**
     *
     * @param properties
     */
    public abstract void updateProperties(KunagiProperties properties);

    /**
     *
     */
    protected void updateLastModified() {
        ADatobManager manager = getManager();
        if (manager == null) {
            return;
        }
        manager.updateLastModified(this);
    }

    /**
     *
     * @param comment
     */
    protected void fireModified(String comment) {
        ADatobManager manager = getManager();
        if (manager == null) {
            return;
        }
        manager.onDatobModified(this, comment);
    }

    /**
     *
     */
    protected final void repairMissingMaster() {
        ADatobManager manager = getManager();
        if (manager == null) {
            return;
        }
        manager.onMissingMaster(this);
    }

    /**
     *
     * @param key
     * @return
     */
    @Override
    public boolean matchesKey(String key) {
        return false;
    }

    /**
     *
     * @param entityId
     */
    protected void repairDeadReferences(String entityId) {
    }

    /**
     *
     */
    public void ensureIntegrity() {
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    // --- properties as map ---
    /**
     *
     * @return
     */
    public final KunagiProperties createPropertiesMap() {
        KunagiProperties properties = new KunagiProperties();
        storeProperties(properties);
        return properties;
    }

    /**
     *
     * @param properties
     */
    protected void storeProperties(KunagiProperties properties) {
        properties.putValue("@type", Str.getSimpleName(getClass()));
    }

    /**
     *
     * @param valueObjects
     * @param entityId
     */
    protected static void repairDeadReferencesOfValueObjects(Collection<? extends ADatob> valueObjects, String entityId) {
        for (ADatob vo : valueObjects) {
            vo.repairDeadReferences(entityId);
        }
    }

    /**
     *
     * @param <S>
     * @param strucktures
     * @param manager
     * @return
     */
    protected final <S extends AStructure> Set<S> cloneValueObjects(Collection<S> strucktures,
            StructureManager<S> manager) {
        Set<S> ret = new HashSet<>();
        for (S s : strucktures) {
            ret.add((S) s.clone(manager));
        }
        return ret;
    }

    /**
     *
     * @param entities
     * @return
     */
    protected static Set<String> getIdsAsSet(Collection<? extends AEntity> entities) {
        Set<String> result = new HashSet<>(entities.size());
        for (AEntity entity : entities) {
            result.add(entity.getId());
        }
        return result;
    }

    /**
     *
     * @param entities
     * @return
     */
    protected static List<String> getIdsAsList(Collection<? extends AEntity> entities) {
        List<String> result = new ArrayList<>(entities.size());
        for (AEntity entity : entities) {
            result.add(entity.getId());
        }
        return result;
    }

    /**
     *
     * @param object
     * @param key
     * @return
     */
    protected static boolean matchesKey(Object object, String key) {
        if (object == null) {
            return false;
        }
        if (object instanceof Searchable) {
            return ((Searchable) object).matchesKey(key);
        }
        return object.toString().toLowerCase().indexOf(key) >= 0;
    }

    /**
     *
     * @param objects
     * @param key
     * @return
     */
    protected static boolean matchesKey(Collection objects, String key) {
        for (Iterator iter = objects.iterator(); iter.hasNext();) {
            if (matchesKey(iter.next(), key)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param datob
     */
    protected void repairDeadDatob(ADatob datob) {
        throw new OverrideExpectedException();
    }

    /**
     *
     * @param <D>
     */
    public class StructureManager<D extends ADatob> extends ADatobManager<D> {

        @Override
        public void onDatobModified(D datob, String comment) {
            fireModified(comment);
        }

        /**
         *
         * @param datob
         */
        @Override
        public void updateLastModified(D datob) {
            ADatob.this.updateLastModified();
        }

        /**
         *
         * @param datob
         */
        @Override
        public void onMissingMaster(D datob) {
            repairDeadDatob(datob);
        }

        /**
         *
         * @param structures
         */
        public void ensureIntegrityOfStructures(Collection<? extends AStructure> structures) {
            for (AStructure structure : new ArrayList<>(structures)) {
                structure.setManager(this);
                structure.ensureIntegrity();
            }
        }

    }

}
