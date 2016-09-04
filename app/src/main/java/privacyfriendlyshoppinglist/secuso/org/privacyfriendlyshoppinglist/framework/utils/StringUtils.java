package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils;

import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 12.06.16 creation date
 */
public abstract class StringUtils
{

    public static final String EMPTY = "";
    public static final String NEW_LINE = "\n";
    public static final String SPACE = " ";
    public static final String DETAIL_SEPARATOR = ": ";
    public static final String DASH = "- ";
    public static final String COMMA = ",";
    public static final String LEFT_BRACE = "[ ";
    public static final String RIGHT_BRACE = " ] ";
    public static final double PARSE_ERROR = -1.0;

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

    public static Double getStringAsDouble(String numberAsString, String format)
    {
        DecimalFormat df = new DecimalFormat(format);
        try
        {
            Number parse = df.parse(numberAsString);
            return parse.doubleValue();
        }

        catch ( ParseException e )
        {
            return PARSE_ERROR;
        }
    }

    public static Double getStringAsDouble(String productPrice, String priceFormat2, String priceFormat1, String priceFormat0)
    {
        Double stringAsDouble;
        try
        {

            stringAsDouble = StringUtils.getStringAsDouble(productPrice, priceFormat2);
        }
        catch ( Exception e1 )
        {
            try
            {
                stringAsDouble = StringUtils.getStringAsDouble(productPrice, priceFormat1);
            }
            catch ( Exception e2 )
            {
                try
                {
                    stringAsDouble = StringUtils.getStringAsDouble(productPrice, priceFormat0);
                }
                catch ( Exception e3 )
                {
                    stringAsDouble = PARSE_ERROR;
                }
            }
        }

        return stringAsDouble;
    }
}
