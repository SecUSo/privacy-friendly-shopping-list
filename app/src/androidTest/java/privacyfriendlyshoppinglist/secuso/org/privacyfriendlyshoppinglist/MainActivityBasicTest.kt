package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.MainActivity

@RunWith(AndroidJUnit4::class)
class MainActivityBasicTest {
    @get: Rule
    var mActivityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun launchActivity() {
    }

    @Test
    fun openDrawer() {
        onView(withContentDescription(R.string.navigation_drawer_open)).perform(click())
    }
}