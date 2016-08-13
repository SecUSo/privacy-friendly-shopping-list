package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by Chris on 13.08.2016.
 */
public class ReminderReceiver extends WakefulBroadcastReceiver
{

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Intent service = new Intent(context, ReminderSchedulingService.class);
        startWakefulService(context, service);

    }

    public void setAlarm(Context context, Intent i, long deltaTimeInMillis)
    {
        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pi = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        //Intent intent = new Intent(context, ReminderReceiver.class);
        //alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);


        alarmMgr.set(AlarmManager.RTC_WAKEUP, deltaTimeInMillis, pi);
    }

    public void cancelAlarm(Context context)
    {
        // If the alarm has been set, cancel it.
        if ( alarmMgr != null )
        {
            alarmMgr.cancel(alarmIntent);
        }
    }

}
