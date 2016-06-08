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
package scrum.client.issues;

import generated.scrum.client.issues.GIssue;
import com.google.gwt.user.client.ui.Widget;
import ilarkesto.core.base.Str;
import ilarkesto.core.logging.Log;
import ilarkesto.core.time.Date;
import ilarkesto.core.time.DateAndTime;
import ilarkesto.core.time.TimePeriod;
import ilarkesto.gwt.client.HyperlinkWidget;
import ilarkesto.gwt.client.LabelProvider;
import ilarkesto.gwt.client.editor.AFieldModel;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import scrum.client.ScrumGwt;
import scrum.client.admin.User;
import scrum.client.collaboration.ForumSupport;
import scrum.client.common.LabelSupport;
import scrum.client.common.ReferenceSupport;
import scrum.client.common.ShowEntityAction;
import scrum.client.common.ThemesContainer;
import scrum.client.project.Project;
import scrum.client.project.Requirement;

public class Issue extends GIssue implements ReferenceSupport, LabelSupport, ForumSupport, ThemesContainer {

	private static Log log = Log.get(Issue.class);

	public static final String INIT_TYPE = Types.ISSUE;
	public static final String REFERENCE_PREFIX = "iss";

	public static final int CRITICAL = 2;
	public static final int SEVERE = 1;
	public static final int NORMAL = 0;
	public static final int MINOR = -1;

	static final Integer[] SEVERITY_OPTIONS = { CRITICAL, SEVERE, NORMAL, MINOR };

	public Issue(Project project) {
		setType(Types.ISSUE);
		setProject(project);
		setDate(DateAndTime.now());
	}

	public Issue(Map data) {
		super(data);
	}

	@Override
	public List<String> getAvailableThemes() {
		return getProject().getThemes();
	}

	@Override
	public boolean isThemesEditable() {
		return getLabelModel().isEditable();
	}

	@Override
	public boolean isThemesCreatable() {
		return ScrumGwt.isCurrentUserProductOwner();
	}

	public List<Requirement> getRelatedRequirements() {
		return getProject().getRequirementsByThemes(getThemes());
	}

	public List<Issue> getRelatedIssues() {
		List<Issue> ret = getProject().getIssuesByThemes(getThemes());
		ret.remove(this);
		return ret;
	}

	public String getIssuer() {
		if (isCreatorSet()) return getCreator().getName();

		String name = getIssuerName();
		String email = getIssuerEmail();

		if (name == null && email == null) return null;
		if (name == null) return email;
		if (email == null) return name;

		return name + " (" + email + ")";
	}

	public boolean isSuspended() {
		Date date = getSuspendedUntilDate();
		if (date == null) return false;
		return date.isAfter(Date.today());
	}

	public void suspend(int days) {
		Date date = Date.today().addDays(days);
		log.info("Suspending for", days, "days, until", date);
		setSuspendedUntilDate(date);
	}

	public void unsuspend() {
		setSuspendedUntilDate(null);
	}

	public void appendStatement(String text) {
		String statement = getStatement();
		if (Str.isBlank(statement)) {
			setStatement(text);
			return;
		}
		setStatement(statement + "\n\n" + text);
	}

	public void claim(User user) {
		setOwner(user);
	}

	public boolean isAccepted() {
		if (isClosed()) return false;
		return getAcceptDate() != null;
	}

	public boolean isBug() {
		return isAccepted() && isUrgent();
	}

	public boolean isUnclosedBug() {
		return isBug() && !isClosed();
	}

	public boolean isIdea() {
		return isAccepted() && !isUrgent();
	}

	public boolean isOpen() {
		return !isClosed() && !isAccepted();
	}

	public boolean isClosed() {
		return getCloseDate() != null;
	}

	public String getStatusLabel() {
		if (isClosed()) {
			TimePeriod period = getCloseDate().getPeriodToToday();
			String time = period.toDays() < 1 ? "today" : period.toShortestString() + " ago";
			return "closed " + time;
		}
		if (isBug()) {
			String s = "";
			if (isFixed()) {
				s += "fixed ";
				TimePeriod period = getFixDate().getPeriodToToday();
				s += period.toDays() < 1 ? "today" : period.toShortestString() + " ago";
			} else if (isOwnerSet()) {
				s += "claimed";
			}
			if (isOwnerSet()) s += " by " + getOwner().getName();
			return s;
		}
		if (isIdea()) {
			TimePeriod period = getAcceptDate().getPeriodToToday();
			String time = period.toDays() < 1 ? "today" : period.toShortestString() + " ago";
			return "accepted " + time;
		}
		if (isSuspended()) return "suspended until " + getSuspendedUntilDate();

		String issuer = getIssuer();
		if (Str.isBlank(issuer)) issuer = "anonymous";
		return "issued " + getDate().getPeriodToNow().toShortestString() + " ago by " + issuer;
	}

