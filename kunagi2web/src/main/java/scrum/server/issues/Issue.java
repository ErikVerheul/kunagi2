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
package scrum.server.issues;

import ilarkesto.base.UtlExtend;
import ilarkesto.core.time.DateAndTime;

import java.util.Comparator;
import java.util.Set;

import scrum.client.common.LabelSupport;
import scrum.client.common.ReferenceSupport;
import scrum.server.admin.User;
import scrum.server.common.Numbered;
import scrum.server.release.Release;

public class Issue extends GIssue implements Numbered, ReferenceSupport, LabelSupport {

	public void appendToStatement(String text) {
		if (!isStatementSet()) {
			setStatement(text);
			return;
		}
		setStatement(getStatement() + "\n\n" + text);
	}

	public String getStatusText() {
		String releasesText = isFixReleasesEmpty() ? "" : " for " + getFixReleasesAsString();
		if (isClosed()) return "Issue is closed" + releasesText + ".";
		if (isIdea()) return "Idea is accepted and the Product Owner needs to create a Story of it.";
		if (isBug()) {
			if (isFixed()) return "Bug is fixed" + releasesText + ". Needs to be tested.";
			if (isOwnerSet()) return getOwner().getPublicName() + " is working on the Bug" + releasesText + ".";
			return "Bug is accepted as '" + getSeverityLabel() + "' and the Team needs to fix it" + releasesText + ".";
		}
		return "Product Owner needs to review this Issue.";
	}

	public String getFixReleasesAsString() {
		Set<Release> releases = getFixReleases();
		if (releases.isEmpty()) return null;
		if (releases.size() == 1) return "Release " + UtlExtend.getElement(releases, 0).getLabel();
		StringBuilder sb = new StringBuilder();
		sb.append("Releases ");
		boolean first = true;
		for (Release release : releases) {
			if (first) {
				first = false;
			} else {
				sb.append(", ");
			}
			sb.append(release.getLabel());
		}
		return sb.toString();
	}

	public String getSeverityLabel() {
		return scrum.client.issues.Issue.SEVERITY_LABELS.getLabel(getSeverity());
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

	public boolean isBug() {
		return isAccepted() && isUrgent();
	}

	public boolean isIdea() {
		return isAccepted() && !isUrgent();
	}

	public boolean isFixed() {
		return isFixDateSet();
	}

	public boolean isOpen() {
		return !isClosed();
	}

	protected boolean isAccepted() {
		return !isClosed() && isAcceptDateSet();
	}

	@Override
	public void updateNumber() {
		if (getNumber() == 0) setNumber(getProject().generateIssueNumber());
	}

	@Override
	public String getReference() {
		return scrum.client.issues.Issue.REFERENCE_PREFIX + getNumber();
	}

	public String getReferenceAndLabel() {
		StringBuilder sb = new StringBuilder();
		sb.append(getReference());
		sb.append(" ");
		sb.append(getLabel());
		if (!isThemesEmpty()) {
			sb.append(" (").append(getThemesAsCommaSeparatedString()).append(")");
		}
		return sb.toString();
	}

	public boolean isClosed() {
		return isCloseDateSet();
	}

	@Override
	public boolean isVisibleFor(User user) {
		return getProject().isVisibleFor(user);
	}

	public boolean isEditableBy(User user) {
		return getProject().isEditableBy(user);
	}

	@Override
	public void ensureIntegrity() {
		super.ensureIntegrity();
		updateNumber();
		if (!isTypeSet()) setType(scrum.client.issues.Issue.INIT_TYPE);
		if (!isDateSet()) setDate(DateAndTime.now());
		if (isAcceptDateSet() || isCloseDateSet()) setPublished(true);
	}

	@Override
	public String toString() {
		return getReferenceAndLabel();
	}

	public static final Comparator<Issue> CLOSE_DATE_COMPARATOR = new Comparator<Issue>() {

		@Override
		public int compare(Issue a, Issue b) {
			return UtlExtend.compare(b.getCloseDate(), a.getCloseDate());
		}
	};

	public static final Comparator<Issue> SEVERITY_COMPARATOR = new Comparator<Issue>() {

		@Override
		public int compare(Issue a, Issue b) {
			int aSeverity = a.getSeverity();
			int bSeverity = b.getSeverity();
			if (aSeverity == bSeverity) return ACCEPT_DATE_COMPARATOR.compare(a, b);
			return bSeverity - aSeverity;
		}
	};

	public static final Comparator<Issue> ACCEPT_DATE_COMPARATOR = new Comparator<Issue>() {

		@Override
		public int compare(Issue a, Issue b) {
			return UtlExtend.compare(b.getAcceptDate(), a.getAcceptDate());
		}
	};

	public static final Comparator<Issue> DATE_COMPARATOR = new Comparator<Issue>() {

		@Override
		public int compare(Issue a, Issue b) {
			return UtlExtend.compare(b.getDate(), a.getDate());
		}
	};

}