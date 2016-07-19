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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author erik
 */
public class ActionModel extends AModel {

	private List<ParameterModel> parameters = new ArrayList<>();
	private String packageName;

    /**
     *
     * @param name
     * @param packageName
     */
    public ActionModel(String name, String packageName) {
		super(name);
		this.packageName = packageName;
	}

    /**
     *
     * @param name
     * @param type
     * @return
     */
    public ActionModel addParameter(String name, String type) {
		ParameterModel parameter = new ParameterModel(name, type);
		parameters.add(parameter);
		return this;
	}

    /**
     *
     * @return
     */
    public List<ParameterModel> getParameters() {
		return parameters;
	}

    /**
     *
     * @return
     */
    public String getPackageName() {
		return packageName;
	}

	// --- helper ---

    /**
     *
     * @param name
     * @param type
     * @return
     */
    
	public ActionModel addParameter(String name, Class type) {
		return addParameter(name, type.getName());
	}

    /**
     *
     * @param name
     * @param bean
     * @return
     */
    public ActionModel addParameter(String name, BeanModel bean) {
		return addParameter(name, bean.getBeanClass());
	}
}
