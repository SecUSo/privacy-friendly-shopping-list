package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist.reminder;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.dialog.ProductDialogFragment;

/**
 * Created by Chris on 13.08.2016.
 */
public class ReminderSchedulingService extends IntentService
{
    public ReminderSchedulingService()
    {
        super("SchedulingService");
    }


    public static final int NOTIFICATION_ID = 10;
    public static final String MESSAGETEXT = "com.shoppinglist.notificationservicetext";
    public static final String MESSAGEUUID = "com.shoppinglist.notificationserviceuuid";
    public static final String LISTID = "com.shoppinglist.notificationserviceuuid";

    private String messageText;
    private String messageUUID;
    private Context context;


    @Override
    protected void onHandleIntent(Intent intent)
    {

        messageText = intent.getStringExtra(MESSAGETEXT);
        messageUUID = intent.getStringExtra(MESSAGEUUID);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent i = new Intent(this, ProductDialogFragment.class);
        i.putExtra(ReminderSchedulingService.MESSAGEUUID, messageUUID);

        Notification notification = new Notification.Builder(this)
                .setContentTitle(messageText)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentIntent(PendingIntent.getActivity(this, messageUUID.hashCode(), i, PendingIntent.FLAG_UPDATE_CURRENT))
                .build();

        manager.notify(NOTIFICATION_ID, notification);

    }
}
