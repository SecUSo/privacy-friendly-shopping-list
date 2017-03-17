package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.impl.converter.impl;

import android.content.Context;
import org.joda.time.DateTime;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.DB;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.DateUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.StringUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.domain.StatisticEntryItem;
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
    private String priceFormat0;
    private String priceFormat1;
    private String priceFormat2;

    @Override
    public void setContext(Context context, DB db)
    {
        this.language = context.getResources().getString(R.string.language);
        this.dateShortPattern = context.getResources().getString(R.string.date_short_pattern);
        this.dateMonthPattern = context.getResources().getString(R.string.date_month_pattern);
        this.dateDayPattern = context.getResources().getString(R.string.date_day_stats_pattern);
        this.priceFormat0 = context.getResources().getString(R.string.number_format_0_decimals);
        this.priceFormat1 = context.getResources().getString(R.string.number_format_1_decimal);
        this.priceFormat2 = context.getResources().getString(R.string.number_format_2_decimals);
    }

    @Override
    public void convertItemToEntity(ProductItem item, StatisticEntryEntity entryEntity)
    {
        entryEntity.setProductName(item.getProductName());
        entryEntity.setProductCategory(item.getProductCategory());

        if ( !StringUtils.isEmpty(item.getQuantity()) )
        {
            entryEntity.setQuantity(Integer.valueOf(item.getQuantity()));
        }
        else
        {
            entryEntity.setQuantity(0);
        }

        entryEntity.setProductStore(item.getProductStore());

        String productPrice = item.getProductPrice();
        if ( !StringUtils.isEmpty(productPrice) )
        {
            entryEntity.setUnitPrice(getStringAsDouble(productPrice));
        }
        else
        {
            entryEntity.setUnitPrice(0.0);
        }
    }

    @Override
    public void convertEntityToItem(StatisticEntryEntity entryEntity, StatisticEntryItem item)
    {
        item.setProductName(entryEntity.getProductName());
        item.setProductStore(entryEntity.getProductStore());
        item.setProductCategory(item.getProductCategory());

        if ( entryEntity.getQuantity() != null )
        {
            item.setQuantity(String.valueOf(entryEntity.getQuantity()));
        }

        Date recordDate = entryEntity.getRecordDate();

        if ( recordDate != null )
        {
            String dateAsString = getStringFromDate(recordDate);
            item.setRecordDate(dateAsString);
        }

        if ( entryEntity.getUnitPrice() != null )
        {
            item.setUnitPrice(StringUtils.getDoubleAsString(entryEntity.getUnitPrice(), priceFormat2));
        }
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
        return StringUtils.getStringAsDouble(productPrice, priceFormat2, priceFormat1, priceFormat0);
    }
}
