package scrum.server.sprint;

import ilarkesto.base.UtlExtend;
import ilarkesto.junit.AjunitTest;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SprintHistoryHelperTest extends AjunitTest {

	@Test
	public void parseLines() {
		assertEquals(UtlExtend.toList("a"), SprintHistoryHelperExtend.parseLines("a"));
		assertEquals(UtlExtend.toList("line1", "line2"), SprintHistoryHelperExtend.parseLines("line1\nline2"));
		assertEquals(UtlExtend.toList("line1", "", "line3"), SprintHistoryHelperExtend.parseLines("line1\n\nline3"));
		assertEquals(UtlExtend.toList(""), SprintHistoryHelperExtend.parseLines(""));
	}

}
