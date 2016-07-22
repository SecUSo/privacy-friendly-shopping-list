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
public class AxisLabels implements AxisValueFormatter
{
    public static final String EMPTY = "";
    private List<String> labels;
    private int decimalDigits;

    public AxisLabels(String[] labels)
    {
        this.labels = new ArrayList<>();
        this.labels.add(EMPTY);
        this.labels.addAll(Arrays.asList(labels));
        decimalDigits = 0;
    }

    public void setDecimalDigits(int decimalDigits)
    {
        this.decimalDigits = decimalDigits;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis)
    {
        int nrLabels = labels.size();
        if ( value < nrLabels )
        {
            return labels.get((int) value % nrLabels);
        }
        else // ignore the empty label
        {
            return labels.get((int) value % nrLabels + 1);
        }
    }

    @Override
    public int getDecimalDigits()
    {
        return decimalDigits;
    }
}
