package scrum.client.project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import scrum.client.ScrumGwt;

public class PlanningPokerStoryFinder {

	private final Project project;
	private final Requirement story;
	private final static int MAX_RESULTS = 3;

	public PlanningPokerStoryFinder(Requirement story) {
		this.project = story.getProject();
		this.story = story;
	}

	public List<Set<Requirement>> getByEstimation(Float estimation) {
		if (estimation < 0.5f) return Collections.emptyList();

		// other Stories with the same estimation
		List<Set<Requirement>> results = getSame(estimation);

		if (results.size() < MAX_RESULTS && estimation >= 1) {
			Float prev = ScrumGwt.getPrevEstimationValue(estimation / 2);
			Float next = ScrumGwt.getNextEstimationValue(estimation / 2);

			if (prev.equals(next)) {
				// Stories with exactly half the estimation
				List<Set<Requirement>> stories = getSame(prev);
				results.addAll(stories);
			} else {
				// pairs of Stories with equal sum of estimations
				Iterator<Set<Requirement>> prevStories = getSame(prev).iterator();
				Iterator<Set<Requirement>> nextStories = getSame(next).iterator();
				while (prevStories.hasNext() && nextStories.hasNext()) {
					Set<Requirement> set = new HashSet<Requirement>(2);
					set.addAll(prevStories.next());
					set.addAll(nextStories.next());
					results.add(set);
				}
			}
		}
		// Stories that have twice the size
		if (results.size() < MAX_RESULTS && estimation < 100) {
			Float value = ScrumGwt.getNextEstimationValue(estimation * 2);
			// Log.DEBUG(value, estimation * 2);
			if (value.equals(estimation * 2)) {
				List<Set<Requirement>> stories = getSame(value);
				// Log.DEBUG(stories);
				results.addAll(stories);
			}
		}
		return results.subList(0, Math.min(results.size(), MAX_RESULTS));
	}

	private List<Set<Requirement>> getSame(Float estimation) {
		if (estimation < 0.5f) return Collections.emptyList();
		List<Requirement> stories = project.getRequirements();
		Collections.sort(stories, project.getRequirementsOrderComparator());
		List<Set<Requirement>> results = new ArrayList<Set<Requirement>>();
		for (Requirement story : stories) {
			if (story.equals(this.story)) continue;
			if (story.isClosed()) continue;
			if (!story.isEstimatedWorkValid()) continue;
			if (!estimation.equals(story.getEstimatedWork())) continue;
			results.add(Collections.singleton(story));
		}
		return results;
	}
}
