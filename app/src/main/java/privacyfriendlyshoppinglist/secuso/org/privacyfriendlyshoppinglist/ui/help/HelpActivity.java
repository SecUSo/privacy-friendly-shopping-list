package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.help;

import android.os.Bundle;
import android.preference.PreferenceFragment;
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