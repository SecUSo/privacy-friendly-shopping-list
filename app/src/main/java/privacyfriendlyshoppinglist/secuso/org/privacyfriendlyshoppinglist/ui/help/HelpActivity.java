package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.help;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.WindowManager;

import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.baseactivity.BaseActivity;

/**
 * Created by yonjuni on 17.06.16.
 */
public class HelpActivity extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // show app without requiring user to unlock device
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        setContentView(R.layout.activity_help);
        //getFragmentManager().beginTransaction().replace(android.R.id.content, new HelpFragment()).commit();

        overridePendingTransition(0, 0);
    }

    @Override
    protected int getNavigationDrawerID()
    {
        return R.id.nav_help;
    }

    public static class HelpFragment extends PreferenceFragment
    {

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.help);
        }
    }

}