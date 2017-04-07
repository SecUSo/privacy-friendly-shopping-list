package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.impl;

import android.content.Context;
import org.joda.time.DateTime;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.DB;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.StatisticsService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.domain.StatisticEntryItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.domain.StatisticsChartData;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.domain.StatisticsQuery;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.domain.StatsRangeItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.impl.converter.StatisticsConverterService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.persistence.StatisticsDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.persistence.entity.StatisticEntryEntity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.statistics.chart.NumberScale;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import javax.inject.Inject;
import java.util.*;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 23.07.16 creation date
 */
public class StatisticsServiceImpl implements StatisticsService
{
    private static final int MAX_NR_CHAR = 10;
    private static final String SPACE = " ";
    private static final String RIGHT_PARENTHESIS = ")";
    private static final String LEFT_PARENTHESIS = "(";
    private StatisticsDao statisticsDao;
    private StatisticsConverterService converterService;
    private Context context;
    private String yAxisName;
    private String xAxisName;

    @Inject
    public StatisticsServiceImpl(
            StatisticsDao statisticsDao,
            StatisticsConverterService converterService
    )
    {
        this.statisticsDao = statisticsDao;
        this.converterService = converterService;
    }

    @Override
    public void setContext(Context context, DB db)
    {
        statisticsDao.setContext(context, db);
        converterService.setContext(context, db);
        this.context = context;
    }

