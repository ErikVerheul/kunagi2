package ilarkesto.junit;

import static ilarkesto.base.StrExtend.getTokenAfter;
import static ilarkesto.core.base.Str.removeSuffix;
import junit.framework.TestCase;

public class StrTest extends TestCase {

	public void testRemovePostfix() {
		assertEquals("my", removeSuffix("myTest", "Test"));
		assertEquals("myTest", removeSuffix("myTest", "Tes"));
		assertEquals("myTest2", removeSuffix("myTest2", "Test"));
	}

	public void testGetTokenAfter() {
		assertEquals("result", getTokenAfter("ein test, um 'result' zu finden", " ,'", "test", 1));
	}

	// --- dependencies ---

}
