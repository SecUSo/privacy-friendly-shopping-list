package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 18.08.16 creation date
 */
public class NotificationUtils
{
    public static void removeNotification(Activity activity, String id)
    {
        NotificationManager manager = (NotificationManager) activity.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(Integer.parseInt(id));
    }
}
