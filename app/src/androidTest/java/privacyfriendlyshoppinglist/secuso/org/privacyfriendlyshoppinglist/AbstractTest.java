package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist;

import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import org.junit.runner.RunWith;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 31.05.16 creation date
 */
@RunWith(AndroidJUnit4.class)
abstract public class AbstractTest extends AndroidTestCase
{

    protected void setUp() throws Exception
    {
        super.setUp();
        setupBeforeEachTest();
    }

    protected void tearDown() throws Exception
    {
        super.tearDown();
        cleanAfterEachTest();
    }

    abstract protected void setupBeforeEachTest();

    abstract protected void cleanAfterEachTest();
}
