package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.logger;

import android.util.Log;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 31.05.16 creation date
 */
public abstract class PFALogger
{
    public static void debug(String className, String methodName, String result)
    {
        Log.d(className, "METHOD=" + methodName + "; RESULT=" + result);
    }

    public static void error(String className, String methodName, Throwable t)
    {
        Log.e(className, "METHOD" + methodName, t);
    }
}