    @Override
    public Observable<Void> saveRecord(ProductItem item)
    {
        Observable<Void> observable = Observable
                .fromCallable(() -> saveRecordSync(item))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private Void saveRecordSync(ProductItem item)
    {
        StatisticEntryEntity entity = new StatisticEntryEntity();
        converterService.convertItemToEntity(item, entity);
        entity.setRecordDate(new Date());
        statisticsDao.save(entity);
        return null;
    }

    @Override
    public Observable<StatisticEntryItem> getAll()
    {
        Observable<StatisticEntryItem> observable = Observable
                .defer(() -> Observable.from(getAllSync()))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private List<StatisticEntryItem> getAllSync()
    {
        List<StatisticEntryItem> items = new ArrayList<>();
        Observable
                .from(statisticsDao.getAllEntities())
                .map(this::getItem)
                .subscribe(item -> items.add(item));

        return items;
    }

    @Override
    public Observable<Boolean> deleteAll()
    {
        Observable<Boolean> observable = Observable
                .from(statisticsDao.getAllEntities())
                .map(entity -> statisticsDao.deleteById(entity.getId()))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());

        return observable;
    }

    @Override
    public Observable<StatsRangeItem> getRange()
    {
        Observable<StatsRangeItem> observable = Observable
                .fromCallable(() -> getRangeSync())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private StatsRangeItem getRangeSync()
    {
        String maxDateSync = getMaxDateSync();
        String minDateSync = getMinDateSync();
        StatsRangeItem item = new StatsRangeItem();
        item.setMaxDate(maxDateSync);
        item.setMinDate(minDateSync);
        return item;
    }

    private String getMaxDateSync()
    {
        List<StatisticEntryEntity> entities = statisticsDao.getAllEntities();
        Date maxDate = new Date();
        if ( !entities.isEmpty() )
        {
            maxDate = Observable
                    .from(entities)
                    .map(StatisticEntryEntity::getRecordDate)
                    .reduce((date1, date2) -> date1.getTime() > date2.getTime() ? date1 : date2)
                    .toSingle().toBlocking().value();
        }

        return converterService.getStringFromDate(maxDate);
    }

    private String getMinDateSync()
    {
        List<StatisticEntryEntity> entities = statisticsDao.getAllEntities();
        Date minDate = new Date();
        if ( !entities.isEmpty() )
        {
            minDate = Observable
                    .from(entities)
                    .map(StatisticEntryEntity::getRecordDate)
                    .reduce((date1, date2) -> date1.getTime() < date2.getTime() ? date1 : date2)
                    .toSingle().toBlocking().value();
        }

        return converterService.getStringFromDate(minDate);
    }

    @Override
    public Observable<Boolean> deleteById(String id)
    {
        Observable<Boolean> observable = Observable
                .fromCallable(() -> statisticsDao.deleteById(Long.valueOf(id)))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    @Override
    public Observable<StatisticsChartData> getChartData(StatisticsQuery query)
    {
        Observable<StatisticsChartData> observable = Observable
                .fromCallable(() -> getChartDataSync(query))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private StatisticsChartData getChartDataSync(StatisticsQuery query)
    {
        DateTime dateFrom = converterService.getDateTimeFromString(query.getDateFrom());
        DateTime dateTo = converterService.getDateTimeFromString(query.getDateTo());
        int groupBy = query.getGroupBy();
        int valuesIndex = query.getValuesY();
        setAxisNames(groupBy, valuesIndex);

        // filter by Range
        List<StatisticEntryEntity> filteredEntities = Observable
                .from(statisticsDao.getAllEntities())
                .filter(entryEntity ->
                {
                    DateTime recordDate = new DateTime(entryEntity.getRecordDate());
                    return recordDate.isBefore(dateTo.plusDays(1)) && recordDate.isAfter(dateFrom.minusDays(1));
                }).toSortedList((entity1, entity2) -> entity1.getRecordDate().compareTo(entity2.getRecordDate()))
                .toBlocking().single();

        StatisticsChartData chartData = getStatisticsChartData(groupBy, valuesIndex, filteredEntities);

        double maxColumnValue = getMaxColumnValue(chartData);
        chartData.setNumberScale(getNumberScale(maxColumnValue));

        return chartData;
    }

    private NumberScale getNumberScale(double maxColumnValue)
    {
        NumberScale numberScale = null;
        if ( maxColumnValue > NumberScale.MILLION.getValue(context) )
        {
            numberScale = NumberScale.MILLION;
        }
        else if ( maxColumnValue > NumberScale.KILO.getValue(context) )
        {
            numberScale = NumberScale.KILO;
        }
        return numberScale;
    }

    private double getMaxColumnValue(StatisticsChartData chartData)
    {
        List<Double> numberData = chartData.getData();
        Double max = Double.MIN_VALUE;
        for ( Double number : numberData )
        {
            if ( number > max )
            {
                max = number;
            }
        }
        return max;
    }

    private void setAxisNames(int groupBy, int valuesIndex)
    {
        String[] groupByStrings = context.getResources().getStringArray(R.array.statistics_spinner_group_by);
        String[] valuesStrings = context.getResources().getStringArray(R.array.statistics_spinner_values);

        xAxisName = groupByStrings[ groupBy ];
        yAxisName = valuesStrings[ valuesIndex ];
    }

    private StatisticsChartData getStatisticsChartData(int groupBy, int valuesIndex, List<StatisticEntryEntity> filteredEntities)
    {
        StatisticsChartData chartData = null;
        if ( groupBy == StatisticsQuery.MONTH )
        {
            chartData = getStatisticsChartDataByMonth(valuesIndex, filteredEntities);
        }
        else if ( groupBy == StatisticsQuery.WEEK )
        {
            chartData = getStatisticsChartDataByWeek(valuesIndex, filteredEntities);
        }
        else if ( groupBy == StatisticsQuery.DAY )
        {
            chartData = getStatisticsChartDataByDay(valuesIndex, filteredEntities);
        }
        else if ( groupBy == StatisticsQuery.CATEGORY )
        {
            chartData = getStatisticsChartDataByCategory(valuesIndex, filteredEntities);
        }
        else if ( groupBy == StatisticsQuery.STORE )
        {
            chartData = getStatisticsChartDataByStore(valuesIndex, filteredEntities);
        }
        else if ( groupBy == StatisticsQuery.PRODUCT )
        {
            chartData = getStatisticsChartDataByProduct(valuesIndex, filteredEntities);
        }
        return chartData;
    }

    private StatisticsChartData getStatisticsChartDataByMonth(int valuesIndex, List<StatisticEntryEntity> filteredEntities)
    {
        Map<String, Double> chartMap = new TreeMap<>();
        List<String> labels = new ArrayList<>();
        Double total = 0.0;

        for ( StatisticEntryEntity entity : filteredEntities )
        {
            double currentTotal = getCurrentTotal(valuesIndex, entity);
            total += currentTotal;

            String month = converterService.getMonthFromDate(entity.getRecordDate());

            Double value = chartMap.get(month);
            if ( value == null )
            {
                chartMap.put(month, currentTotal);
                labels.add(month);
            }
            else
            {
                chartMap.put(month, value + currentTotal);
            }
        }

        StatisticsChartData chartData = new StatisticsChartData();
        chartData.setTitle(getTitle());
        return setupChartData(chartMap, labels, total, chartData);
    }

    private StatisticsChartData getStatisticsChartDataByWeek(int valuesIndex, List<StatisticEntryEntity> filteredEntities)
    {
        Map<String, Double> chartMap = new TreeMap<>();
        List<String> labels = new ArrayList<>();
        Double total = 0.0;

        for ( StatisticEntryEntity entity : filteredEntities )
        {
            double currentTotal = getCurrentTotal(valuesIndex, entity);
            total += currentTotal;

            DateTime dateTime = new DateTime(entity.getRecordDate()).withDayOfWeek(1);

            String monthFromDate = converterService.getMonthFromDate(dateTime.toDate());
            int weekOfYear = dateTime.weekOfWeekyear().get();
            String week = LEFT_PARENTHESIS + String.valueOf(weekOfYear) + RIGHT_PARENTHESIS + SPACE + monthFromDate;

            Double value = chartMap.get(week);
            if ( value == null )
            {
                chartMap.put(week, currentTotal);
                labels.add(week);
            }
            else
            {
                chartMap.put(week, value + currentTotal);
            }
        }

        StatisticsChartData chartData = new StatisticsChartData();
        chartData.setTitle(getTitle());
        return setupChartData(chartMap, labels, total, chartData);
    }

    private StatisticsChartData getStatisticsChartDataByDay(int valuesIndex, List<StatisticEntryEntity> filteredEntities)
    {
        Map<String, Double> chartMap = new TreeMap<>();
        List<String> labels = new ArrayList<>();
        Double total = 0.0;

        for ( StatisticEntryEntity entity : filteredEntities )
        {
            double currentTotal = getCurrentTotal(valuesIndex, entity);
            total += currentTotal;

            String day = converterService.getDayFromDate(entity.getRecordDate());

            Double value = chartMap.get(day);
            if ( value == null )
            {
                chartMap.put(day, currentTotal);
                labels.add(day);
            }
            else
            {
                chartMap.put(day, value + currentTotal);
            }
        }

        StatisticsChartData chartData = new StatisticsChartData();
        chartData.setTitle(getTitle());
        return setupChartData(chartMap, labels, total, chartData);
    }

    private StatisticsChartData getStatisticsChartDataByCategory(int valuesIndex, List<StatisticEntryEntity> filteredEntities)
    {
        Map<String, Double> chartMap = new TreeMap<>();
        List<String> labels = new ArrayList<>();
        Double total = 0.0;

        for ( StatisticEntryEntity entity : filteredEntities )
        {
            double currentTotal = getCurrentTotal(valuesIndex, entity);
            total += currentTotal;

            String fullCategoryName = entity.getProductCategory();
            String category = fullCategoryName.substring(0, Math.min(MAX_NR_CHAR, fullCategoryName.length()));

            Double value = chartMap.get(category);
            if ( value == null )
            {
                chartMap.put(category, currentTotal);
                labels.add(category);
            }
            else
            {
                chartMap.put(category, value + currentTotal);
            }
        }

        StatisticsChartData chartData = new StatisticsChartData();
        chartData.setTitle(getTitle());
        return setupChartData(chartMap, labels, total, chartData);
    }

    private StatisticsChartData getStatisticsChartDataByStore(int valuesIndex, List<StatisticEntryEntity> filteredEntities)
    {
        Map<String, Double> chartMap = new TreeMap<>();
        List<String> labels = new ArrayList<>();
        Double total = 0.0;

        for ( StatisticEntryEntity entity : filteredEntities )
        {
            double currentTotal = getCurrentTotal(valuesIndex, entity);
            total += currentTotal;

            String fullStoreName = entity.getProductStore();
            String store = fullStoreName.substring(0, Math.min(MAX_NR_CHAR, fullStoreName.length()));

            Double value = chartMap.get(store);
            if ( value == null )
            {
                chartMap.put(store, currentTotal);
                labels.add(store);
            }
            else
            {
                chartMap.put(store, value + currentTotal);
            }
        }

        StatisticsChartData chartData = new StatisticsChartData();
        chartData.setTitle(getTitle());
        return setupChartData(chartMap, labels, total, chartData);
    }

    private StatisticsChartData getStatisticsChartDataByProduct(int valuesIndex, List<StatisticEntryEntity> filteredEntities)
    {
        Map<String, Double> chartMap = new TreeMap<>();
        List<String> labels = new ArrayList<>();
        Double total = 0.0;

        for ( StatisticEntryEntity entity : filteredEntities )
        {
            double currentTotal = getCurrentTotal(valuesIndex, entity);
            total += currentTotal;

            String fullProductName = entity.getProductName();
            String productName = fullProductName.substring(0, Math.min(MAX_NR_CHAR, fullProductName.length()));

            Double value = chartMap.get(productName);
            if ( value == null )
            {
                chartMap.put(productName, currentTotal);
                labels.add(productName);
            }
            else
            {
                chartMap.put(productName, value + currentTotal);
            }
        }

        StatisticsChartData chartData = new StatisticsChartData();
        chartData.setTitle(getTitle());
        return setupChartData(chartMap, labels, total, chartData);
    }

    private double getCurrentTotal(int valuesIndex, StatisticEntryEntity entity)
    {
        double currentTotal;
        if ( valuesIndex == StatisticsQuery.PRICE )
        {
            currentTotal = entity.getUnitPrice() * entity.getQuantity();
        }
        else
        {
            currentTotal = entity.getQuantity();
        }
        return currentTotal;
    }

    private StatisticsChartData setupChartData(Map<String, Double> chartMap, List<String> labels, Double total, StatisticsChartData chartData)
    {
        chartData.setTotal(total);
        chartData.setLabels(labels);

        List<Double> values = new ArrayList<>();
        for ( String label : labels )
        {
            values.add(chartMap.get(label));
        }
        chartData.setData(values);
        return chartData;
    }

    private StatisticEntryItem getItem(StatisticEntryEntity entryEntity)
    {
        StatisticEntryItem item = new StatisticEntryItem();
        converterService.convertEntityToItem(entryEntity, item);
        return item;
    }

    public String getTitle()
    {
        return yAxisName + " vs. " + xAxisName;
    }
}
