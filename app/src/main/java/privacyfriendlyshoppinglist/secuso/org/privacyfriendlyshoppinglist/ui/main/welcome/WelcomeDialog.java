package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.welcome;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.MainActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.settings.SettingsKeys;

public class WelcomeDialog extends DialogFragment
{
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        LayoutInflater i = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(i.inflate(R.layout.welcome_dialog, null));
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(getActivity().getString(R.string.welcome));
        builder.setPositiveButton(getActivity().getString(R.string.okay), null);
        builder.setNeutralButton(getActivity().getString(R.string.do_not_show_again), new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                SharedPreferences welcomeSettings = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = welcomeSettings.edit();
                editor.putBoolean(SettingsKeys.WELCOME_PREF, false);
                editor.commit();
            }
        });
        builder.setNegativeButton(getActivity().getString(R.string.viewhelp), new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                ((MainActivity) getActivity()).goToNavigationItem(R.id.nav_help);
            }
        });

        return builder.create();
    }
}

