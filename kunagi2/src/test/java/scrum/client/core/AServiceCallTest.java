
package scrum.client.core;

import ilarkesto.core.base.KunagiProperties;
import static ilarkesto.core.logging.ClientLog.DEBUG;
import java.io.Serializable;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author Erik-local
 */
public class AServiceCallTest {

    public AServiceCallTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of execute method, of class AServiceCall.
     */
//    @Test
//    public void testExecute() {
//        System.out.println("execute");
//        AServiceCall instance = new AServiceCallImpl(entityId, properties);
//        instance.execute();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of isDispensable method, of class AServiceCall.
//     */
//    @Test
//    public void testIsDispensable() {
//        System.out.println("isDispensable");
//        AServiceCall instance = new AServiceCallImpl(entityId, properties);
//        boolean expResult = false;
//        boolean result = instance.isDispensable();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    public class AServiceCallImpl extends AServiceCall {

        private final String entityId;

        private KunagiProperties props = null;

        public AServiceCallImpl(String entityId, KunagiProperties properties) {
            this.entityId = entityId;
            this.props = properties;
        }

        @Override
        public void execute(Runnable returnHandler) {
            serviceCaller.onServiceCall(this);
            DEBUG("ConversationNumber = " + serviceCaller.getConversationNumber());
            DEBUG("entityId = " + entityId);
            DEBUG("Is properties serializable? = " + (props instanceof Serializable));
            serviceCaller.getService().changeProperties(serviceCaller.getConversationNumber(),
                    entityId,
                    props,
                    new DefaultCallback(this, returnHandler));
        }

        @Override
        public boolean isDispensable() {
            return ilarkesto.core.scope.Scope.get().getComponent(scrum.client.Dao.class).getEntity(entityId) instanceof scrum.client.admin.ProjectUserConfig && (props.containsKey("selectedEntitysIds") || props.containsKey("richtextAutosaveText") || props.containsKey("richtextAutosaveField") || props.containsKey("online"));
        }

        @Override
        public String toString() {
            return "ChangeProperties";
        }

    }

}
