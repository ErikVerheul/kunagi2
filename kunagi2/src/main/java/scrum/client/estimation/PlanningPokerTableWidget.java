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
package scrum.client.estimation;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import ilarkesto.core.logging.Log;
import ilarkesto.gwt.client.Gwt;
import ilarkesto.gwt.client.HyperlinkWidget;
import ilarkesto.gwt.client.TableBuilder;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import scrum.client.ScrumGwt;
import scrum.client.admin.User;
import scrum.client.common.AScrumWidget;
import scrum.client.project.CloseRequirementEstimationVotingAction;
import scrum.client.project.PlanningPokerStoryFinder;
import scrum.client.project.Project;
import scrum.client.project.Requirement;
import scrum.client.project.RequirementEstimationVotingShowoffAction;
import scrum.client.project.ResetRequirementEstimationVotingAction;
import scrum.client.workspace.VisibleDataChangedEvent;

public class PlanningPokerTableWidget extends AScrumWidget {

	private static final Log LOG = Log.get(PlanningPokerTableWidget.class);

	private Requirement requirement;

	private SimplePanel cardSlotsWrapper;
	private SimplePanel handCardsWrapper;
	private SimplePanel actionsWrapper;
	private SimplePanel estimationHelpDisplay;

	public PlanningPokerTableWidget(Requirement requirement) {
		super();
		this.requirement = requirement;
	}

	@Override
	protected Widget onInitialization() {
		cardSlotsWrapper = new SimplePanel();
		handCardsWrapper = new SimplePanel();
		actionsWrapper = new SimplePanel();
		estimationHelpDisplay = new SimplePanel();
		estimationHelpDisplay.getElement().getStyle().setProperty("minHeight", "90px");

		FlowPanel pokerTable = new FlowPanel();
		pokerTable.setStyleName("PlanningPokerWidget-table");
		pokerTable.add(createTableBranding());
		pokerTable.add(cardSlotsWrapper);
		if (getCurrentProject().isTeamMember(getCurrentUser())) {
			pokerTable.add(Gwt.createSpacer(1, 20));
			pokerTable.add(handCardsWrapper);
		}
		pokerTable.add(Gwt.createSpacer(1, 20));
		pokerTable.add(actionsWrapper);
		pokerTable.add(Gwt.createSpacer(1, 20));
		pokerTable.add(estimationHelpDisplay);

		SimplePanel pokerTableBorder = Gwt.createDiv("PlanningPokerWidget-table-border", pokerTable);

		return pokerTableBorder;
	}

	@Override
	protected void onUpdate() {
		cardSlotsWrapper.setWidget(createCardSlots());
		handCardsWrapper.setWidget(createHandCards());
		actionsWrapper.setWidget(createActions());
		super.onUpdate();
	}

	private Widget createActions() {
		ResetRequirementEstimationVotingAction reset = new ResetRequirementEstimationVotingAction(requirement);
		RequirementEstimationVotingShowoffAction showoff = new RequirementEstimationVotingShowoffAction(requirement);
		CloseRequirementEstimationVotingAction close = new CloseRequirementEstimationVotingAction(requirement);

		TableBuilder tb = new TableBuilder();
		tb.setColumnWidths("33%", "33%", "33%");
		tb.add(new HyperlinkWidget(showoff));
		tb.add(new HyperlinkWidget(reset));
		tb.add(new HyperlinkWidget(close));
		return tb.createTable();
	}

	private Widget createHandCards() {
		final Project project = getCurrentProject();
		RequirementEstimationVote vote = requirement.getEstimationVote(getCurrentUser());
		Float voteValue = vote == null ? null : vote.getEstimatedWork();
		boolean showoff = requirement.isWorkEstimationVotingShowoff();

		TableBuilder tb = new TableBuilder();
		tb.setWidth(null);

		// ?-card
		PlanningPokerCardWidget qCard = null;
		if (!showoff && (voteValue == null || -1 != voteValue)) {
			qCard = new PlanningPokerCardWidget(-1, true, new SetEstimationClickHandler(-1),
					"Put this card on the table.");
		}
		tb.add(new PlanningPokerCardSlotWidget("No idea", qCard));
		tb.add(Gwt.createSpacer(5, 1));

		// value cards
		for (String value : Requirement.getWorkEstimationValues()) {
			if (value.length() == 0) continue;
			final float estimation = Float.parseFloat(value);
			PlanningPokerCardWidget card = null;
			if (!showoff && (voteValue == null || estimation != voteValue)) {
				card = new PlanningPokerCardWidget(estimation, true, new SetEstimationClickHandler(estimation),
						"Put this card on the table.");
				card.setMouseOverHandler(new MouseOverHandler() {

					@Override
					public void onMouseOver(MouseOverEvent event) {
						estimationHelpDisplay.setWidget(createEstimationHelp(estimation));
					}
				});
			}
			tb.add(new PlanningPokerCardSlotWidget(value + " " + project.getEffortUnit(), card));
			tb.add(Gwt.createSpacer(5, 1));
		}

		return tb.createTable();
	}

