package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.statistics.chart;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.settings.SettingsKeys;

import java.text.DecimalFormat;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 23.07.16 creation date
 */
public class YAxisLabels implements AxisValueFormatter
{
    private static final String SPACE = " ";
    private String currency;
    private DecimalFormat format;

    public YAxisLabels(Context context)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        currency = prefs.getString(SettingsKeys.CURRENCY, null);

        String numberFormat = context.getResources().getString(R.string.number_format);
        this.format = new DecimalFormat(numberFormat);
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis)
    {
        return format.format(value) + SPACE + currency;
    }

    @Override
    public int getDecimalDigits()
    {
        return 0;
    }
}
