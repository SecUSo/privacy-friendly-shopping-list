package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business;

import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.ContextSetter;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.domain.StatisticEntryItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.domain.StatisticsChartData;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.domain.StatisticsQuery;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.domain.StatsRangeItem;
import rx.Observable;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 23.07.16 creation date
 */
public interface StatisticsService extends ContextSetter
{
    Observable<Void> saveRecord(ProductItem item);

    Observable<StatisticEntryItem> getAll();

    Observable<Boolean> deleteAll();

    Observable<StatsRangeItem> getRange();

    Observable<StatisticsChartData> getChartData(StatisticsQuery query);

    Observable<Boolean> deleteById(String id);
}
