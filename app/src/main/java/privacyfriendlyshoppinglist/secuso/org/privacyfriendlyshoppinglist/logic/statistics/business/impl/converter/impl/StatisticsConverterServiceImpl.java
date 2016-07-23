package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.impl.converter.impl;

import android.content.Context;
import org.joda.time.DateTime;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.DB;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.DateUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.StringUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.domain.StatisticEntryDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.impl.converter.StatisticsConverterService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.persistence.entity.StatisticEntryEntity;

import java.util.Date;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 23.07.16 creation date
 */
public class StatisticsConverterServiceImpl implements StatisticsConverterService
{
    private String language;
    private String dateShortPattern;
    private String dateMonthPattern;
    private String dateDayPattern;
    private String priceFormat;

    @Override
    public void setContext(Context context, DB db)
    {
        this.language = context.getResources().getString(R.string.language);
        this.dateShortPattern = context.getResources().getString(R.string.date_short_pattern);
        this.dateMonthPattern = context.getResources().getString(R.string.date_month_pattern);
        this.dateDayPattern = context.getResources().getString(R.string.date_day_stats_pattern);
        this.priceFormat = context.getResources().getString(R.string.number_format);
    }

    @Override
    public void convertDtoToEntity(ProductDto dto, StatisticEntryEntity entryEntity)
    {
        entryEntity.setProductName(dto.getProductName());
        entryEntity.setProductCategory(dto.getProductCategory());
        entryEntity.setQuantity(Integer.valueOf(dto.getQuantityPurchased()));
        entryEntity.setProductStore(dto.getProductStore());

        String date = dto.getLastTimePurchased();
        Date purchasedDate = getDateTimeFromString(date).toDate();
        entryEntity.setRecordDate(purchasedDate);

        String productPrice = dto.getProductPrice();
        entryEntity.setUnitPrice(getStringAsDouble(productPrice));
    }

    @Override
    public void convertEntityToDto(StatisticEntryEntity entryEntity, StatisticEntryDto dto)
    {
        dto.setProductName(entryEntity.getProductName());
        dto.setProductStore(entryEntity.getProductStore());
        dto.setProductCategory(dto.getProductCategory());
        dto.setQuantity(String.valueOf(entryEntity.getQuantity()));

        Date recordDate = entryEntity.getRecordDate();
        String dateAsString = getStringFromDate(recordDate);
        dto.setRecordDate(dateAsString);

        dto.setUnitPrice(StringUtils.getDoubleAsString(entryEntity.getUnitPrice(), priceFormat));
    }

    @Override
    public String getMonthFromDate(Date recordDate)
    {
        return DateUtils.getDateAsString(recordDate.getTime(), dateMonthPattern, language);
    }

    @Override
    public String getDayFromDate(Date recordDate)
    {
        return DateUtils.getDateAsString(recordDate.getTime(), dateDayPattern, language);
    }

    @Override
    public String getStringFromDate(Date recordDate)
    {
        return DateUtils.getDateAsString(recordDate.getTime(), dateShortPattern, language);
    }

    @Override
    public DateTime getDateTimeFromString(String date)
    {
        return DateUtils.getDateFromString(date, dateShortPattern, language);
    }

    @Override
    public Double getStringAsDouble(String productPrice)
    {
        return StringUtils.getStringAsDouble(productPrice, priceFormat);
    }
}
