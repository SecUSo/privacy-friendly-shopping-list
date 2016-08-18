package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 18.08.16 creation date
 */
public class MessageUtils
{
    public static void showToast(Context context, int messageStringResource, int toastLength)
    {
        Toast toast = Toast.makeText(context, messageStringResource, toastLength);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
