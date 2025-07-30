package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.DB;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 31.05.16 creation date
 */
@RunWith(AndroidJUnit4.class)
abstract public class AbstractDatabaseTest {

    @Before
    public void setUp() throws Exception {
        // delete database before each test
        InstrumentationRegistry.getInstrumentation().getContext().deleteDatabase(DB.TEST.getDbName());
        setupBeforeEachTest();
    }

    @After
    public void tearDown() throws Exception {
        cleanAfterEachTest();
    }

    abstract protected void setupBeforeEachTest();

    protected void cleanAfterEachTest() {
    }
}
