package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils;

import java.text.DecimalFormat;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 12.06.16 creation date
 */
public class StringUtils
{

    public static final String EMPTY = "";

    public static boolean isEmpty(String string)
    {
        return string == null || string.isEmpty();
    }

    public static String getDoubleAsString(double number, String format)
    {
        String numberAsString;
        try
        {
            DecimalFormat df = new DecimalFormat(format);
            numberAsString = df.format(number);
        }
        catch ( Exception e )
        {
            numberAsString = EMPTY;
        }
        return numberAsString;
    }
}
