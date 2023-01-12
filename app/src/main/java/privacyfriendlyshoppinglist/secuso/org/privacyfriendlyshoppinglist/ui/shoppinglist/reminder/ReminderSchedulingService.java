package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist.reminder;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.MainActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductsActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.settings.SettingsKeys;

/**
 * Created by Chris on 13.08.2016.
 */
public class ReminderSchedulingService extends IntentService
{
    public static final String MESSAGE_TEXT = "com.shoppinglist.notificationservicetext";

    public ReminderSchedulingService()
    {
        super("SchedulingService");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        String messageText = intent.getStringExtra(MESSAGE_TEXT);
        String listId = intent.getStringExtra(MainActivity.LIST_ID_KEY);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        PendingIntent pendingIntent = getPendingIntent(getApplicationContext(), listId);

        String appName = getResources().getString(R.string.app_name);

        if ( PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean(SettingsKeys.NOTIFICATIONS_ENABLED, true) )
        {

            Notification notification = new NotificationCompat.Builder(this)
                    .setContentTitle(appName)
                    .setContentText(messageText)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(messageText))
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.ic_stat_name)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setContentIntent(pendingIntent)
                    .build();

            manager.notify(Integer.parseInt(listId), notification);
        }
    }

    private PendingIntent getPendingIntent(Context context, String listId)
    {
        Intent pendingIntent = new Intent(context, ProductsActivity.class);
        pendingIntent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

        pendingIntent.putExtra(MainActivity.LIST_ID_KEY, listId);
        TaskStackBuilder stackBuilder = TaskStackBuilder
                .create(context)
                .addParentStack(ProductsActivity.class)
                .addNextIntent(pendingIntent);
        return stackBuilder.getPendingIntent(Integer.parseInt(listId), PendingIntent.FLAG_UPDATE_CURRENT);
    }


}
