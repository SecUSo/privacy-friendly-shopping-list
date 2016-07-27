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
    private String priceFormat;

    @Override
    public void setContext(Context context, DB db)
    {
        this.language = context.getResources().getString(R.string.language);
        this.dateLongPattern = context.getResources().getString(R.string.date_long_pattern);
        this.priceFormat = context.getResources().getString(R.string.number_format);

    }

    @Override
    public void convertDtoToEntity(ProductDto dto, ProductItemEntity entity)
    {
        if ( dto.getProductId() != null )
        {
            entity.setId(Long.valueOf(dto.getProductId()));
        }
        entity.setQuantity(Integer.valueOf(dto.getQuantity()));

        // TODO Implement or Remove
        if ( dto.getQuantityPurchased() != null )
        {
            entity.setQuantityPurchased(Integer.valueOf(dto.getQuantityPurchased()));
        }
        entity.setNotes(dto.getProductNotes());
        entity.setStore(dto.getProductStore());

        String priceString = dto.getProductPrice();
        Double price = getStringAsDouble(priceString);
        entity.setPrice(price);

        if ( dto.getLastTimePurchased() != null )
        {
            Date purchasedDate = DateUtils.getDateFromString(dto.getLastTimePurchased(), dateLongPattern, language).toDate();
            entity.setPurchasedDate(purchasedDate);
        }

        entity.setSelected(dto.isChecked());
    }

    @Override
    public void convertDtoToTemplateEntity(ProductDto dto, ProductTemplateEntity entity)
    {
        if ( dto.getId() != null )
        {
            entity.setId(Long.valueOf(dto.getId()));
        }
        entity.setProductName(dto.getProductName());
        entity.setCategory(dto.getProductCategory());

        if ( dto.getHistoryCount() != null )
        {
            entity.setHistoryCount(Integer.valueOf(dto.getHistoryCount()));
        }

        if ( dto.getLastTimePurchased() != null )
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
        dto.setId(String.valueOf(entity.getId()));
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
        dto.setProductId(String.valueOf(entity.getId()));
        dto.setQuantity(String.valueOf(entity.getQuantity()));

        if ( entity.getQuantityPurchased() != null )
        {
            dto.setQuantityPurchased(String.valueOf(entity.getQuantityPurchased()));
        }
        dto.setProductNotes(entity.getNotes());
        dto.setProductStore(entity.getStore());

        dto.setProductPrice(getDoubleAsString(entity.getPrice()));

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
        return StringUtils.getDoubleAsString(price, priceFormat);
    }

    @Override
    public Double getStringAsDouble(String priceString)
    {
        return StringUtils.getStringAsDouble(priceString, priceFormat);
    }
}
