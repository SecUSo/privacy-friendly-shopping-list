package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.statistics;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import org.joda.time.DateTime;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.DateUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.StringUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.StatisticsService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.domain.StatisticsChartData;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.domain.StatisticsQuery;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.baseactivity.BaseActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.statistics.chart.PFAChart;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.statistics.listeners.DataFromOnClickListener;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.statistics.listeners.DataToOnClickListener;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.statistics.listeners.SpinnerOnItemSelectListener;

import java.util.Observable;
import java.util.Observer;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 22.07.16 creation date
 */
public class StatisticsActivity extends BaseActivity implements Observer
{
    private static final long ANIMATION_DURATION = 500L;
    private static final float FADE_OUT = 0.0f;
    private static final float FADE_IN = 1.0f;
    private StatisticsService statisticsService;
    private StatisticsQuery query;
    private StatisticsCache cache;
    private PFAChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.statistics_activity);

        AbstractInstanceFactory instanceFactory = new InstanceFactory(getApplicationContext());
        statisticsService = (StatisticsService) instanceFactory.createInstance(StatisticsService.class);
        query = new StatisticsQuery();
        query.addObserver(this);
        cache = new StatisticsCache(this);
        chart = new PFAChart(cache);

        createFakeDataInDatabase();

        setupInitialDates(cache);
        setupSpinner(cache);

        overridePendingTransition(0, 0);
    }

    @Override
    protected int getNavigationDrawerID()
    {
        return R.id.nav_statistics;
    }

    @Override
    public void update(Observable observable, Object data)
    {
        int middleblue = getResources().getColor(R.color.middleblue);
        StatisticsChartData chartData = statisticsService.getChartData(query);
        updateChartVisibility(chartData);

        chart.updateChart(chartData.getData(), chartData.getLabels(), middleblue);
        String totalAmount = StringUtils.getDoubleAsString(chartData.getTotal(), cache.getNumberFormat());
        cache.getTotalTextView().setText(totalAmount);
        cache.getTitleTextView().setText(chartData.getTitle());
    }

    private void updateChartVisibility(StatisticsChartData chartData)
    {
        if ( chartData.getLabels().isEmpty() )
        {
            cache.getChart().animate().alpha(FADE_OUT).setDuration(ANIMATION_DURATION);
        }
        else
        {
            cache.getChart().animate().alpha(FADE_IN).setDuration(ANIMATION_DURATION);
        }
    }

    private void setupInitialDates(StatisticsCache cache)
    {
        String minDate = statisticsService.getMinDate();
        String maxDate = statisticsService.getMaxDate();

        query.setDateFrom(minDate);
        query.setDateTo(maxDate);

        cache.getRangeFromTextView().setText(minDate);
        cache.getRangeToTextView().setText(maxDate);

        cache.getRangeFromTextView().setOnClickListener(new DataFromOnClickListener(cache, query, cache.getRangeFromTextView()));
        cache.getRangeToTextView().setOnClickListener(new DataToOnClickListener(cache, query, cache.getRangeToTextView()));
    }

    private void setupSpinner(StatisticsCache cache)
    {
        String[] spinnerParams = getResources().getStringArray(R.array.statistics_spinner_params);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner, spinnerParams);
        cache.getGroupBySpinner().setAdapter(adapter);

        cache.getGroupBySpinner().setOnItemSelectedListener(new SpinnerOnItemSelectListener(query));
        query.setGroupBy(cache.getGroupBySpinner().getSelectedItemPosition());
        query.notifyObservers();
    }

    private void createFakeDataInDatabase()
    {
        for ( int i = 0; i < 365; i++ )
        {
            int mod3 = i % 3;
            int mod2 = i % 2;
            int mod5 = i % 5;

            String expectedQuantityPurchased = String.valueOf(mod3);
            ;
            String expectedStore = "";
            if ( mod5 != 0 )
            {
                expectedStore = "Store " + mod2;
            }
            String expectedPrice = String.valueOf(i);
            String expectedProductName = (i++ % 30) + " Product Name";
            String expectedCategory = "";
            if ( mod3 != 0 )
            {
                expectedCategory = "Cat. " + mod5;
            }

            DateTime currentDate = new DateTime().minusDays(100).plusDays(i);
            String stringDate = DateUtils.getDateAsString(currentDate.getMillis(), cache.getDatePattern(), cache.getDateLanguage());

            ProductDto dto = new ProductDto();
            dto.setQuantityPurchased(expectedQuantityPurchased);
            dto.setProductStore(expectedStore);
            dto.setProductPrice(expectedPrice);
            dto.setLastTimePurchased(stringDate);
            dto.setProductName(expectedProductName);
            dto.setProductCategory(expectedCategory);
            statisticsService.saveRecord(dto);
        }
    }
}
