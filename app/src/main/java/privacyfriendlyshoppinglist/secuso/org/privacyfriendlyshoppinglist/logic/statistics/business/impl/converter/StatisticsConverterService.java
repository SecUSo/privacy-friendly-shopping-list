package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.impl.converter;

import org.joda.time.DateTime;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.ContextSetter;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.domain.StatisticEntryDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.persistence.entity.StatisticEntryEntity;

import java.util.Date;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 23.07.16 creation date
 */
public interface StatisticsConverterService extends ContextSetter
{
    void convertDtoToEntity(ProductDto dto, StatisticEntryEntity entryEntity);

    void convertEntityToDto(StatisticEntryEntity entryEntity, StatisticEntryDto dto);

    String getMonthFromDate(Date recordDate);

    String getDayFromDate(Date recordDate);

    String getStringFromDate(Date recordDate);

    DateTime getDateTimeFromString(String date);

    Double getStringAsDouble(String productPrice);
}
