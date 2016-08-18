package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.widget.Toast;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.help.HelpActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.MainActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.welcome.WelcomeDialog;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductsActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.settings.SettingsKeys;
import rx.Observable;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 18.08.16 creation date
 */
public class MessageUtils
{
    private static int NOTHING = -1;

    public static void showToast(Context context, int messageStringResource, int toastLength)
    {
        Toast toast = Toast.makeText(context, messageStringResource, toastLength);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showWelcomeDialog(String keyWelcomeEnabled, Activity activity)
    {
        if ( PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext()).getBoolean(keyWelcomeEnabled, true) )
        {
            WelcomeDialog welcomeDialog = new WelcomeDialog();
            welcomeDialog.show(activity.getFragmentManager(), "WelcomeDialog");
        }
    }

    public static void showTutorialDialog(Activity activity)
    {
        String settingKey;
        int tutorialLayoutResource;
        if ( activity instanceof MainActivity )
        {
            settingKey = SettingsKeys.TUTORIAL_LIST;
            tutorialLayoutResource = R.layout.shopping_list_tutorial;
        }
        else if ( activity instanceof ProductsActivity )
        {
            settingKey = SettingsKeys.TUTORIAL_PRODUCT;
            tutorialLayoutResource = R.layout.products_tutorial;
        }
        else
        {
            return; // ignore
        }

        if ( PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext()).getBoolean(settingKey, true) )
        {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, R.style.AlertDialogCustom);
            dialogBuilder.setView(tutorialLayoutResource);
            dialogBuilder.setPositiveButton(activity.getString(R.string.okay), null);
            dialogBuilder.setNeutralButton(activity.getString(R.string.do_not_show_again), new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    SharedPreferences welcomeSettings = PreferenceManager.getDefaultSharedPreferences(activity);
                    SharedPreferences.Editor editor = welcomeSettings.edit();
                    editor.putBoolean(settingKey, false);
                    editor.commit();
                }
            });
            dialogBuilder.setNegativeButton(activity.getString(R.string.viewhelp), new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    Intent helpIntent = new Intent(activity, HelpActivity.class);
                    activity.startActivity(helpIntent);
                }
            });
            dialogBuilder.show();
        }
    }

    public static void showAlertDialog(Context context, int titleResource, int messageResource, Observable action)
    {
        showAlertDialog(context, titleResource, messageResource, null, action);
    }

    public static void showAlertDialog(Context context, int titleResource, int messageResource, String customText, Observable action)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context, R.style.AlertDialogCustom);
        if ( titleResource != NOTHING )
        {
            String title = context.getResources().getString(titleResource);
            dialogBuilder.setTitle(title);
        }
        if ( messageResource != NOTHING )
        {
            String message;
            if ( customText == null )
            {
                message = context.getResources().getString(messageResource);
            }
            else
            {
                message = context.getResources().getString(messageResource, customText);
            }
            dialogBuilder.setMessage(message);

        }
        dialogBuilder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                action.subscribe();
            }
        });
        dialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                // do nothing
            }
        });
        dialogBuilder.setIcon(R.drawable.ic_dialog_alert_yellow);
        dialogBuilder.show();
    }
}
