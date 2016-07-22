package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.statistics;

import android.os.Bundle;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.baseactivity.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 22.07.16 creation date
 */
public class StatisticsActivity extends BaseActivity
{
    private static final String EMPTY = "";
    private BarChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.statistics_activity);

        chart = (BarChart) findViewById(R.id.chart);

        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.setDescription(EMPTY);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(60);
        chart.setPinchZoom(false);
        chart.setDrawGridBackground(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(10);

        String[] months = getResources().getStringArray(R.array.statistics_months);
        xAxis.setValueFormatter(new AxisLabels(Arrays.asList(months)));

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setLabelCount(10, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(20f);
        leftAxis.setAxisMinValue(0f);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);


        setData(12);


        overridePendingTransition(0, 0);
    }

    @Override
    protected int getNavigationDrawerID()
    {
        return R.id.nav_statistics;
    }

    private void setData(int count)
    {

        float start = 0f;

        chart.getXAxis().setAxisMinValue(start);
        chart.getXAxis().setAxisMaxValue(start + count + 2);

        ArrayList<BarEntry> yValues = new ArrayList<BarEntry>();

        for ( int i = (int) start; i < start + count + 1; i++ )
        {
            yValues.add(new BarEntry(i, i));
        }

        BarDataSet dataSet;

        if ( chart.getData() != null && chart.getData().getDataSetCount() > 0 )
        {
            dataSet = (BarDataSet) chart.getData().getDataSetByIndex(0);
            dataSet.setValues(yValues);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        }
        else
        {
            dataSet = new BarDataSet(yValues, EMPTY);
            int[] colors = {getResources().getColor(R.color.middleblue)};
            dataSet.setColors(colors);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(dataSet);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);

            chart.setData(data);
        }
    }
}
