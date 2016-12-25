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
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.deletelists.DeleteListsActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.deleteproducts.DeleteProductsActivity;
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
    private static Toast toast;

    private static int NOTHING = -1;

    public static void shareText(Context context, String text)
    {
        shareText(context, text, null);
    }

    public static void shareText(Context context, String text, String title)
    {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        if ( title != null )
        {
            sendIntent.putExtra(Intent.EXTRA_TITLE, title);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, title);
        }
        sendIntent.setType("text/plain");
        context.startActivity(Intent.createChooser(sendIntent, context.getResources().getText(R.string.share_as_text)));
    }

    public static void showToast(Context context, int messageStringResource, int toastLength)
    {
        if ( toast == null )
        {
            toast = Toast.makeText(context, messageStringResource, toastLength);
        }
        else
        {
            toast.setText(messageStringResource);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showWelcomeDialog(String keyWelcomeEnabled, Activity activity)
    {
        if ( PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext()).getBoolean(keyWelcomeEnabled, true) )
        {
            setPreferenceToFalse(keyWelcomeEnabled, activity);
            WelcomeDialog welcomeDialog = new WelcomeDialog();
            welcomeDialog.show(activity.getFragmentManager(), "WelcomeDialog");
        }
    }

    public static void showTutorialDialog(Activity activity)
    {
        String settingKey;
        int tutorialLayoutResource;
        int dialogTheme;
        if ( activity instanceof MainActivity )
        {
            settingKey = SettingsKeys.TUTORIAL_LIST;
            tutorialLayoutResource = R.layout.shopping_list_tutorial;
            dialogTheme = R.style.AlertDialogColourful;
        }
        else if ( activity instanceof ProductsActivity )
        {
            settingKey = SettingsKeys.TUTORIAL_PRODUCT;
            tutorialLayoutResource = R.layout.products_tutorial;
            dialogTheme = R.style.AlertDialogColourful;
        }
        else if ( activity instanceof DeleteProductsActivity || activity instanceof DeleteListsActivity )
        {
            settingKey = SettingsKeys.TUTORIAL_DELETE;
            tutorialLayoutResource = R.layout.delete_tutorial;
            dialogTheme = R.style.AlertDialogGrey;
        }
        else
        {
            return; // ignore
        }

        if ( PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext()).getBoolean(settingKey, true) )
        {
            setPreferenceToFalse(settingKey, activity);
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, dialogTheme);
            dialogBuilder.setView(tutorialLayoutResource);
            dialogBuilder.setPositiveButton(activity.getString(R.string.okay), null);
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

    private static void setPreferenceToFalse(String preferenceKey, Activity activity)
    {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(preferenceKey, false);
        editor.commit();
    }

    public static void showInfoDialog(Context context, int titleResource, int messageResource, Observable action)
    {
        showAlertDialog(context, titleResource, messageResource, R.drawable.ic_menu_info_darkblue, null, action);
    }

    public static void showAlertDialog(Context context, int titleResource, int messageResource, Observable action)
    {
        showAlertDialog(context, titleResource, messageResource, null, action);
    }

    public static void showAlertDialog(Context context, int titleResource, int messageResource, String customText, Observable action)
    {
        showAlertDialog(context, titleResource, messageResource, R.drawable.ic_dialog_alert_yellow, customText, action);
    }

    public static void showAlertDialog(Context context, int titleResource, int messageResource, int icon, String customText, Observable action)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context, R.style.AlertDialogColourful);
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
        dialogBuilder.setIcon(icon);
        dialogBuilder.show();
    }
}
