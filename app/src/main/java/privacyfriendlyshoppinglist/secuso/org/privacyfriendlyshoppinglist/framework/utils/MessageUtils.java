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
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.welcome.WelcomeDialog;
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

    private static void setPreferenceToFalse(String preferenceKey, Activity activity)
    {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(preferenceKey, false);
        editor.commit();
    }

    public static void showAlertDialog(Context context, int titleResource, int messageResource, Observable action)
    {
        showAlertDialog(context, titleResource, messageResource, null, action);
    }

    public static void showAlertDialog(Context context, int titleResource, int messageResource, String customText, Observable action)
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
        dialogBuilder.setIcon(R.drawable.ic_dialog_alert_yellow);
        dialogBuilder.show();
    }
}
