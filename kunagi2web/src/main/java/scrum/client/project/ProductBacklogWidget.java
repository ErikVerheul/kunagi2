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
package scrum.client.project;

import ilarkesto.gwt.client.AAction;
import ilarkesto.gwt.client.AFieldValueWidget;
import ilarkesto.gwt.client.AWidget;
import ilarkesto.gwt.client.ButtonWidget;
import ilarkesto.gwt.client.Gwt;
import ilarkesto.gwt.client.TableBuilder;
import ilarkesto.gwt.client.animation.AnimatingFlowPanel.InsertCallback;
import ilarkesto.gwt.client.editor.AFieldModel;
import ilarkesto.gwt.client.editor.IntegerEditorWidget;
import ilarkesto.gwt.client.editor.TextOutputWidget;

import java.util.Collections;
import java.util.List;

import scrum.client.ScrumGwt;
import scrum.client.common.AScrumWidget;
import scrum.client.common.BlockListWidget;
import scrum.client.common.UserGuideWidget;
import scrum.client.workspace.PagePanel;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class ProductBacklogWidget extends AScrumWidget {

	private BlockListWidget<Requirement> list;
	private boolean firstUpdate = true;

	private FilterToggleAction filterToggleAction;
	private SimplePanel headerWrapper;
	private Widget velocityWidget;
	private ProductBacklogFilterWidget filterWidget;

	@Override
	protected Widget onInitialization() {
		list = new BlockListWidget<Requirement>(RequirementBlock.FACTORY);
		list.setAutoSorter(getCurrentProject().getRequirementsOrderComparator());
		if (getCurrentProject().isProductOwner(getCurrentUser())) {
			list.setDndSorting(true);
			list.setMoveObserver(new MoveObserver());
		}

		headerWrapper = new SimplePanel();
		filterToggleAction = new FilterToggleAction();

		PagePanel page = new PagePanel();
		page.addHeader("Product Backlog", createCreateStoryButtons(), new ButtonWidget(filterToggleAction));
		page.addSection(headerWrapper);
		page.addSection(Gwt.createFlowPanel(list));
		page.addSection(createCreateStoryButtons());
		page.addSection(ScrumGwt.createPdfLink("Download as PDF", "productBacklog", getCurrentProject()));
		page.addSection(new UserGuideWidget(getLocalizer().views().productBacklog(), getCurrentProject()
				.getProductBacklogRequirements().size() < 5, getCurrentUser().getHideUserGuideProductBacklogModel()));

		return page;
	}

	public AWidget createCreateStoryButtons() {
		return getCurrentProject().isProductOwner(getCurrentUser()) ? new CreateStoryButtonWidget(filterToggleAction)
				: new ButtonWidget(new CreateRequirementAction(filterToggleAction));
	}

	@Override
	protected void onUpdate() {
		headerWrapper.setWidget(filterToggleAction.filterActive ? getFilterWidget() : getVelocityWidget());

		Integer velocity;
		List<Requirement> requirements = getCurrentProject().getProductBacklogRequirements();
		if (filterToggleAction.filterActive) {
			velocity = null;
			requirements = filterWidget.getRequirements();
		} else {
			velocity = getCurrentProject().getVelocity();
			requirements = getCurrentProject().getProductBacklogRequirements();
		}

		Collections.sort(requirements, getCurrentProject().getRequirementsOrderComparator());
		EstimationBarFactory.createEstimationBars(requirements, velocity);

		list.setObjects(requirements);
		if (firstUpdate) {
			getCurrentProject().updateRequirementsModificationTimes();
			firstUpdate = false;
		}
		super.onUpdate();
	}

	public boolean select(Requirement requirement) {
		if (!list.contains(requirement)) update();
		return list.showObject(requirement);
	}

	private Widget getFilterWidget() {
		if (filterWidget == null) filterWidget = new ProductBacklogFilterWidget();
		return filterWidget;
	}

	private Widget getVelocityWidget() {
		if (velocityWidget == null) {
			TableBuilder tb = ScrumGwt.createFieldTable();
			tb.addField("In current Sprint", new TextOutputWidget(new AFieldModel<String>() {

				@Override
				public String getValue() {
					Float work = getCurrentSprint().getEstimatedRequirementWork();
					return (work < 1 ? work.toString() : String.valueOf(work.intValue())) + " SP";
				}
			}));
			tb.addField("Assumed Velocity", new IntegerEditorWidget(getCurrentProject().getVelocityModel()) {

				@Override
				protected void onEditorSubmit() {
					super.onEditorSubmit();
					getCurrentProject().updateRequirementsModificationTimes();
				}

				@Override
				protected void onPlusClicked() {
					super.onPlusClicked();
					getCurrentProject().updateRequirementsModificationTimes();
				}

				@Override
				protected void onMinusClicked() {
					super.onMinusClicked();
					getCurrentProject().updateRequirementsModificationTimes();
				}

			});
			tb.addField("Velocity History", new AFieldValueWidget() {

				@Override
				protected void onUpdate() {
					setText(getCurrentProject().getVelocityFromLastSprint());
					super.onUpdate();
				}
			});
			velocityWidget = tb.createTable();
		}
		return velocityWidget;
	}

	class MoveObserver implements InsertCallback {

		@Override
		public void onInserted(int index) {
			List<Requirement> requirements = list.getObjects();
			if (filterToggleAction.filterActive) {
				Requirement requirement = requirements.get(index);
				boolean movedUp = index < filterWidget.getRequirements().indexOf(requirement);
				List<Requirement> allRequirements = getCurrentProject().getProductBacklogRequirements();
				Collections.sort(allRequirements, getCurrentProject().getRequirementsOrderComparator());
				if (movedUp) {
					Requirement next = requirements.get(index + 1);
					allRequirements.remove(requirement);
					int newIndex = allRequirements.indexOf(next);
					allRequirements.add(newIndex, requirement);
				} else {
					Requirement previous = requirements.get(index - 1);
					allRequirements.remove(requirement);
					int newIndex = allRequirements.indexOf(previous) + 1;
					allRequirements.add(newIndex, requirement);
				}
				getCurrentProject().updateRequirementsOrder(allRequirements);
			} else {
				getCurrentProject().updateRequirementsOrder(requirements);
			}
		}

	}

	static class FilterToggleAction extends AAction {

		boolean filterActive;

		@Override
		public String getLabel() {
			return filterActive ? "Deactivate Filter" : "Activate Filter";
		}

		@Override
		protected void onExecute() {
			filterActive = !filterActive;
		}

	}

}
