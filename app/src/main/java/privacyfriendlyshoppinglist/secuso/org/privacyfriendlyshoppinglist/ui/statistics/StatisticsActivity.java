package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.statistics;

import android.os.Bundle;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.baseactivity.BaseActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.statistics.chart.PFAChart;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 22.07.16 creation date
 */
public class StatisticsActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.statistics_activity);

        StatisticsCache cache = new StatisticsCache(this);

        String[] months = getResources().getStringArray(R.array.statistics_months);
        int middleblue = getResources().getColor(R.color.middleblue);
        List<Double> inputData = getFakeDataForChart();

        PFAChart pfaChart = new PFAChart(cache);
        pfaChart.setXlabels(months);
        pfaChart.updateChart(inputData, middleblue);

        overridePendingTransition(0, 0);
    }

    private List<Double> getFakeDataForChart()
    {
        List<Double> inputData = new ArrayList<>();
        for ( int i = 0; i < 50; i++ )
        {
            inputData.add((double) i);
        }
        return inputData;
    }

    @Override
    protected int getNavigationDrawerID()
    {
        return R.id.nav_statistics;
    }
}
