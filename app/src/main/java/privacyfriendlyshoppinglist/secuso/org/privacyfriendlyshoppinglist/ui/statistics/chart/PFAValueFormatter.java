package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.statistics.chart;

import android.content.Context;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.StringUtils;
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
    private NumberScale numberScale;
    private Context context;

    public PFAValueFormatter(Context context, int valuesSelectedItemPos, NumberScale numberScale)
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
        this.context = context;
        this.numberScale = numberScale;
        this.format = new DecimalFormat(numberFormat);
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler)
    {
        String numberSuffix = StringUtils.EMPTY;
        if ( numberScale != null && value > numberScale.getValue(context) )
        {
            value /= numberScale.getValue(context);
            numberSuffix = StringUtils.SPACE + numberScale.getAbbreviation(context);
        }
        return format.format(value) + numberSuffix;
    }
}
