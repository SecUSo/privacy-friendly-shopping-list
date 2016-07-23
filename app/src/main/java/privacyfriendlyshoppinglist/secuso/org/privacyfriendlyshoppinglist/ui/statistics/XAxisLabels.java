package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.statistics;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.AxisValueFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 22.07.16 creation date
 */
public class XAxisLabels implements AxisValueFormatter
{
    public static final String EMPTY = "";
    private List<String> labels;

    public XAxisLabels(String[] labels)
    {
        this.labels = new ArrayList<>();
        this.labels.addAll(Arrays.asList(labels));
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis)
    {
        if ( value == 0.0 )
        {
            return EMPTY;
        }
        else
        {
            value = value - 1;
            int labelIndex = (int) value % labels.size();
            return labels.get(labelIndex);
        }
    }

    @Override
    public int getDecimalDigits()
    {
        return 0;
    }
}
