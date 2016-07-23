
package scrum.client.release;

import com.google.gwt.user.client.ui.Widget;
import ilarkesto.core.KunagiProperties;
import ilarkesto.core.base.Utl;
import ilarkesto.core.time.Date;
import ilarkesto.gwt.client.HyperlinkWidget;
import ilarkesto.gwt.client.editor.AFieldModel;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import scrum.client.ScrumGwt;
import scrum.client.collaboration.ForumSupport;
import scrum.client.common.ReferenceSupport;
import scrum.client.common.ShowEntityAction;
import scrum.client.issues.Issue;
import scrum.client.project.Project;
import scrum.client.project.Requirement;
import scrum.client.sprint.Sprint;

/**
 *
 * @author erik
 */
public class Release extends GRelease implements ReferenceSupport, ForumSupport {

    /**
     *
     */
    public static final String REFERENCE_PREFIX = "rel";

    /**
     *
     * @param project
     */
    public Release(Project project) {
		setProject(project);
	}

    /**
     *
     * @param data
     */
    public Release(KunagiProperties data) {
		super(data);
	}

    /**
     *
     * @return
     */
    public boolean isMajor() {
		return !isBugfix();
	}

    /**
     *
     * @return
     */
    public boolean isBugfix() {
		return isParentReleaseSet();
	}

    /**
     *
     * @return
     */
    public List<Release> getBugfixReleases() {
		return getDao().getReleasesByParentRelease(this);
	}

    /**
     *
     * @return
     */
    public List<Issue> getAffectedByIssues() {
		List<Issue> ret = new ArrayList<Issue>();
		for (Issue issue : getDao().getIssues()) {
			if (issue.getAffectedReleases().contains(this)) {
                            ret.add(issue);
            }
		}
		return ret;
	}

    /**
     *
     * @return
     */
    public List<Issue> getFixedIssues() {
		List<Issue> ret = new ArrayList<Issue>();
		for (Issue issue : getDao().getIssues()) {
			if (issue.isClosed() && issue.containsFixRelease(this)) {
                            ret.add(issue);
            }
		}
		return ret;
	}

    /**
     *
     * @return
     */
    public List<Issue> getPlannedIssues() {
		List<Issue> ret = new ArrayList<Issue>();
		for (Issue issue : getDao().getIssues()) {
                    if (!issue.isClosed() && issue.getFixReleases().contains(this)) {
                        ret.add(issue);
            }
		}
		return ret;
	}

    /**
     *
     * @return
     */
    @Override
	public String getReference() {
		return REFERENCE_PREFIX + getNumber();
	}

    /**
     *
     * @return
     */
    @Override
	public Widget createForumItemWidget() {
		return new HyperlinkWidget(new ShowEntityAction(ReleaseManagementWidget.class, this, getLabel()));
	}

    /**
     *
     * @return
     */
    public String createIzemizedReleaseNotes() {
		StringBuilder sb = new StringBuilder();

                String releaseNotes = getReleaseNotes();
                if (releaseNotes != null) {
                    sb.append(releaseNotes).append("\n\n");
        }

		// add Stories from all Sprints that are part of this Release
		if (someSprintHasStories()) {
			sb.append("'''New Features'''\n\n");
			for (Sprint sprint : getSprints()) {
				for (Requirement story : sprint.getRequirements()) {
					sb.append("* " + (story.isClosed() ? "" : "(UNFINISHED) ")).append(story.getReferenceAndLabel())
							.append("\n");
				}
			}
			sb.append("\n\n");
		}

		// add Bugs that have been fixed for this Release
		if (!getFixedIssues().isEmpty() || !getPlannedIssues().isEmpty()) {
			sb.append("'''Fixed Bugs'''\n\n");
			for (Issue issue : getFixedIssues()) {
				sb.append("* ").append(issue.getReferenceAndLabel()).append("\n");
			}
			for (Issue issue : getPlannedIssues()) {
				sb.append("* (UNFINISHED) ").append(issue.getReferenceAndLabel()).append("\n");
			}
			sb.append("\n\n");
		}

		// add all Bugs that have not been fixed for this Release
		if (!getAffectedByIssues().isEmpty()) {
			sb.append("'''Known Issues'''\n\n");
			for (Issue issue : getAffectedByIssues()) {
				sb.append("* ").append(issue.getReferenceAndLabel() + "\n");
			}
		}

		return sb.toString();
	}

	private boolean someSprintHasStories() {
            for (Sprint sprint : getSprints()) {
			if (!sprint.getRequirements().isEmpty()) {
                return true;
            }
		}
		return false;
	}

	@Override
	public String toHtml() {
		return ScrumGwt.toHtml(this, getLabel());
	}

	@Override
	public String toString() {
		return getReference() + " " + getLabel() + "-> " + isReleased();
	}

    /**
     *
     */
    public static final Comparator<Release> DATE_COMPARATOR = new Comparator<Release>() {

		@Override
		public int compare(Release ra, Release rb) {
                    Date a = ra.getReleaseDate();
                    Date b = rb.getReleaseDate();
                    if (a == null && b == null) {
                        return Utl.compare(ra.getLabel(), rb.getLabel());
            }
			if (a == null) {
                return 1;
            }
			if (b == null) {
                return -1;
            }
			return a.compareTo(b);
		}
	};

    /**
     *
     */
    public static final Comparator<Release> DATE_REVERSE_COMPARATOR = new Comparator<Release>() {

		@Override
		public int compare(Release ra, Release rb) {
			return DATE_COMPARATOR.compare(rb, ra);
		}
	};

    /**
     *
     * @return
     */
    public List<Requirement> getRequirements() {
		List<Requirement> ret = new ArrayList<Requirement>();
		for (Sprint sprint : getSprints()) {
			ret.addAll(sprint.getRequirements());
		}
		return ret;
	}

	private transient AFieldModel<String> parentReleaseLabelModel;
        
        /**
         *
         * @return
         */
        public AFieldModel<String> getParentReleaseLabelModel() {
            if (parentReleaseLabelModel == null) {
                parentReleaseLabelModel = new AFieldModel<String>() {

			@Override
			public String getValue() {
				return isBugfix() ? "Bugfix for " + getParentRelease().getLabel() : "";
			};
		};
        }
		return parentReleaseLabelModel;
	}

}
