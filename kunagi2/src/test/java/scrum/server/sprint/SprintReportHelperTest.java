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
package scrum.server.sprint;

import ilarkesto.core.base.Utl;
import ilarkesto.testng.ATest;

import java.util.List;

import org.testng.annotations.Test;

import scrum.TestUtil;
import scrum.client.sprint.SprintHistoryHelper.StoryInfo;
import scrum.server.project.Requirement;

public class SprintReportHelperTest extends ATest {

	@Test
	public void encodeRequirement() {
		Requirement req = TestUtil.createRequirement(TestUtil.createProject(), 1);
		req.setLabel("Story ;x");
		req.setEstimatedWork(13f);
		assertEquals(SprintHistoryHelperExtend.encodeRequirement(req), "sto1;13;Story ;x");
	}

	@Test
	public void decodeRequirement() {
		String[] req = SprintHistoryHelperExtend.decodeRequirement("sto1;13;Story ;x");
		assertEquals(req, new String[] { "sto1", "13", "Story ;x" });
	}

	@Test
	public void encodeTask() {
		Requirement req = TestUtil.createRequirement(TestUtil.createProject(), 2);
		Task tsk = TestUtil.createTask(req, 1, 1, 0);
		tsk.setLabel("Task;X :-D");
		tsk.setBurnedWork(6);
		assertEquals(SprintHistoryHelperExtend.encodeTask(tsk), "tsk1;6;1;Task;X :-D");
	}

	@Test
	public void decodeTask() {
		String[] req = SprintHistoryHelperExtend.decodeTask("tsk1;6;1;Task;X :-D");
		assertEquals(req, new String[] { "tsk1", "6", "1", "Task;X :-D" });
	}

	@Test
	public void encodeAndParseAll() {
		Requirement req1 = TestUtil.createRequirement(TestUtil.createProject(), 1);
		req1.setEstimatedWork(0.5f);
		Requirement req2 = TestUtil.createRequirement(TestUtil.createProject(), 2);
		req2.setEstimatedWork(5f);
		Task tsk1 = TestUtil.createTask(req2, 1, 5, 0);
		tsk1.setRemainingWork(2);
		tsk1.setBurnedWork(3);
		Task tsk2 = TestUtil.createTask(req2, 2, 3, 0);
		tsk2.setRemainingWork(1);
		tsk2.setBurnedWork(1);
		Requirement req3 = TestUtil.createRequirement(TestUtil.createProject(), 3);
		req3.setEstimatedWork(8f);

		String encoded = SprintHistoryHelperExtend.encodeRequirementsAndTasks(Utl.toList(req1, req2, req3));
		List<StoryInfo> reqs = SprintHistoryHelperExtend.parseRequirementsAndTasks(encoded);

		assertEquals(reqs.size(), 3);
		assertEquals(reqs.get(1).getTasks().size(), 2);
	}

}
