package scrum.server.sprint;

import ilarkesto.base.UtlExtend;
import ilarkesto.testng.ATest;

import org.testng.annotations.Test;

public class SprintHistoryHelperTest extends ATest {

	@Test
	public void parseLines() {
		assertEquals(SprintHistoryHelperExtend.parseLines("a"), UtlExtend.toList("a"));
		assertEquals(SprintHistoryHelperExtend.parseLines("line1\nline2"), UtlExtend.toList("line1", "line2"));
		assertEquals(SprintHistoryHelperExtend.parseLines("line1\n\nline3"), UtlExtend.toList("line1", "", "line3"));
		assertEquals(SprintHistoryHelperExtend.parseLines(""), UtlExtend.toList(""));
	}

}
