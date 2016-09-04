package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.statistics.chart;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.StringUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.domain.StatisticsQuery;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.settings.SettingsKeys;

import java.text.DecimalFormat;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 23.07.16 creation date
 */
public class PFAYAxisLabels implements AxisValueFormatter
{
    private static final String SPACE = " ";
    private String unit;
    private DecimalFormat format;
    private Context context;
    private NumberScale numberScale;

    public PFAYAxisLabels(Context context, int valuesSelectedItemPos, NumberScale numberScale)
    {
        this.numberScale = numberScale;
        this.context = context;
        String numberFormat;
        if ( valuesSelectedItemPos == StatisticsQuery.PRICE )
        {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            String defaultCurrency = context.getResources().getString(R.string.currency);
            this.unit = prefs.getString(SettingsKeys.CURRENCY, defaultCurrency);
            numberFormat = context.getResources().getString(R.string.number_format_2_decimals);
        }
        else
        {
            this.unit = context.getResources().getString(R.string.statistics_quantity_unit);
            numberFormat = context.getResources().getString(R.string.number_format_0_decimals);
        }
        this.format = new DecimalFormat(numberFormat);
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis)
    {
        String numberSuffix = StringUtils.EMPTY;
        if ( numberScale != null && value > numberScale.getValue(context) )
        {
            value /= numberScale.getValue(context);
            numberSuffix = numberScale.getAbbreviation(context) + StringUtils.SPACE;
        }
        return format.format(value) + SPACE + numberSuffix + unit;
    }

    @Override
    public int getDecimalDigits()
    {
        return 0;
    }
}
