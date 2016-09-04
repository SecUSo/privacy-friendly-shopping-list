
package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.statistics.chart;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.TextView;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.StringUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.domain.StatisticsQuery;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.settings.SettingsKeys;

import java.text.DecimalFormat;

class PFAMarkerView extends MarkerView
{
    private static final String SEPARATION = " - ";
    private static final String SPACE = " ";
    private NumberScale numberScale;
    private TextView markup;
    private AxisValueFormatter xValueFormatter;
    private DecimalFormat format;
    private Context context;
    private String unit;

    PFAMarkerView(Context context, AxisValueFormatter xValueFormatter, int valuesSelectedItemPos, NumberScale numberScale)
    {
        super(context, R.layout.statistics_marker_view);

        this.xValueFormatter = xValueFormatter;
        this.context = context;
        markup = (TextView) findViewById(R.id.textview_chart_markup);

        String numberFormat;
        if ( valuesSelectedItemPos == StatisticsQuery.PRICE )
        {
            numberFormat = context.getResources().getString(R.string.number_format_2_decimals);
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            String defaultCurrency = context.getResources().getString(R.string.currency);
            this.unit = prefs.getString(SettingsKeys.CURRENCY, defaultCurrency);
        }
        else
        {
            numberFormat = context.getResources().getString(R.string.number_format_0_decimals);
            this.unit = context.getResources().getString(R.string.statistics_quantity_unit);
        }

        this.context = context;
        this.numberScale = numberScale;
        this.format = new DecimalFormat(numberFormat);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight)
    {
        float value = e.getY();
        String numberSuffix = StringUtils.EMPTY;
        if ( numberScale != null && value > numberScale.getValue(context) )
        {
            value /= numberScale.getValue(context);
            numberSuffix = numberScale.getAbbreviation(context) + StringUtils.SPACE;
        }
        String markupText =
                xValueFormatter.getFormattedValue(e.getX(), null) +
                        SEPARATION +
                        format.format(value) + SPACE + numberSuffix +
                        unit;
        markup.setText(markupText);
    }

    @Override
    public int getXOffset(float xpos)
    {
        int center = -(getWidth() / 2);
        return center;
    }

    @Override
    public int getYOffset(float ypos)
    {
        return -getHeight();
    }
}
