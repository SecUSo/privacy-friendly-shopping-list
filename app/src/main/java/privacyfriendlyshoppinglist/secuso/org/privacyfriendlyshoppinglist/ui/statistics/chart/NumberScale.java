package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.statistics.chart;

import android.content.Context;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 04.09.16 creation date
 */
public enum NumberScale
{
    NO_SCALE(R.string.no_scale_abbreviation, R.string.no_scale_value),
    KILO(R.string.kilo_abbreviation, R.string.kilo_value),
    MILLION(R.string.million_abbreviation, R.string.million_value);

    private int abbreviation;
    private int value;

    NumberScale(int abbreviation, int value)
    {
        this.abbreviation = abbreviation;
        this.value = value;
    }

    public String getAbbreviation(Context context)
    {
        return context.getResources().getString(this.abbreviation);
    }

    public int getValue(Context context)
    {
        return Integer.parseInt(context.getResources().getString(this.value));
    }
}
