package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist;

import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import org.junit.runner.RunWith;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.DB;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 31.05.16 creation date
 */
@RunWith(AndroidJUnit4.class)
abstract public class AbstractDatabaseTest extends AndroidTestCase
{

    protected void setUp() throws Exception
    {
        super.setUp();
        // delete database before each test
        getContext().deleteDatabase(DB.TEST.getDbName());
        setupBeforeEachTest();
    }

    protected void tearDown() throws Exception
    {
        super.tearDown();
        cleanAfterEachTest();
    }

    abstract protected void setupBeforeEachTest();

    protected void cleanAfterEachTest(){}
}
