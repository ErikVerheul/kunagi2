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
package scrum.client.journal;

import ilarkesto.core.base.Str;
import ilarkesto.core.diff.HtmlDiffMarker;
import ilarkesto.core.diff.TokenDiff;
import ilarkesto.core.scope.Scope;
import ilarkesto.core.time.DateAndTime;
import ilarkesto.gwt.client.AGwtEntity;
import ilarkesto.gwt.client.EntityDoesNotExistException;

import java.util.Comparator;
import java.util.Map;

import scrum.client.ScrumGwt;
import scrum.client.admin.User;
import scrum.client.core.RequestEntityServiceCall;
import scrum.client.impediments.Impediment;
import scrum.client.issues.Issue;
import scrum.client.project.Requirement;
import scrum.client.risks.Risk;
import scrum.client.risks.RiskComputer;

public class Change extends GChange {

	public static final String CREATED = "@created";
	public static final String REQ_COMPLETED_IN_SPRINT = "@completedInSprint";
	public static final String REQ_REJECTED_IN_SPRINT = "@rejectedInSprint";

	public Change(Map data) {
		super(data);
	}

	public Change(AGwtEntity parent, String key) {
		setParent(parent);
		setKey(key);
		setDateAndTime(DateAndTime.now());
		setUser(Scope.get().getComponent(User.class));
	}

	public String getLabel() {
		String key = getKey();
		AGwtEntity parent = getParent();
		if (parent instanceof Requirement) {
			if (CREATED.equals(key) && getNewValue() != null) return "splitted story from " + getNewValue();
			if ("@split".equals(key)) return "splitted " + getNewValue();
			if (REQ_COMPLETED_IN_SPRINT.equals(key))
				return "Completed in " + getEntityReferenceAndLabel(getNewValue());
			if (REQ_REJECTED_IN_SPRINT.equals(key)) return "Rejected in " + getEntityReferenceAndLabel(getNewValue());
		}
		if (CREATED.equals(key)) return "created entity";
		if (parent instanceof Issue) {
			if (key.equals("@reply")) return "emailed a reply";
		}

		return getFieldChangeLabel();
	}

	private String getFieldChangeLabel() {
		String key = getKey();
		AGwtEntity parent = getParent();
		String oldValue = getOldValue();
		String newValue = getNewValue();

		if (parent instanceof Issue) {
			if (key.equals("closeDate")) return Str.isBlank(newValue) ? "reopened issue" : "closed issue";
			if (key.equals("storyId")) return "converted issue to story " + getEntityReferenceAndLabel(newValue);
		} else if (parent instanceof Impediment) {
			if (key.equals("closed")) return Str.isTrue(newValue) ? "closed impediment" : "reopened impediment";
		} else if (parent instanceof Requirement) {
			if (key.equals("closed")) return Str.isTrue(newValue) ? "accepted story" : "reopened story";
			if (key.equals("sprintId"))
				return newValue == null ? "kicked story from " + getEntityReferenceAndLabel(oldValue)
						: "pulled story to " + getEntityReferenceAndLabel(newValue);
			if (key.equals("issueId")) return "created story from issue " + getEntityReferenceAndLabel(newValue);
		}

		if (Str.isBlank(oldValue)) return "created " + getFieldLabel();
		if (Str.isBlank(newValue)) return "deleted " + getFieldLabel();
		return "changed " + getFieldLabel();
	}

	private String getEntityReferenceAndLabel(String id) {
		if (id == null) return null;
		try {
			return ScrumGwt.getReferenceAndLabel(getDao().getEntity(id));
		} catch (EntityDoesNotExistException ex) {
			new RequestEntityServiceCall(id).execute();
			return id;
		}
	}

	public String getDiff() {
		String key = getKey();
		AGwtEntity parent = getParent();
		String oldValue = getOldValue();
		String newValue = getNewValue();

		if (parent instanceof Issue) {
			if (key.equals("closeDate")) return null;
			if (key.equals("storyId")) return null;
			if (key.equals("@reply")) return Str.toHtml(newValue);
		} else if (parent instanceof Impediment) {
			if (key.equals("closed")) return null;
		} else if (parent instanceof Risk) {
			if (key.equals("impact"))
				return createDiff(RiskComputer.getImpactLabel(oldValue), RiskComputer.getImpactLabel(newValue));
			if (key.equals("probability"))
				return createDiff(RiskComputer.getProbabilityLabel(oldValue),
					RiskComputer.getProbabilityLabel(newValue));
		} else if (parent instanceof Requirement) {
			if (key.equals("closed")) return null;
			if (key.equals("sprintId")) return null;
			if (key.equals("issueId")) return null;
		}

		return createDiff(oldValue, newValue);
	}

	private String getFieldLabel() {
		String key = getKey();
		return key;
	}

	@Override
	public String toString() {
		String parent;
		try {
			parent = getParent().toString();
		} catch (Exception ex) {
			parent = "<?>";
		}
		return getUser() + " on " + getDateAndTime() + ": " + parent + " ." + getKey();
	}

	public static transient final Comparator<Change> DATE_AND_TIME_COMPARATOR = new Comparator<Change>() {

		@Override
		public int compare(Change a, Change b) {
			return b.getDateAndTime().compareTo(a.getDateAndTime());
		}
	};

	private static String createDiff(String oldValue, String newValue) {
		String s = TokenDiff.combinedDiff(oldValue, newValue, new HtmlDiffMarker());
		return s;
	}

	public boolean isDiffAvailable() {
		if (getParent() instanceof Requirement) {
			if (isKey(REQ_REJECTED_IN_SPRINT)) return false;
			if (isKey(REQ_COMPLETED_IN_SPRINT)) return false;
		}
		return true;
	}

}