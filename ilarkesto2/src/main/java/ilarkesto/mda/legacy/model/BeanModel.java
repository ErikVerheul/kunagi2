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
import static java.lang.Character.isUpperCase;
import static java.lang.System.err;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 *
 */
public abstract class BeanModel extends AModel {

    private final String packageName;
    private final Set<DependencyModel> dependencies = new LinkedHashSet<>();
    private final Set<CompositeModel> composites = new LinkedHashSet<>();
    private final Set<EventModel> events = new LinkedHashSet<>();
    private final List<PredicateModel> predicates = new ArrayList<>();
    private boolean _abstract;

    private BeanModel superbean;
    private String superclass;

    /**
     *
     * @param name
     * @param packageName
     */
    public BeanModel(String name, String packageName) {
        super(name);
        this.packageName = packageName;
    }

    /**
     *
     * @param name
     * @return
     */
    public PredicateModel addPredicate(String name) {
        PredicateModel predicate = new PredicateModel(name);
        predicates.add(predicate);
        return predicate;
    }

    /**
     *
     * @return
     */
    public List<PredicateModel> getPredicates() {
        return predicates;
    }

    /**
     *
     * @return
     */
    public boolean isEntity() {
        return false;
    }

    /**
     *
     * @return
     */
    public boolean isValueObject() {
        return false;
    }

    /**
     *
     * @return
     */
    public String getBeanClass() {
        return getPackageName() + "." + getName();
    }

    /**
     *
     * @return
     */
    public Set<CompositeModel> getComposites() {
        return composites;
    }

    /**
     *
     * @param entites
     */
    public void addDaosAsComposites(Collection<EntityModel> entites) {
        for (EntityModel entity : entites) {
            addDaoAsComposite(entity);
        }
    }

    /**
     *
     * @param entity
     * @return
     */
    public CompositeModel addDaoAsComposite(EntityModel entity) {
        return addComposite(entity.getDaoClass(), entity.getDaoName());
    }

    /**
     *
     * @param bean
     * @return
     */
    public CompositeModel addComposite(BeanModel bean) {
        return addComposite(bean.getBeanClass(), bean.getName());
    }

    /**
     *
     * @param type
     * @param name
     * @return
     */
    public CompositeModel addComposite(String type, String name) {
        CompositeModel composite = new CompositeModel(type, name);
        composites.add(composite);
        return composite;
    }

    /**
     *
     * @param type
     * @return
     */
    public CompositeModel addComposite(String type) {
        if (!isUpperCase(type.charAt(0))) {
            throw new RuntimeException("Type needs to start with uppercase character: " + type);
        }
        return addComposite(type, lowercaseFirstLetter(type));
    }

    /**
     *
     * @param name
     * @return
     */
    public EventModel addEvent(String name) {
        EventModel event = new EventModel(name);
        events.add(event);
        return event;
    }

    /**
     *
     * @return
     */
    public Set<EventModel> getEvents() {
        return events;
    }

    /**
     *
     * @return
     */
    public String getAbstractBaseClassName() {
        return "G" + getName();
    }

    /**
     *
     * @return
     */
    public final String getPackageName() {
        return packageName;
    }

    /**
     *
     * @return
     */
    public final String getGwtPackageName() {
        return packageName.replace(".server", ".client");
    }

    /**
     *
     * @return
     */
    public Set<DependencyModel> getDependencies() {
        return dependencies;
    }

    private void addDependency(DependencyModel dependencyModel) {
        dependencies.add(dependencyModel);
    }

    /**
     *
     * @param name
     * @return
     */
    public boolean containsDependency(String name) {
        for (DependencyModel dm : dependencies) {
            if (dm.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param caller
     * @param type
     * @param name
     * @return
     */
    protected DependencyModel addDependency(String caller, String type, String name) {
        err.println("BeanModel:addDependency (caller, type, name): " + caller + ", " + type + ", " + name);
        DependencyModel dependencyModel = new DependencyModel(type, name);
        addDependency(dependencyModel);
        return dependencyModel;
    }

    /**
     *
     * @return
     */
    public boolean isAbstract() {
        return _abstract;
    }

    /**
     *
     * @param value
     */
    public void setAbstract(boolean value) {
        this._abstract = value;
    }

    /**
     *
     * @return
     */
    public String getSuperclass() {
        if (superbean != null) {
            return superbean.getName();
        }
        return superclass;
    }

    /**
     *
     * @return
     */
    public BeanModel getSuperbean() {
        return superbean;
    }

    /**
     *
     * @param superentity
     */
    public void setSuperbean(BeanModel superentity) {
        if (superclass != null) {
            throw new RuntimeException("superclass already set");
        }
        this.superbean = superentity;
    }

    /**
     *
     * @param superClass
     */
    public void setSuperclass(String superClass) {
        if (superbean != null) {
            throw new RuntimeException("superbean already set");
        }
        this.superclass = superClass;
    }

}
