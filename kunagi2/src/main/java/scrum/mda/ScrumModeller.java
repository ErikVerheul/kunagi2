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
package scrum.mda;

import ilarkesto.base.StrExtend;
import ilarkesto.core.scope.Scope;
import ilarkesto.core.time.Date;
import ilarkesto.core.time.DateAndTime;
import ilarkesto.core.time.Time;
import ilarkesto.di.app.ApplicationStarter;
import ilarkesto.logging.Log;
import ilarkesto.mda.legacy.model.EntityModel;
import ilarkesto.mda.legacy.model.PropertyModel;
import ilarkesto.mda.model.CsvFileModelSource;
import ilarkesto.mda.model.Model;
import ilarkesto.mda.model.ModelProcessor;
import ilarkesto.mda.model.ModellingSession;
import ilarkesto.mda.model.Node;
import ilarkesto.mda.model.NodeTypes;
import ilarkesto.mda.swingeditor.Starter;
import ilarkesto.mda.swingeditor.Workspace;
import java.io.File;
import java.util.List;

/**
 *
 * @author erik
 */
public class ScrumModeller extends Starter {

	private static final Log log = Log.get(ScrumModeller.class);

	private static ScrumModelApplication scrumModelApplication;

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
		scrumModelApplication = ApplicationStarter.startApplication(ScrumModelApplication.class);

		Scope scope = createModellerScope();

		ModellingSession modellingSession = scope.getComponent(ModellingSession.class);
		modellingSession.addProcessor(new LegacyGeneration());
		// modellingSession.addProcessor(new TextGeneration());
		modellingSession.load(new CsvFileModelSource(new File("src/model.csv")));
		appendLegacy(modellingSession.getModel());

		scope.getComponent(Workspace.class).showJFrame();
	}

	private static void appendLegacy(Model model) {
		Node nRoot = model.getRoot();
		Node nScrum = nRoot.getChildOrCreate(NodeTypes.GwtModule, "Scrum");
		for (EntityModel entity : scrumModelApplication.getEntityModels(false)) {
			if (!entity.isGwtSupport()) {
                            continue;
            }
			log.info(entity.getName());
			String packageName = StrExtend.removePrefix(entity.getPackageName(), "scrum.server.");
			Node nPackage = nScrum.getChildOrCreate(NodeTypes.Package, packageName);
			Node nEntity = nPackage.getChildOrCreate("Entity", entity.getName());
			for (PropertyModel property : entity.getProperties()) {
				Node nProperty;
				if (property.isReference()) {
					nProperty = nEntity.getChildOrCreate(NodeTypes.ReferenceProperty, property.getName());
				} else if (property.getType().equals(String.class.getName())) {
					nProperty = nEntity.getChildOrCreate(NodeTypes.TextProperty, property.getName());
				} else if (property.getType().equals(Integer.class.getName())) {
					nProperty = nEntity.getChildOrCreate(NodeTypes.IntegerProperty, property.getName());
				} else if (property.getType().equals(int.class.getName())) {
					nProperty = nEntity.getChildOrCreate(NodeTypes.IntegerProperty, property.getName());
				} else if (property.getType().equals(Float.class.getName())) {
					nProperty = nEntity.getChildOrCreate(NodeTypes.FloatProperty, property.getName());
				} else if (property.getType().equals(float.class.getName())) {
					nProperty = nEntity.getChildOrCreate(NodeTypes.FloatProperty, property.getName());
				} else if (property.getType().equals(Boolean.class.getName())) {
					nProperty = nEntity.getChildOrCreate(NodeTypes.BooleanProperty, property.getName());
				} else if (property.getType().equals(boolean.class.getName())) {
					nProperty = nEntity.getChildOrCreate(NodeTypes.BooleanProperty, property.getName());
				} else if (property.getType().equals(Date.class.getName())) {
					nProperty = nEntity.getChildOrCreate(NodeTypes.DateProperty, property.getName());
				} else if (property.getType().equals(Time.class.getName())) {
					nProperty = nEntity.getChildOrCreate(NodeTypes.TimeProperty, property.getName());
				} else if (property.getType().equals(DateAndTime.class.getName())) {
					nProperty = nEntity.getChildOrCreate(NodeTypes.DateAndTimeProperty, property.getName());
				} else {
					log.error("Unsupported type:", property.getType());
				}
			}
		}
	}

	static class LegacyGeneration implements ModelProcessor {

		@Override
		public void processModel(Model model) {
			scrumModelApplication.generateClasses().shutdown();
		}

	}

	static class TextGeneration implements ModelProcessor {

		@Override
		public void processModel(Model model) {
			List<Node> nodes = model.getRoot().getChildrenByTypeRecursive(NodeTypes.EN);
			for (Node n : nodes) {
				System.out.println("");
				System.out.println("<h2>" + StrExtend.uppercaseFirstLetter(n.getParent().getValue()) + "</h2>");
				System.out.println(n.getValue());
			}

		}

	}

}