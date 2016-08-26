package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.impl;

import android.content.Context;
import org.joda.time.DateTime;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.DB;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.StatisticsService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.domain.StatisticEntryDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.domain.StatisticsChartData;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.domain.StatisticsQuery;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.domain.StatsRangeDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.impl.converter.StatisticsConverterService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.persistence.StatisticsDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.persistence.entity.StatisticEntryEntity;
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
    public Observable<Void> saveRecord(ProductDto dto)
    {
        Observable<Void> observable = Observable
                .fromCallable(() -> saveRecordSync(dto))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private Void saveRecordSync(ProductDto dto)
    {
        StatisticEntryEntity entity = new StatisticEntryEntity();
        converterService.convertDtoToEntity(dto, entity);
        entity.setRecordDate(new Date());
        statisticsDao.save(entity);
        return null;
    }

    @Override
    public Observable<StatisticEntryDto> getAll()
    {
        Observable<StatisticEntryDto> observable = Observable
                .defer(() -> Observable.from(getAllSync()))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private List<StatisticEntryDto> getAllSync()
    {
        List<StatisticEntryDto> dtos = new ArrayList<>();
        Observable
                .from(statisticsDao.getAllEntities())
                .map(this::getDto)
                .subscribe(dto -> dtos.add(dto));

        return dtos;
    }

    @Override
    public Observable<Boolean> deleteAll()
    {
        Observable<Boolean> observable = Observable
                .from(statisticsDao.getAllEntities())
                .map(entity -> statisticsDao.deleteById(entity.getId()))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        return observable;
    }

    @Override
    public Observable<StatsRangeDto> getRange()
    {
        Observable<StatsRangeDto> observable = Observable
                .fromCallable(() -> getRangeSync())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private StatsRangeDto getRangeSync()
    {
        String maxDateSync = getMaxDateSync();
        String minDateSync = getMinDateSync();
        StatsRangeDto dto = new StatsRangeDto();
        dto.setMaxDate(maxDateSync);
        dto.setMindDate(minDateSync);
        return dto;
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
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    @Override
    public Observable<StatisticsChartData> getChartData(StatisticsQuery query)
    {
        Observable<StatisticsChartData> observable = Observable
                .fromCallable(() -> getChartDataSync(query))
                .subscribeOn(Schedulers.newThread())
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
        return chartData;
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

    private StatisticEntryDto getDto(StatisticEntryEntity entryEntity)
    {
        StatisticEntryDto dto = new StatisticEntryDto();
        converterService.convertEntityToDto(entryEntity, dto);
        return dto;
    }

    public String getTitle()
    {
        return yAxisName + " vs. " + xAxisName;
    }
}
