package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 22.06.16 creation date
 */
public abstract class DateUtils
{
    public static final String ISO_PATTERN_MIN = "yyyy-MM-dd HH:mm";
    public static final String ISO_PATTERN = "yyyy-MM-dd";
    public static final String US = "US";
    public static final String DE = "DE";
    public static final String JA = "JA";

    public static String getFormattedDateString(String aDate, String inputPattern, String outputPattern, String language)
    {
        DateTime date = getDateFromString(aDate, inputPattern, language);

        DateTimeFormatter outputFormatter = getDateTimeFormatter(outputPattern, language);
        return outputFormatter.print(date);
    }

    public static String getIsoDate(String aDate, String inputPattern, String language)
    {
        DateTime date = getDateFromString(aDate, inputPattern, language);

        DateTimeFormatter outputFormatter = getDateTimeFormatter(ISO_PATTERN, language);
        return outputFormatter.print(date);
    }

    public static DateTime getDateFromString(String aDate, String inputPattern, String language)
    {
        DateTimeFormatter inputFormatter = getDateTimeFormatter(inputPattern, language);
        return getDateFromString(aDate, inputFormatter);
    }

    public static String getDateAsString(Long date, String pattern, String language)
    {
        String isoDate = new DateTime(date).toString(DateUtils.ISO_PATTERN_MIN);
        return DateUtils.getFormattedDateString(isoDate, DateUtils.ISO_PATTERN_MIN, pattern, language);
    }

    private static DateTime getDateFromString(String aDate, DateTimeFormatter formatter)
    {
        return DateTime.parse(aDate, formatter);
    }

    private static DateTimeFormatter getDateTimeFormatter(String outputPattern, String language)
    {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(outputPattern);
        if ( language.equals(US) )
        {
            formatter = formatter.withLocale(Locale.US);
        }
        else if ( language.equals(DE) )
        {
            formatter = formatter.withLocale(Locale.GERMAN);
        }
        else if ( language.equals(JA) )
        {
            formatter = formatter.withLocale(Locale.JAPANESE);
        }
        return formatter;
    }

    public static String getFromIsoDate(String aDate, String outputPattern, String language)
    {
        DateTime date;
        try
        {
            date = getDateFromString(aDate, ISO_PATTERN_MIN, language);
        }
        catch ( Exception e )
        {
            date = getDateFromString(aDate, ISO_PATTERN, language);
        }

        DateTimeFormatter outputFormatter = getDateTimeFormatter(outputPattern, language);
        return outputFormatter.print(date);
    }
}
