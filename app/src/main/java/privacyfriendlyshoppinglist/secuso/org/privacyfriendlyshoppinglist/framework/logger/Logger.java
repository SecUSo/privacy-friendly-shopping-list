package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.logger;

import android.util.Log;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 31.05.16 creation date
 */
public class Logger
{
    public static void info(String className, String methodName, Object object)
    {
        Log.i(className, "METHOD=" + methodName + "; PARAMETER=" + object.toString());
    }

    public static void info(String className, String methodName, String status)
    {
        Log.i(className, "METHOD=" + methodName + "; STATUS=" + status);
    }

    public static void error(String className, String methodName, Object object, Throwable t)
    {
        Log.e(className, "METHOD" + methodName + "; PARAMETER=" + object.toString(), t);
    }
}
