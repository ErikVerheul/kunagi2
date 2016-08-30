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
package ilarkesto.mda.generator;

import ilarkesto.mda.model.Node;
import ilarkesto.mda.model.NodeTypes;

/**
 *
 *
 */
public class GwtComponentBaseGenerator extends AJavaClassGenerator implements NodeTypes {

	private Node component;
	private Node package_;
	private Node gwtModule;

    /**
     *
     * @param srcPath
     * @param component
     */
    public GwtComponentBaseGenerator(String srcPath, Node component) {
		super(srcPath, true);
		this.component = component;

		package_ = component.getSuperparentByType(Package);
		assert package_ != null;
		gwtModule = package_.getSuperparentByType(GwtModule);
		assert gwtModule != null;

	}

    /**
     *
     * @param out
     */
    @Override
	protected void printCode(JavaPrinter out) {
		out.package_(getPackageName());
		out.beginClass(true, getClassName(), null, null);

		for (Node initProc : component.getChildrenByType(InitializationProcedure)) {
			printInitializationProcedure(out, initProc);
		}

		for (Node dependency : component.getChildrenByType(Dependency)) {
			printDependency(out, dependency);
		}

		out.toStringMethod('"' + component.getValue() + '"');

		out.endClass();
	}

	private void printInitializationProcedure(JavaPrinter out, Node initProc) {
		out.abstractMethod("void", initProc.getValue(), null);
	}

	private void printDependency(JavaPrinter out, Node dependency) {
		out.protectedField(getDependencyType(dependency), dependency.getValue());
	}

	private String getClassName() {
		return "G" + component.getValue();
	}

	private String getPackageName() {

		String packageName = getBasePackageName() + "." + package_.getValue();

		return packageName;
	}

	private String getBasePackageName() {
		return gwtModule.getValue().toLowerCase() + ".client";
	}
}
