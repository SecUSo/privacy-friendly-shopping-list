package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.impl.converter.impl;

import android.content.Context;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.DB;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.DateUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.StringUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductTemplateDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.impl.converter.ProductConverterService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence.entity.ProductItemEntity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence.entity.ProductTemplateEntity;

import java.util.Date;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 17.07.16 creation date
 */
public class ProductConverterServiceImpl implements ProductConverterService
{
    private String language;
    private String dateLongPattern;
    private String priceFormat0;
    private String priceFormat1;
    private String priceFormat2;

    @Override
    public void setContext(Context context, DB db)
    {
        this.language = context.getResources().getString(R.string.language);
        this.dateLongPattern = context.getResources().getString(R.string.date_long_pattern);
        this.priceFormat0 = context.getResources().getString(R.string.number_format_0_decimals);
        this.priceFormat1 = context.getResources().getString(R.string.number_format_1_decimal);
        this.priceFormat2 = context.getResources().getString(R.string.number_format_2_decimals);

    }

    @Override
    public void convertDtoToEntity(ProductDto dto, ProductItemEntity entity)
    {
        if ( !StringUtils.isEmpty(dto.getProductId()) )
        {
            entity.setId(Long.valueOf(dto.getProductId()));
        }

        if ( !StringUtils.isEmpty(dto.getQuantity()) )
        {
            entity.setQuantity(Integer.valueOf(dto.getQuantity()));
        }
        else
        {
            entity.setQuantity(0);
        }

        if ( !StringUtils.isEmpty(dto.getQuantityPurchased()) )
        {
            entity.setQuantityPurchased(Integer.valueOf(dto.getQuantityPurchased()));
        }
        else
        {
            entity.setQuantityPurchased(0);
        }
        entity.setNotes(dto.getProductNotes());
        entity.setStore(dto.getProductStore());

        String priceString = dto.getProductPrice();
        if ( !StringUtils.isEmpty(priceString) )
        {
            Double price = getStringAsDouble(priceString);
            entity.setPrice(price);
        }
        else
        {
            entity.setPrice(0.0);
        }

        if ( !StringUtils.isEmpty(dto.getLastTimePurchased()) )
        {
            Date purchasedDate = DateUtils.getDateFromString(dto.getLastTimePurchased(), dateLongPattern, language).toDate();
            entity.setPurchasedDate(purchasedDate);
        }

        entity.setSelected(dto.isChecked());
    }

    @Override
    public void convertDtoToTemplateEntity(ProductDto dto, ProductTemplateEntity entity)
    {
        if ( !StringUtils.isEmpty(dto.getId()) )
        {
            entity.setId(Long.valueOf(dto.getId()));
        }
        entity.setProductName(dto.getProductName());
        entity.setCategory(dto.getProductCategory());

        if ( !StringUtils.isEmpty(dto.getHistoryCount()) )
        {
            entity.setHistoryCount(Integer.valueOf(dto.getHistoryCount()));
        }

        if ( !StringUtils.isEmpty(dto.getLastTimePurchased()) )
        {
            Date purchasedDate = DateUtils.getDateFromString(dto.getLastTimePurchased(), dateLongPattern, language).toDate();
            entity.setLastTimePurchased(purchasedDate);
        }

        entity.setDefaultNotes(dto.getDefaultNotes());
        entity.setDefaultStore(dto.getDefaultStore());
    }

    @Override
    public void convertTemplateEntityToDto(ProductTemplateEntity entity, ProductTemplateDto dto)
    {
        if ( entity.getId() != null )
        {
            dto.setId(String.valueOf(entity.getId()));
        }

        dto.setProductName(entity.getProductName());
        dto.setProductCategory(entity.getCategory());

        if ( entity.getHistoryCount() != null )
        {
            dto.setHistoryCount(String.valueOf(entity.getHistoryCount()));
        }

        if ( entity.getLastTimePurchased() != null )
        {
            String dateAsString = DateUtils.getDateAsString(entity.getLastTimePurchased().getTime(), dateLongPattern, language);
            dto.setLastTimePurchased(dateAsString);
        }

        dto.setDefaultNotes(entity.getDefaultNotes());
        dto.setDefaultStore(entity.getDefaultStore());
    }

    @Override
    public void convertEntitiesToDto(ProductTemplateEntity templateEntity, ProductItemEntity entity, ProductDto dto)
    {

        convertTemplateEntityToDto(templateEntity, dto);

        // from product
        if ( entity.getId() != null )
        {
            dto.setProductId(String.valueOf(entity.getId()));
        }

        if ( entity.getQuantity() != null )
        {
            dto.setQuantity(String.valueOf(entity.getQuantity()));
        }

        if ( entity.getQuantityPurchased() != null )
        {
            dto.setQuantityPurchased(String.valueOf(entity.getQuantityPurchased()));
        }
        dto.setProductNotes(entity.getNotes());
        dto.setProductStore(entity.getStore());

        if ( entity.getPrice() != null )
        {
            dto.setProductPrice(getDoubleAsString(entity.getPrice()));
        }

        if ( entity.getQuantity() != null && entity.getPrice() != null )
        {
            dto.setTotalProductPrice(getDoubleAsString(entity.getPrice() * entity.getQuantity()));
        }

        if ( entity.getPurchasedDate() != null )
        {
            String dateAsString = DateUtils.getDateAsString(entity.getPurchasedDate().getTime(), dateLongPattern, language);
            dto.setLastTimePurchased(dateAsString);
        }
        dto.setChecked(entity.getSelected());
    }

    @Override
    public String getDoubleAsString(Double price)
    {
        return StringUtils.getDoubleAsString(price, priceFormat2);
    }

    @Override
    public Double getStringAsDouble(String priceString)
    {
        return StringUtils.getStringAsDouble(priceString, priceFormat2, priceFormat1, priceFormat0);
    }
}
