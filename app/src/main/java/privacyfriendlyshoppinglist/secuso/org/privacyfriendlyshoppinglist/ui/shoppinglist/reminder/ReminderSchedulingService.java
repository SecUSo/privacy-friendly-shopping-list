package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist.reminder;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.MainActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductsActivity;
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
    private String listId;

    private Context context;


    @Override
    protected void onHandleIntent(Intent intent)
    {

        messageText = intent.getStringExtra(MESSAGETEXT);
        messageUUID = intent.getStringExtra(MESSAGEUUID);
        listId = intent.getStringExtra(LISTID);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent i = new Intent(this, ProductDialogFragment.class);
        i.putExtra(ReminderSchedulingService.MESSAGEUUID, messageUUID);

        PendingIntent pendingIntent = getPendingIntent(getApplicationContext(), listId);

        Notification notification = new Notification.Builder(this)
                .setContentTitle(messageText)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher)
                .setDefaults(Notification.DEFAULT_SOUND)
                .addAction(R.drawable.ic_launcher, "Go to List", pendingIntent)
                .setContentIntent(PendingIntent.getActivity(this, messageUUID.hashCode(), i, PendingIntent.FLAG_UPDATE_CURRENT))
                .build();

        manager.notify(NOTIFICATION_ID, notification);

    }

    private PendingIntent getPendingIntent(Context context, String listId)
    {
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        Intent productsIntent = new Intent(context, ProductsActivity.class);
        productsIntent.putExtra(MainActivity.LIST_ID_KEY, listId);
        stackBuilder.addNextIntent(productsIntent);
        return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
    }


}
