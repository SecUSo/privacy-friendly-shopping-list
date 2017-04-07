package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.impl.converter;

import org.joda.time.DateTime;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.ContextSetter;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.domain.StatisticEntryItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.persistence.entity.StatisticEntryEntity;

import java.util.Date;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 23.07.16 creation date
 */
public interface StatisticsConverterService extends ContextSetter
{
    void convertItemToEntity(ProductItem item, StatisticEntryEntity entryEntity);

    void convertEntityToItem(StatisticEntryEntity entryEntity, StatisticEntryItem item);

    String getMonthFromDate(Date recordDate);

    String getDayFromDate(Date recordDate);

    String getStringFromDate(Date recordDate);

    DateTime getDateTimeFromString(String date);

    Double getStringAsDouble(String productPrice);
}
