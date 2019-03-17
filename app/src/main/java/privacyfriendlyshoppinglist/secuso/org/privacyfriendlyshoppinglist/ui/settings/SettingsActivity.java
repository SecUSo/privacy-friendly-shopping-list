package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.settings;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.*;
import android.view.MenuItem;
import android.widget.Toast;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.MessageUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.StatisticsService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.baseactivity.BaseActivity;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p/>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends BaseActivity
{
    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener()
    {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value)
        {
            String stringValue = value.toString();

            if ( preference instanceof ListPreference )
            {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[ index ]
                                : null);
            }
            else
            {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }
            return true;
        }
    };

    /**
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */
    private static boolean isXLargeTablet(Context context)
    {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    /**
     * Binds a preference's summary to its value. More specifically, when the
     * preference's value is changed, its summary (line of text below the
     * preference title) is updated to reflect the value. The summary is also
     * immediately updated upon calling this method. The exact display format is
     * dependent on the type of preference.
     *
     * @see #sBindPreferenceSummaryToValueListener
     */
    private static void bindPreferenceSummaryToValue(Preference preference)
    {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        //setupActionBar();


        overridePendingTransition(0, 0);
    }

    @Override
    protected int getNavigationDrawerID()
    {
        return R.id.nav_settings;
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    /*private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }*/

    /*@Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            //finish();
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return true;

            // (!super.onMenuItemSelected(featureId, item)) {
            //    NavUtils.navigateUpFromSameTask(this);
            //}
            //return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }*/

    /**
     * {@inheritDoc}
     */
    /*@Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }*/

    /**
     * {@inheritDoc}
     */
    /*@Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);
    }*/

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    protected boolean isValidFragment(String fragmentName)
    {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || GeneralPreferenceFragment.class.getName().equals(fragmentName);
    }

    /**
     * This fragment shows general preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class GeneralPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);
            //setHasOptionsMenu(true);

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            bindPreferenceSummaryToValue(findPreference(SettingsKeys.CURRENCY));

            SwitchPreference checkboxSettings = (SwitchPreference) findPreference(SettingsKeys.CHECKBOX_POSITION_PREF);
            checkboxSettings.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
            {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue)
                {
                    if ( !checkboxSettings.isChecked() )
                    {
                        MessageUtils.showToast(getActivity(), R.string.pref_checkbox_toast_on, Toast.LENGTH_SHORT);
                    }
                    else
                    {
                        MessageUtils.showToast(getActivity(), R.string.pref_checkbox_toast_off, Toast.LENGTH_SHORT);
                    }
                    return true;
                }
            });
            checkboxSettings.setSwitchTextOn(R.string.pref_checkbox_right_display_text);
            checkboxSettings.setSwitchTextOff(R.string.pref_checkbox_left_display_text);

            SwitchPreference moveProductsPref = (SwitchPreference) findPreference(SettingsKeys.MOVE_PRODUCTS_PREF);
            moveProductsPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
            {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue)
                {
                    if ( !moveProductsPref.isChecked() )
                    {
                        MessageUtils.showToast(getActivity(), R.string.pref_move_products_toast_on, Toast.LENGTH_SHORT);
                    }
                    else
                    {
                        MessageUtils.showToast(getActivity(), R.string.pref_move_products_toast_off, Toast.LENGTH_SHORT);
                    }
                    return true;
                }
            });


            SwitchPreference notificationsSetting = (SwitchPreference) findPreference(SettingsKeys.NOTIFICATIONS_ENABLED);
            notificationsSetting.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
            {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue)
                {
                    if ( !notificationsSetting.isChecked() )
                    {
                        MessageUtils.showToast(getActivity(), R.string.pref_notifications_toast_on, Toast.LENGTH_SHORT);
                    }
                    else
                    {
                        MessageUtils.showToast(getActivity(), R.string.pref_notifications_toast_off, Toast.LENGTH_SHORT);
                    }
                    return true;
                }
            });

            SwitchPreference statisticsSetting = (SwitchPreference) findPreference(SettingsKeys.STATISTICS_ENABLED);
            statisticsSetting.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
            {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue)
                {
                    if ( !statisticsSetting.isChecked() )
                    {
                        MessageUtils.showToast(getActivity(), R.string.pref_statistics_toast_on, Toast.LENGTH_LONG);
                    }
                    else
                    {
                        MessageUtils.showToast(getActivity(), R.string.pref_statistics_toast_off, Toast.LENGTH_SHORT);
                    }
                    return true;
                }
            });


            Preference statisticsDeletePref = findPreference(SettingsKeys.STATISTICS_DELETE);
            statisticsDeletePref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
            {
                @Override
                public boolean onPreferenceClick(Preference preference)
                {
                    MessageUtils.showAlertDialog(getActivity(), R.string.delete_confirmation_title, R.string.delete_statistics_confirmation, deleteStatistics());
                    return false;
                }
            });
            //bindPreferenceSummaryToValue(findPreference("example_list"));
        }

        private Observable<Void> deleteStatistics()
        {
            Observable observable = Observable
                    .defer(() -> Observable.just(deleteStatisticsSync()))
                    .doOnError(Throwable::printStackTrace)
                    .subscribeOn(Schedulers.computation());
            return observable;
        }

        private Void deleteStatisticsSync()
        {
            AbstractInstanceFactory instanceFactory = new InstanceFactory(getActivity().getApplicationContext());
            StatisticsService statisticsService = (StatisticsService) instanceFactory.createInstance(StatisticsService.class);
            statisticsService.deleteAll()
                    .doOnError(Throwable::printStackTrace).subscribe();
            return null;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item)
        {
            int id = item.getItemId();
            if ( id == android.R.id.home )
            {
                //getActivity().finish();
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }
}
