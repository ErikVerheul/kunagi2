
package scrum.server.issues;

import ilarkesto.core.time.DateAndTime;
import ilarkesto.fp.Predicate;
import java.util.Set;
import scrum.server.project.Project;

/**
 *
 * @author erik
 */
public class IssueDao extends GIssueDao {

    /**
     *
     * @param project
     * @return
     */
    public Set<Issue> getAcceptedIssues(final Project project) {
		return getEntities(new Predicate<Issue>() {

			@Override
			public boolean test(Issue issue) {
				if (!issue.isProject(project)) {
                                    return false;
                }
				if (issue.isClosed()) {
                                    return false;
                }
				return issue.isAccepted();
			}
		});
	}

    /**
     *
     * @param project
     * @return
     */
    public Set<Issue> getClosedIssues(final Project project) {
		return getEntities(new Predicate<Issue>() {

			@Override
			public boolean test(Issue issue) {
                            if (!issue.isProject(project)) {
                                return false;
                }
				return issue.isClosed();
			}
		});
	}

    /**
     *
     * @param project
     * @return
     */
    public Set<Issue> getOpenIssues(final Project project) {
		return getEntities(new Predicate<Issue>() {

			@Override
                        public boolean test(Issue issue) {
                            if (!issue.isProject(project)) {
                    return false;
                }
				return issue.isOpen();
			}
		});
	}

    /**
     *
     * @param project
     * @return
     */
    public Set<Issue> getPublishedIssues(final Project project) {
        return getEntities(new Predicate<Issue>() {
            
            @Override
			public boolean test(Issue issue) {
				if (!issue.isProject(project)) {
                    return false;
                }
				return issue.isPublished();
			}
		});
	}

    /**
     *
     * @param project
     * @return
     */
    public Set<Issue> getOpenBugs(final Project project) {
        return getEntities(new Predicate<Issue>() {

			@Override
                        public boolean test(Issue issue) {
                            if (!issue.isProject(project)) {
                    return false;
                }
				if (issue.isClosed()) {
                    return false;
                }
				return issue.isBug();
			}
		});
	}

    /**
     *
     * @param project
     * @return
     */
    public Set<Issue> getOpenIdeas(final Project project) {
        return getEntities(new Predicate<Issue>() {
            
            @Override
			public boolean test(Issue issue) {
				if (!issue.isProject(project)) {
                    return false;
                }
				if (issue.isClosed()) {
                    return false;
                }
				return issue.isIdea();
			}
		});
	}

    /**
     *
     * @param number
     * @param project
     * @return
     */
    public Issue getIssueByNumber(final int number, final Project project) {
		return getEntity(new Predicate<Issue>() {

			@Override
			public boolean test(Issue t) {
				return t.isNumber(number) && t.isProject(project);
			}
		});
	}

    /**
     *
     * @param project
     * @param label
     * @param text
     * @param issuerName
     * @param issuerEmail
     * @param publish
     * @return
     */
    public Issue postIssue(Project project, String label, String text, String issuerName, String issuerEmail,
			boolean publish) {
		Issue issue = newEntityInstance();
		issue.setProject(project);
		issue.setLabel(label);
		issue.setDescription(text);
		issue.setDate(DateAndTime.now());
		issue.setIssuerName(issuerName);
		issue.setIssuerEmail(issuerEmail);
		issue.setPublished(publish);
		issue.updateNumber();
		saveEntity(issue);
		return issue;
	}

    /**
     *
     * @param project
     * @param label
     * @return
     */
    public Issue postIssue(Project project, String label) {
		Issue issue = newEntityInstance();
		issue.setProject(project);
		issue.setLabel(label);
		saveEntity(issue);
		return issue;
	}
}