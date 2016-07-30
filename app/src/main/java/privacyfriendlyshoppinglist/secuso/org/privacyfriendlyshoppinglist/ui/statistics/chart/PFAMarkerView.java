
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
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.settings.SettingsKeys;

import java.text.DecimalFormat;

class PFAMarkerView extends MarkerView
{
    private static final String SEPARATION = " - ";
    private static final String SPACE = " ";
    private TextView markup;
    private AxisValueFormatter xValueFormatter;
    private DecimalFormat format;
    private Context context;
    private String currency;

    PFAMarkerView(Context context, AxisValueFormatter xValueFormatter)
    {
        super(context, R.layout.statistics_marker_view);

        this.xValueFormatter = xValueFormatter;
        this.context = context;
        markup = (TextView) findViewById(R.id.textview_chart_markup);
        String numberFormat = this.context.getResources().getString(R.string.number_format_2_decimals);
        format = new DecimalFormat(numberFormat);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        currency = prefs.getString(SettingsKeys.CURRENCY, null);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight)
    {
        String markupText = xValueFormatter.getFormattedValue(e.getX(), null) + SEPARATION +
                format.format(e.getY()) + SPACE +
                currency;
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
