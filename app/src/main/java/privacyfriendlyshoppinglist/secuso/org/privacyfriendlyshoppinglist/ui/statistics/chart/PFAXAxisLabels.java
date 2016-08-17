package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.statistics.chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 22.07.16 creation date
 */
class PFAXAxisLabels implements AxisValueFormatter
{
    private List<String> labels;

    PFAXAxisLabels(String[] labels)
    {
        this.labels = new ArrayList<>();
        this.labels.addAll(Arrays.asList(labels));
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis)
    {
        if ( value == 0.0 || labels.isEmpty() )
        {
            return StringUtils.EMPTY;
        }
        else
        {
            value = value - 1;
            if ( value >= labels.size() )
            {
                return StringUtils.EMPTY;
            }
            else
            {
                return labels.get((int) value);
            }
        }
    }

    @Override
    public int getDecimalDigits()
    {
        return 0;
    }
}