	public String getThemesAsString() {
		List<String> themes = getThemes();
		Collections.sort(themes);
		return Str.concat(themes, ", ");
	}

	public void setFixed(User user) {
		setOwner(user);
		setFixDate(Date.today());
	}

	public void rejectFix() {
		setFixDate(null);
	}

	public boolean isFixed() {
		return getFixDate() != null;
	}

	public void close() {
		setOwner(null);
		setCloseDate(Date.today());
	}

	public void acceptAsIdea() {
		if (getAcceptDate() == null) setAcceptDate(Date.today());
		setUrgent(false);
	}

	public void acceptAsBug() {
		if (getAcceptDate() == null) setAcceptDate(Date.today());
		setUrgent(true);
	}

	public void reopen() {
		setAcceptDate(null);
		setUrgent(false);
		setCloseDate(null);
	}

	public String getSeverityLabel() {
		return SEVERITY_LABELS.getLabel(getSeverity());
	}

	@Override
	public List<Integer> getSeverityOptions() {
		return Arrays.asList(SEVERITY_OPTIONS);
	}

	@Override
	public String getReference() {
		return REFERENCE_PREFIX + getNumber();
	}

	public String getReferenceAndLabel() {
		return getReference() + " " + getLabel();
	}

	@Override
	public List<String> getTypeOptions() {
		return Types.ALL;
	}

	@Override
	public String toHtml() {
		return ScrumGwt.toHtml(this, getLabel());
	}

	@Override
	public String toString() {
		return getReference() + " (" + getType() + ") " + getLabel();
	}

	@Override
	public Widget createForumItemWidget() {
		return new HyperlinkWidget(new ShowEntityAction(IssueManagementWidget.class, this, getLabel()));
	}

	private AFieldModel<String> severityLabelModel;

	public AFieldModel<String> getSeverityLabelModel() {
		if (severityLabelModel == null) severityLabelModel = new AFieldModel<String>() {

			@Override
			public String getValue() {
				return getSeverityLabel();
			}
		};
		return severityLabelModel;
	}

	public static final Comparator<Issue> SEVERITY_COMPARATOR = new Comparator<Issue>() {

		@Override
		public int compare(Issue a, Issue b) {
			int aSeverity = a.getSeverity();
			int bSeverity = b.getSeverity();
			if (aSeverity == bSeverity) return ACCEPT_DATE_COMPARATOR.compare(a, b);
			return bSeverity - aSeverity;
		}
	};

	public static final Comparator<Issue> ISSUE_DATE_COMPARATOR = new Comparator<Issue>() {

		@Override
		public int compare(Issue a, Issue b) {
			return b.getDate().compareTo(a.getDate());
		}
	};

	public static final Comparator<Issue> CLOSE_DATE_COMPARATOR = new Comparator<Issue>() {

		@Override
		public int compare(Issue a, Issue b) {
			return b.getCloseDate().compareTo(a.getCloseDate());
		}
	};

	public static final Comparator<Issue> ACCEPT_DATE_COMPARATOR = new Comparator<Issue>() {

		@Override
		public int compare(Issue a, Issue b) {
			return b.getAcceptDate().compareTo(a.getAcceptDate());
		}
	};

	public final static LabelProvider<Integer> SEVERITY_LABELS = new LabelProvider<Integer>() {

		@Override
		public String getLabel(Integer severity) {
			switch (severity) {
				case 2:
					return "critical";
				case 1:
					return "severe";
				case 0:
					return "normal";
				case -1:
					return "minor ";
			}
			return String.valueOf(severity);
		}
	};

	@Deprecated
	public static class Types {

		public static final String ISSUE = "issue";
		public static final String BUG = "bug";
		public static final String REQUIREMENT = "requirement";
		public static final String QUALITY = "quality";
		public static final String IDEA = "idea";

		public static final List<String> ALL = Arrays.asList(ISSUE, BUG, REQUIREMENT, QUALITY, IDEA);
	}

	private transient AFieldModel<String> statusLabelModel;

	public AFieldModel<String> getStatusLabelModel() {
		if (statusLabelModel == null) statusLabelModel = new AFieldModel<String>() {

			@Override
			public String getValue() {
				return getStatusLabel();
			}
		};
		return statusLabelModel;
	}

	private transient AFieldModel<String> themesAsStringModel;

	public AFieldModel<String> getThemesAsStringModel() {
		if (themesAsStringModel == null) themesAsStringModel = new AFieldModel<String>() {

			@Override
			public String getValue() {
				return getThemesAsString();
			}
		};
		return themesAsStringModel;
	}
}
