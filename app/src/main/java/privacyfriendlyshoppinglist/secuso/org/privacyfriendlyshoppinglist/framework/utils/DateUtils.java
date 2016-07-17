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
public class DateUtils
{
    public static final int MILLISECONDS_TO_MINUTES = (1000 * 60);
    public static final String ISO_PATTERN_MIN = "yyyy-MM-dd HH:mm";
    public static final String ISO_PATTERN = "yyyy-MM-dd";
    public static final String US = "US";
    public static final String DE = "DE";

    public static String getFormattedDateString(String aDate, String inputPattern, String outputPattern, String language)
    {
        DateTime date = getDateFromString(aDate, inputPattern, language);

        DateTimeFormatter outputFormatter = getDateTimeFormatter(outputPattern, language);
        String dateString = outputFormatter.print(date);
        return dateString;
    }

    public static String getIsoDate(String aDate, String inputPattern, String language)
    {
        DateTime date = getDateFromString(aDate, inputPattern, language);

        DateTimeFormatter outputFormatter = getDateTimeFormatter(ISO_PATTERN, language);
        String dateString = outputFormatter.print(date);
        return dateString;
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
        String dateString = outputFormatter.print(date);
        return dateString;
    }

    public static DateTime getDateFromString(String aDate, String inputPattern, String language)
    {
        DateTimeFormatter inputFormatter = getDateTimeFormatter(inputPattern, language);
        DateTime date = getDateFromString(aDate, inputFormatter);
        return date;
    }

    public static String getDateAsString(Long date, String pattern, String language)
    {
        String isoDate = new DateTime(date).toString(DateUtils.ISO_PATTERN_MIN);
        return DateUtils.getFormattedDateString(isoDate, DateUtils.ISO_PATTERN_MIN, pattern, language);
    }

    public static Long getDurationInMinutes(Long arrivalTime, Long departureTime)
    {
        Long durationInMilliseconds = Math.abs(arrivalTime - departureTime);
        Long durationInMinutes = durationInMilliseconds / MILLISECONDS_TO_MINUTES;
        return durationInMinutes;
    }

    private static DateTime getDateFromString(String aDate, DateTimeFormatter formatter)
    {
        DateTime dateTime = DateTime.parse(aDate, formatter);
        return dateTime;
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
        return formatter;
    }
}