	private Widget createEstimationHelp(float estimation) {
		PlanningPokerStoryFinder finder = new PlanningPokerStoryFinder(requirement);
		List<Set<Requirement>> results = finder.getByEstimation(estimation);

		if (!results.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			sb.append("<div class='PlanningPokerTableWidget-estimationHelp'>");
			sb.append("<span>");
			sb.append("Other stuff estimated with ")
					.append(ScrumGwt.getEstimationAsString(estimation, requirement.getProject().getEffortUnit()))
					.append(":");
			sb.append("</span>");

			sb.append("<ul>");
			for (Set<Requirement> stories : results) {
				sb.append("<li>");
				Iterator<Requirement> it = stories.iterator();
				while (it.hasNext()) {
					Requirement some = it.next();
					if (Math.abs(some.getEstimatedWork() - estimation / 2) < 0.0000001) {
						sb.append("<strong>twice as much as</strong> ");
					} else if (Math.abs(some.getEstimatedWork() - estimation * 2) < 0.0000001) {
						sb.append("<strong>half as much as</strong> ");
					}
					sb.append(some.toHtml());
					if (some.getEstimatedWork() != estimation) {
						sb.append(" (").append(some.getEstimatedWorkWithUnit()).append(")");
					}
					if (it.hasNext()) sb.append(" <strong>and</strong> ");
				}
				sb.append("</li>");
			}
			sb.append("</ul></div>");
			return new HTML(sb.toString());
		}
		return new HTML();
	}

	private Widget createCardSlots() {
		TableBuilder tb = new TableBuilder();
		tb.setWidth(null);
		for (User user : getCurrentProject().getTeamMembers()) {
			boolean currentUser = user == getCurrentUser();
			RequirementEstimationVote vote = requirement.getEstimationVote(user);
			Float estimation = vote == null ? null : vote.getEstimatedWork();
			LOG.debug("Estimation:", user.getName(), "->", estimation);

			Widget card;
			if (estimation == null) {
				card = null;
			} else {
				ClickHandler clickHandler = null;
				String clickTooltip = null;
				if (requirement.isWorkEstimationVotingShowoff()) {
					clickHandler = new SelectEstimationClickHandler(estimation);
					clickTooltip = "Use this card as the estimation for this Story. Planning Poker will be closed.";
				} else if (currentUser) {
					clickHandler = new RemoveEstimationClickHandler();
					clickTooltip = "Remove this card from table.";
				}
				boolean visible = requirement.isWorkEstimationVotingShowoff();
				card = new PlanningPokerCardWidget(estimation, visible, clickHandler, clickTooltip);
			}

			tb.add(new PlanningPokerCardSlotWidget(user.getName(), card));
			tb.add(Gwt.createSpacer(5, 1));
		}
		return tb.createTable();
	}

	private Widget createTableBranding() {
		return Gwt.createDiv("PlanningPokerWidget-table-branding", "Planning Poker");
	}

	private class SelectEstimationClickHandler implements ClickHandler {

		private float estimation;

		public SelectEstimationClickHandler(float estimation) {
			super();
			this.estimation = estimation;
		}

		@Override
		public void onClick(ClickEvent event) {
			if (estimation >= 0) {
				requirement.setEstimatedWork(estimation);
				requirement.deactivateWorkEstimationVoting();
				new VisibleDataChangedEvent().fireInCurrentScope();
			}
		}
	}

	private class SetEstimationClickHandler implements ClickHandler {

		private float estimation;

		public SetEstimationClickHandler(float estimation) {
			super();
			this.estimation = estimation;
		}

		@Override
		public void onClick(ClickEvent event) {
			requirement.setVote(estimation);
			new VisibleDataChangedEvent().fireInCurrentScope();
		}
	}

	private class RemoveEstimationClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			requirement.setVote(null);
			new VisibleDataChangedEvent().fireInCurrentScope();
		}
	}
}
