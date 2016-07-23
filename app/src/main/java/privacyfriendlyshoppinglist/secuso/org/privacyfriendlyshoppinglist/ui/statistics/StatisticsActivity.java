package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.statistics;

import android.os.Bundle;
import org.joda.time.DateTime;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.DateUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.baseactivity.BaseActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.statistics.chart.PFAChart;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.statistics.listeners.DateOnClickListener;

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

        setupInitialDates(cache);

        overridePendingTransition(0, 0);
    }

    private void setupInitialDates(StatisticsCache cache)
    {
        // todo: setup the correct ranges here
        DateTime currentDate = new DateTime();
        String stringDate = DateUtils.getDateAsString(currentDate.getMillis(), cache.getDatePattern(), cache.getDateLanguage());

        cache.getRangeFromTextView().setText(stringDate);
        cache.getRangeToTextView().setText(stringDate);

        cache.getRangeFromTextView().setOnClickListener(new DateOnClickListener(cache, cache.getRangeFromTextView()));
        cache.getRangeToTextView().setOnClickListener(new DateOnClickListener(cache, cache.getRangeToTextView()));
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
