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

import ilarkesto.auth.AUser;
import ilarkesto.base.Iconized;
import ilarkesto.core.base.KunagiProperties;
import ilarkesto.core.time.DateAndTime;
import static ilarkesto.core.time.DateAndTime.now;
import ilarkesto.id.Identifiable;
import static java.util.UUID.randomUUID;

/**
 *
 * @author erik
 */
public abstract class AEntity extends ADatob implements Identifiable, Iconized {

    private static final long serialVersionUID = 1L;

    private static DaoService daoService;

    private String id;
    private DateAndTime lastModified;
    private String lastEditorId;

    /**
     *
     * @return
     */
    public abstract ADao getDao();

// --- dependencies ---
    /**
     *
     * @return
     */
    public static DaoService getDaoService() {
        return daoService;
    }

    /**
     *
     * @param daoService
     */
    public static void setDaoService(DaoService daoService) {
        AEntity.daoService = daoService;
    }

// --- ---
    /**
     *
     * @return
     */
    @Override
    protected final ADao<AEntity> getManager() {
        return getDao();
    }

    /**
     *
     * @return
     */
    @Override
    public String getIcon() {
        return getDao().getIcon();
    }

    /**
     *
     * @return
     */
    @Override
    public final String getId() {
        if (id == null) {
            id = randomUUID().toString();
        }
        return id;
    }

    final void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public final DateAndTime getLastModified() {
        return lastModified;
    }

    final void setLastModified(DateAndTime value) {
        this.lastModified = value;
    }

    /**
     *
     * @return
     */
    public final AUser getLastEditor() {
        if (this.lastEditorId == null) {
            return null;
        }
        return (AUser) userDao.getById(this.lastEditorId);
    }

    /**
     *
     * @param lastEditor
     */
    public final void setLastEditor(AUser lastEditor) {
        if (isLastEditor(lastEditor)) {
            return;
        }
        this.lastEditorId = lastEditor == null ? null : lastEditor.getId();
        fireModified("lastEditor=" + lastEditor);
    }

    /**
     *
     * @param user
     * @return
     */
    public final boolean isLastEditor(AUser user) {
        if (this.lastEditorId == null && user == null) {
            return true;
        }
        return user != null && user.getId().equals(this.lastEditorId);
    }

    /**
     *
     * @return
     */
    public final boolean isLastEditorSet() {
        return lastEditorId != null;
    }

    /**
     *
     * @param comment
     */
    @Override
    protected void fireModified(String comment) {
        super.fireModified(comment);
    }

    /**
     *
     */
    @Override
    public void updateLastModified() {
        setLastModified(now());
    }

    /**
     *
     */
    @Override
    public void ensureIntegrity() {
        super.ensureIntegrity();
        if (lastModified == null) {
            fireModified("lastModified!=null");
        }
    }

    /**
     *
     * @param properties
     */
    @Override
    protected void storeProperties(KunagiProperties properties) {
        properties.putValue("id", getId());
        properties.putValue("@type", getDao().getEntityName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!getClass().equals(o.getClass())) {
            return false;
        }
        AEntity ae = (AEntity) o;
        return getId().equals(ae.getId());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + getId().hashCode();
        return hash;
    }

}
