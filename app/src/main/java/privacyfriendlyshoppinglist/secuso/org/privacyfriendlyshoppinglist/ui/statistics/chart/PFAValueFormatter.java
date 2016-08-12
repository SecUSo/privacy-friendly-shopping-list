package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.statistics.chart;

import android.content.Context;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.domain.StatisticsQuery;

import java.text.DecimalFormat;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 12.08.16 creation date
 */
public class PFAValueFormatter implements ValueFormatter
{
    private DecimalFormat format;

    public PFAValueFormatter(Context context, int valuesSelectedItemPos)
    {
        String numberFormat;
        if ( valuesSelectedItemPos == StatisticsQuery.PRICE )
        {
            numberFormat = context.getResources().getString(R.string.number_format_2_decimals);
        }
        else
        {
            numberFormat = context.getResources().getString(R.string.number_format_0_decimals);
        }
        this.format = new DecimalFormat(numberFormat);
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler)
    {
        return format.format(value);
    }
}
