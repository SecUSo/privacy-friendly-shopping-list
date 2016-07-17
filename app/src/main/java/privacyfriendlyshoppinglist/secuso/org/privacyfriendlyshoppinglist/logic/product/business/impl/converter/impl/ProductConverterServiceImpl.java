package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.impl.converter.impl;

import android.content.Context;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.DB;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.DateUtils;
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

    @Override
    public void setContext(Context context, DB db)
    {
        this.language = context.getResources().getString(R.string.language);
        this.dateLongPattern = context.getResources().getString(R.string.date_long_pattern);
    }

    @Override
    public void convertDtoToEntity(ProductDto dto, ProductItemEntity entity)
    {
        if ( dto.getProductId() != null )
        {
            entity.setId(Long.valueOf(dto.getProductId()));
        }
        entity.setQuantity(Integer.valueOf(dto.getQuantity()));
        entity.setQuantityPurchased(Integer.valueOf(dto.getQuantityPurchased()));
        entity.setNotes(dto.getProductNotes());
        entity.setStore(dto.getProductStore());
        entity.setPrice(Double.parseDouble(dto.getProductPrice()));

        Date purchasedDate = DateUtils.getDateFromString(dto.getPurchasedDate(), dateLongPattern, language).toDate();
        entity.setPurchasedDate(purchasedDate);
        entity.setSelected(dto.isSelected());
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
        entity.setHistoryCount(Integer.valueOf(dto.getHistoryCount()));

        Date purchasedDate = DateUtils.getDateFromString(dto.getPurchasedDate(), dateLongPattern, language).toDate();
        entity.setLastTimePurchased(purchasedDate);

        entity.setDefaultNotes(dto.getDefaultNotes());
        entity.setDefaultStore(dto.getDefaultStore());
    }

    @Override
    public void convertTemplateEntityToDto(ProductTemplateEntity entity, ProductTemplateDto dto)
    {

    }

    @Override
    public void convertEntitiesToDto(ProductTemplateEntity templateEntity, ProductItemEntity entity, ProductDto dto)
    {

    }
}
