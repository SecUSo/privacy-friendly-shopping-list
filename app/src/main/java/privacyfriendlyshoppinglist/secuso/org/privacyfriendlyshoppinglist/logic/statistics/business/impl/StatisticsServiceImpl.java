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
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.impl.converter.StatisticsConverterService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.persistence.StatisticsDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.persistence.entity.StatisticEntryEntity;
import rx.Observable;

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
    public void saveRecord(ProductDto dto)
    {
        StatisticEntryEntity entity = new StatisticEntryEntity();
        converterService.convertDtoToEntity(dto, entity);
        entity.setRecordDate(new Date());
        statisticsDao.save(entity);
    }

    @Override
    public List<StatisticEntryDto> getAll()
    {
        Observable<StatisticEntryDto> dtos = Observable
                .from(statisticsDao.getAllEntities())
                .map(this::getDto);
        return dtos.toList().toBlocking().single();
    }

    @Override
    public void deleteAll()
    {
        Observable
                .from(statisticsDao.getAllEntities())
                .map(entity -> statisticsDao.deleteById(entity.getId()))
                .subscribe();
    }

    @Override
    public String getMaxDate()
    {
        Date maxDate = Observable
                .from(statisticsDao.getAllEntities())
                .map(StatisticEntryEntity::getRecordDate)
                .reduce((date1, date2) -> date1.getTime() > date2.getTime() ? date1 : date2)
                .toSingle().toBlocking().value();

        return converterService.getStringFromDate(maxDate);
    }

    @Override
    public String getMinDate()
    {
        Date maxDate = Observable
                .from(statisticsDao.getAllEntities())
                .map(StatisticEntryEntity::getRecordDate)
                .reduce((date1, date2) -> date1.getTime() < date2.getTime() ? date1 : date2)
                .toSingle().toBlocking().value();

        return converterService.getStringFromDate(maxDate);
    }

    @Override
    public void deleteById(String id)
    {
        statisticsDao.deleteById(Long.valueOf(id));
    }

    @Override
    public StatisticsChartData getChartData(StatisticsQuery query)
    {
        DateTime dateFrom = converterService.getDateTimeFromString(query.getDateFrom());
        DateTime dateTo = converterService.getDateTimeFromString(query.getDateTo());
        int groupBy = query.getGroupBy();

        // filter by Range
        List<StatisticEntryEntity> filteredEntities = Observable
                .from(statisticsDao.getAllEntities())
                .filter(entryEntity ->
                {
                    DateTime recordDate = new DateTime(entryEntity.getRecordDate());
                    return recordDate.isBefore(dateTo.plusDays(1)) && recordDate.isAfter(dateFrom.minusDays(1));
                }).toSortedList((entity1, entity2) -> entity1.getRecordDate().compareTo(entity2.getRecordDate()))
                .toBlocking().single();

        StatisticsChartData chartData = getStatisticsChartData(groupBy, filteredEntities);
        return chartData;
    }

    private StatisticsChartData getStatisticsChartData(int groupBy, List<StatisticEntryEntity> filteredEntities)
    {
        StatisticsChartData chartData = null;
        if ( groupBy == StatisticsQuery.MONTH )
        {
            chartData = getStatisticsChartDataByMonth(filteredEntities);
        }
        else if ( groupBy == StatisticsQuery.WEEK )
        {
            chartData = getStatisticsChartDataByWeek(filteredEntities);
        }
        else if ( groupBy == StatisticsQuery.DAY )
        {
            chartData = getStatisticsChartDataByDay(filteredEntities);
        }
        else if ( groupBy == StatisticsQuery.CATEGORY )
        {
            chartData = getStatisticsChartDataByCategory(filteredEntities);
        }
        else if ( groupBy == StatisticsQuery.STORE )
        {
            chartData = getStatisticsChartDataByStore(filteredEntities);
        }
        else if ( groupBy == StatisticsQuery.PRODUCT )
        {
            chartData = getStatisticsChartDataByProduct(filteredEntities);
        }
        return chartData;
    }

    private StatisticsChartData getStatisticsChartDataByMonth(List<StatisticEntryEntity> filteredEntities)
    {
        Map<String, Double> chartMap = new TreeMap<>();
        List<String> labels = new ArrayList<>();
        Double total = 0.0;

        for ( StatisticEntryEntity entity : filteredEntities )
        {
            double currentAmount = entity.getUnitPrice() * entity.getQuantity();
            total += currentAmount;
            String month = converterService.getMonthFromDate(entity.getRecordDate());

            Double value = chartMap.get(month);
            if ( value == null )
            {
                chartMap.put(month, currentAmount);
                labels.add(month);
            }
            else
            {
                chartMap.put(month, value + currentAmount);
            }
        }

        StatisticsChartData chartData = new StatisticsChartData();
        chartData.setTitle(context.getResources().getString(R.string.statistics_amount_vs_months));
        return setupChartData(chartMap, labels, total, chartData);
    }

    private StatisticsChartData getStatisticsChartDataByWeek(List<StatisticEntryEntity> filteredEntities)
    {
        Map<String, Double> chartMap = new TreeMap<>();
        List<String> labels = new ArrayList<>();
        Double total = 0.0;

        for ( StatisticEntryEntity entity : filteredEntities )
        {
            double currentAmount = entity.getUnitPrice() * entity.getQuantity();
            total += currentAmount;

            DateTime dateTime = new DateTime(entity.getRecordDate()).withDayOfWeek(1);

            String monthFromDate = converterService.getMonthFromDate(dateTime.toDate());
            int weekOfYear = dateTime.weekOfWeekyear().get();
            String week = LEFT_PARENTHESIS + String.valueOf(weekOfYear) + RIGHT_PARENTHESIS + SPACE + monthFromDate;

            Double value = chartMap.get(week);
            if ( value == null )
            {
                chartMap.put(week, currentAmount);
                labels.add(week);
            }
            else
            {
                chartMap.put(week, value + currentAmount);
            }
        }

        StatisticsChartData chartData = new StatisticsChartData();
        chartData.setTitle(context.getResources().getString(R.string.statistics_amount_vs_weeks));
        return setupChartData(chartMap, labels, total, chartData);
    }

    private StatisticsChartData getStatisticsChartDataByDay(List<StatisticEntryEntity> filteredEntities)
    {
        Map<String, Double> chartMap = new TreeMap<>();
        List<String> labels = new ArrayList<>();
        Double total = 0.0;

        for ( StatisticEntryEntity entity : filteredEntities )
        {
            double currentAmount = entity.getUnitPrice() * entity.getQuantity();
            total += currentAmount;

            String day = converterService.getDayFromDate(entity.getRecordDate());

            Double value = chartMap.get(day);
            if ( value == null )
            {
                chartMap.put(day, currentAmount);
                labels.add(day);
            }
            else
            {
                chartMap.put(day, value + currentAmount);
            }
        }

        StatisticsChartData chartData = new StatisticsChartData();
        chartData.setTitle(context.getResources().getString(R.string.statistics_amount_vs_days));
        return setupChartData(chartMap, labels, total, chartData);
    }

    private StatisticsChartData getStatisticsChartDataByCategory(List<StatisticEntryEntity> filteredEntities)
    {
        Map<String, Double> chartMap = new TreeMap<>();
        List<String> labels = new ArrayList<>();
        Double total = 0.0;

        for ( StatisticEntryEntity entity : filteredEntities )
        {
            double currentAmount = entity.getUnitPrice() * entity.getQuantity();
            total += currentAmount;

            String fullCategoryName = entity.getProductCategory();
            String category = fullCategoryName.substring(0, Math.min(MAX_NR_CHAR, fullCategoryName.length()));

            Double value = chartMap.get(category);
            if ( value == null )
            {
                chartMap.put(category, currentAmount);
                labels.add(category);
            }
            else
            {
                chartMap.put(category, value + currentAmount);
            }
        }

        StatisticsChartData chartData = new StatisticsChartData();
        chartData.setTitle(context.getResources().getString(R.string.statistics_amount_vs_categories));
        return setupChartData(chartMap, labels, total, chartData);
    }

    private StatisticsChartData getStatisticsChartDataByStore(List<StatisticEntryEntity> filteredEntities)
    {
        Map<String, Double> chartMap = new TreeMap<>();
        List<String> labels = new ArrayList<>();
        Double total = 0.0;

        for ( StatisticEntryEntity entity : filteredEntities )
        {
            double currentAmount = entity.getUnitPrice() * entity.getQuantity();
            total += currentAmount;

            String fullStoreName = entity.getProductStore();
            String store = fullStoreName.substring(0, Math.min(MAX_NR_CHAR, fullStoreName.length()));

            Double value = chartMap.get(store);
            if ( value == null )
            {
                chartMap.put(store, currentAmount);
                labels.add(store);
            }
            else
            {
                chartMap.put(store, value + currentAmount);
            }
        }

        StatisticsChartData chartData = new StatisticsChartData();
        chartData.setTitle(context.getResources().getString(R.string.statistics_amount_vs_stores));
        return setupChartData(chartMap, labels, total, chartData);
    }

    private StatisticsChartData getStatisticsChartDataByProduct(List<StatisticEntryEntity> filteredEntities)
    {
        Map<String, Double> chartMap = new TreeMap<>();
        List<String> labels = new ArrayList<>();
        Double total = 0.0;

        for ( StatisticEntryEntity entity : filteredEntities )
        {
            double currentAmount = entity.getUnitPrice() * entity.getQuantity();
            total += currentAmount;

            String fullProductName = entity.getProductName();
            String productName = fullProductName.substring(0, Math.min(MAX_NR_CHAR, fullProductName.length()));

            Double value = chartMap.get(productName);
            if ( value == null )
            {
                chartMap.put(productName, currentAmount);
                labels.add(productName);
            }
            else
            {
                chartMap.put(productName, value + currentAmount);
            }
        }

        StatisticsChartData chartData = new StatisticsChartData();
        chartData.setTitle(context.getResources().getString(R.string.statistics_amount_vs_products));
        return setupChartData(chartMap, labels, total, chartData);
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
}
