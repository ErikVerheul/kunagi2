package ilarkesto.junit.email;

import ilarkesto.email.EmailAddress;
import static ilarkesto.email.EmailAddress.formatAddress;
import junit.framework.TestCase;

/**
 *
 * @author erik
 */
public class EmailAddressTestJUnit extends TestCase {

	private static final String DUKE_ADDR = "duke@hell.org";
	private static final String DUKE_LABEL = "Duke Nukem";
	private static final String DUKE_FULL = DUKE_LABEL + " <" + DUKE_ADDR + ">";

    /**
     *
     */
    public void testPlain() {
		assertDukePlain("duke@hell.org");
		assertDukePlain("Duke@Hell.org");
		assertDukePlain(" duke@hell.org ");
		assertDukePlain("<duke@hell.org>");
		assertDukePlain("<duke@hell.org> Crap");
		assertDukePlain("duke@hell.org Crap");
	}

    /**
     *
     */
    public void testFull() {
		assertDukeFull("Duke Nukem <duke@hell.org>");
		assertDukeFull("Duke Nukem <Duke@Hell.ORG>");
		assertDukeFull(" Duke Nukem < duke@hell.org > ");
		assertDukeFull("Duke Nukem<duke@hell.org>");
		assertDukeFull("\"Duke Nukem\"<duke@hell.org>");
	}

	private void assertDukePlain(String email) {
		EmailAddress emailAddress = new EmailAddress(email);
		assert emailAddress.getLabel() == null;
		assert DUKE_ADDR.equals(emailAddress.getAddress());

		assert DUKE_ADDR.equals(formatAddress(email, false));
	}

	private void assertDukeFull(String email) {
		// System.out.println(email + "|" + EmailAddress.formatAddress(email, false) + "|");

		EmailAddress emailAddress = new EmailAddress(email);
		assert DUKE_LABEL.equals(emailAddress.getLabel());
		assert DUKE_ADDR.equals(emailAddress.getAddress());

		assert DUKE_FULL.equals(formatAddress(email, false));
	}

}
