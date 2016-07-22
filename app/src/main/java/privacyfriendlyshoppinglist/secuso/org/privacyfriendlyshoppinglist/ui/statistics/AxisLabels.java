package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.statistics;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.AxisValueFormatter;

import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 22.07.16 creation date
 */
public class AxisLabels implements AxisValueFormatter
{
    private List<String> labels;
    private int decimalDigits;

    public AxisLabels(List<String> labels)
    {
        this.labels = labels;
        decimalDigits = 0;
    }

    public void setDecimalDigits(int decimalDigits)
    {
        this.decimalDigits = decimalDigits;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis)
    {
        return labels.get((int) value % labels.size());
    }

    @Override
    public int getDecimalDigits()
    {
        return decimalDigits;
    }
}
