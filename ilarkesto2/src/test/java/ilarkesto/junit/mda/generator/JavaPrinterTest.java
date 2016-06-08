package ilarkesto.junit.mda.generator;

import ilarkesto.mda.generator.JavaPrinter;
import static java.lang.System.err;
import static java.util.Arrays.asList;
import junit.framework.TestCase;
import org.testng.annotations.Test;

public class JavaPrinterTest extends TestCase {

	@Test
	public void test() {
		JavaPrinter out = new JavaPrinter();

		out.package_("my.test");
		out.imports(asList("java.util.*", "java.net.*"));
		out.beginClass("TestClass", "Object", null);

		out.protectedField("String", "name");

		out.endClass();

		err.println(out);
	}

}
