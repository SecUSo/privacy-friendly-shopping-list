
package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.statistics;

import android.content.Context;
import android.widget.TextView;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;

import java.text.DecimalFormat;

public class PFAMarkerView extends MarkerView
{
    private static final String SEPARATION = ", ";
    private static final String SPACE = " ";
    private TextView markup;
    private AxisValueFormatter xValueFormatter;
    private DecimalFormat format;
    private Context context;

    public PFAMarkerView(Context context, AxisValueFormatter xValueFormatter)
    {
        super(context, R.layout.statistics_marker_view);

        this.xValueFormatter = xValueFormatter;
        this.context = context;
        markup = (TextView) findViewById(R.id.textview_chart_markup);
        String numberFormat = this.context.getResources().getString(R.string.number_format);
        format = new DecimalFormat(numberFormat);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight)
    {
        String currency = context.getResources().getString(R.string.currency);
        markup.setText(
                xValueFormatter.getFormattedValue(e.getX(), null) + SEPARATION +
                        format.format(e.getY()) + SPACE +
                        currency);
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
