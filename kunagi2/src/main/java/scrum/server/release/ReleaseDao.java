
package scrum.server.release;

import ilarkesto.core.time.Date;
import ilarkesto.fp.Predicate;
import java.util.Set;
import scrum.server.project.Project;

/**
 *
 * @author erik
 */
public class ReleaseDao extends GReleaseDao {

    /**
     *
     * @param number
     * @param project
     * @return
     */
    public Release getReleaseByNumber(final int number, final Project project) {
		return getEntity(new Predicate<Release>() {

			@Override
			public boolean test(Release t) {
				return t.isNumber(number) && t.isProject(project);
			}
		});
	}

    /**
     *
     * @param project
     * @return
     */
    public Release getNextRelease(Project project) {
		Release next = null;
		Set<Release> releases = getReleasesByProject(project);
		for (Release release : releases) {
			if (release.isReleased()) {
                            continue;
            }
			if (next == null || release.getReleaseDate().isBefore(next.getReleaseDate())) {
				next = release;
			}
		}
		return next;
	}

    /**
     *
     * @param project
     * @return
     */
    public Release getCurrentRelease(Project project) {
		Release latest = null;
		Set<Release> releases = getReleasesByProject(project);
		for (Release release : releases) {
			if (!release.isReleased()) {
                            continue;
            }
			if (latest == null || release.getReleaseDate().isAfter(latest.getReleaseDate())) {
				latest = release;
			}
		}
		return latest;
	}

    /**
     *
     */
    public void resetScripts() {
		for (Release release : getEntities()) {
			release.setScriptRunning(false);
		}
	}

    /**
     *
     * @param project
     * @param releaseDate
     * @param label
     * @return
     */
    public Release postRelease(Project project, Date releaseDate, String label) {
		Release release = newEntityInstance();
		release.setProject(project);
		release.setReleaseDate(releaseDate);
		release.setLabel(label);
		saveEntity(release);
		return release;
	}
}