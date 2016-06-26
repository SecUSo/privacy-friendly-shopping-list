package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.utils;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 12.06.16 creation date
 */
public class MainActivityUtils
{
    public static void replaceFragmentPlaceholder(Fragment fragment, Activity activity)
    {
        FragmentManager fragmentManager = activity.getFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_placeholder, fragment)
                .commit();
    }
}
