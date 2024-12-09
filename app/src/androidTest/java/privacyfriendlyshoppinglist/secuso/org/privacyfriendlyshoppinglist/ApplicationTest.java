package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist;

import static org.junit.Assert.assertEquals;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
@RunWith(AndroidJUnit4.class)
public class ApplicationTest {
    @Test
    public void useApplicationContext() {
        assertEquals("privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist", ApplicationProvider.getApplicationContext().getPackageName());
    }

    @Test
    public void useContext() {
        assertEquals("privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.test", InstrumentationRegistry.getInstrumentation().getContext().getPackageName());
    }
}