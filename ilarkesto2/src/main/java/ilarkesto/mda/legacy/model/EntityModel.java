/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>, Artjom Kochtchi
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package ilarkesto.mda.legacy.model;

import static ilarkesto.base.StrExtend.lowercaseFirstLetter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author erik
 */
public final class EntityModel extends DatobModel {

    private final List<ActionModel> actions = new ArrayList<>();
    private boolean ownable;
    private EntityModel userModel;
    private boolean viewProtected = false;
    private boolean editProtected;
    private final List<BackReferenceModel> backReferences = new ArrayList<>();

    /**
     *
     * @param name
     * @param packageName
     */
    public EntityModel(String name, String packageName) {
        super(name, packageName);
    }

    /**
     *
     * @param backReference
     */
    public void addBackReference(BackReferenceModel backReference) {
        backReferences.add(backReference);
    }

    /**
     *
     * @return
     */
    public List<BackReferenceModel> getBackReferences() {
        return backReferences;
    }

    /**
     *
     * @return
     */
    public List<ActionModel> getActions() {
        return actions;
    }

    /**
     *
     * @param name
     * @return
     */
    public ActionModel addAction(String name) {
        ActionModel action = new ActionModel(name, getPackageName());
        action.addParameter(lowercaseFirstLetter(getName()), this);
        actions.add(action);
        return action;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isEntity() {
        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isValueObject() {
        return false;
    }

    /**
     *
     * @return
     */
    public String getDaoName() {
        return lowercaseFirstLetter(getName()) + "Dao";
    }

    /**
     *
     * @return
     */
    public String getDaoClass() {
        return getBeanClass() + "Dao";
    }

    /**
     *
     * @return
     */
    public String getAbstractBaseDaoClassName() {
        return getAbstractBaseClassName() + "Dao";
    }

    /**
     *
     * @return
     */
    public final boolean isOwnable() {
        BeanModel superbean = getSuperbean();
        if (superbean != null) {
            if (superbean instanceof EntityModel) {
                if (((EntityModel) superbean).isOwnable()) {
                    return true;
                }
            }
        }
        return ownable;
    }

    /**
     *
     * @param ownable
     */
    public final void setOwnable(boolean ownable) {
        this.ownable = ownable;
    }

    /**
     *
     * @param userModel
     */
    public void setUserModel(EntityModel userModel) {
        this.userModel = userModel;
    }

    /**
     *
     * @return
     */
    public EntityModel getUserModel() {
        return userModel;
    }

    /**
     *
     * @return
     */
    public final boolean isViewProtected() {
        BeanModel superbean = getSuperbean();
        if (superbean != null) {
            if (superbean instanceof EntityModel) {
                if (((EntityModel) superbean).isViewProtected()) {
                    return true;
                }
            }
        }
        return viewProtected;
    }

    /**
     *
     * @param viewProtected
     */
    public final void setViewProtected(boolean viewProtected) {
        this.viewProtected = viewProtected;
    }

    /**
     *
     * @return
     */
    public final boolean isEditProtected() {
        BeanModel superbean = getSuperbean();
        if (superbean != null) {
            if (superbean instanceof EntityModel) {
                if (((EntityModel) superbean).isEditProtected()) {
                    return true;
                }
            }
        }

        return editProtected;
    }

    /**
     *
     * @param editProtected
     */
    public final void setEditProtected(boolean editProtected) {
        this.editProtected = editProtected;
    }

    private boolean deleteProtected;

    /**
     *
     * @return
     */
    public final boolean isDeleteProtected() {
        BeanModel superbean = getSuperbean();
        if (superbean != null) {
            if (superbean instanceof EntityModel) {
                if (((EntityModel) superbean).isDeleteProtected()) {
                    return true;
                }
            }
        }

        return deleteProtected;
    }

    /**
     *
     * @param deleteProtected
     */
    public final void setDeleteProtected(boolean deleteProtected) {
        this.deleteProtected = deleteProtected;
    }

}
