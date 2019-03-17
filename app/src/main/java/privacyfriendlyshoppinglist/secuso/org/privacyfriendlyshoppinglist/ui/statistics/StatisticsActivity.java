package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.statistics;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.StringUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.StatisticsService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.domain.StatisticsChartData;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.domain.StatisticsQuery;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.baseactivity.BaseActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.statistics.chart.PFAChart;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.statistics.listeners.DataFromOnClickListener;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.statistics.listeners.DataToOnClickListener;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.statistics.listeners.GroupBySpinnerOnItemSelectListener;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.statistics.listeners.ValuesSpinnerOnItemSelectListener;

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

        setupQuery(cache);

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
        final StatisticsChartData[] chartData = new StatisticsChartData[ 1 ];
        statisticsService.getChartData(query)
                .doOnNext(result -> chartData[ 0 ] = result)
                .doOnCompleted(() ->
                {
                    cache.setNumberScale(chartData[ 0 ].getNumberScale());
                    updateChartVisibility(chartData[ 0 ]);

                    chart.updateChart(chartData[ 0 ].getData(), chartData[ 0 ].getLabels(), middleblue);
                    String totalAmount = StringUtils.getDoubleAsString(chartData[ 0 ].getTotal(), cache.getNumberFormat());
                    cache.getUnitsTextView().setText(cache.getCurrency());

                    if ( cache.getValuesSpinner().getSelectedItemPosition() == StatisticsQuery.QUANTITY )
                    {
                        // don't show decimals for quantities
                        totalAmount = totalAmount.replace(".00", "").replace(",00", "");
                        String unit = getResources().getString(R.string.statistics_quantity_unit);
                        cache.getUnitsTextView().setText(unit);
                    }

                    cache.getTotalTextView().setText(totalAmount);
                    cache.getTitleTextView().setText(chartData[ 0 ].getTitle());
                })
                .doOnError(Throwable::printStackTrace)
                .subscribe();
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

    private void setupQuery(StatisticsCache cache)
    {


        final String[] maxDate = new String[ 1 ];
        final String[] minDate = new String[ 1 ];

        statisticsService.getRange()
                .doOnNext(item ->
                {
                    maxDate[ 0 ] = item.getMaxDate();
                    minDate[ 0 ] = item.getMinDate();
                })
                .doOnCompleted(() ->
                        {
                            query.setDateTo(maxDate[ 0 ]);
                            cache.getRangeToTextView().setText(maxDate[ 0 ]);
                            cache.getRangeToTextView().setOnClickListener(new DataToOnClickListener(cache, query, cache.getRangeToTextView()));

                            query.setDateFrom(minDate[ 0 ]);
                            cache.getRangeFromTextView().setText(minDate[ 0 ]);
                            cache.getRangeFromTextView().setOnClickListener(new DataFromOnClickListener(cache, query, cache.getRangeFromTextView()));

                            setupSpinner(cache);
                        }

                )
                .doOnError(Throwable::printStackTrace)
                .subscribe();
    }

    private void setupSpinner(StatisticsCache cache)
    {
        String[] groupBySpinner = getResources().getStringArray(R.array.statistics_spinner_group_by);
        ArrayAdapter<String> groupByAdapter = new ArrayAdapter<>(this, R.layout.pfa_lists, groupBySpinner);
        cache.getGroupBySpinner().setAdapter(groupByAdapter);

        String[] valueSpinner = getResources().getStringArray(R.array.statistics_spinner_values);
        ArrayAdapter<String> valueAdapter = new ArrayAdapter<>(this, R.layout.pfa_lists, valueSpinner);
        cache.getValuesSpinner().setAdapter(valueAdapter);

        cache.getGroupBySpinner().setOnItemSelectedListener(new GroupBySpinnerOnItemSelectListener(query));
        query.setGroupBy(cache.getGroupBySpinner().getSelectedItemPosition());

        cache.getValuesSpinner().setOnItemSelectedListener(new ValuesSpinnerOnItemSelectListener(query));
        query.setValuesY(cache.getValuesSpinner().getSelectedItemPosition());

        query.notifyObservers();
    }
}
