package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.impl.converter;

import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.ContextSetter;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductTemplateDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence.entity.ProductItemEntity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence.entity.ProductTemplateEntity;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 17.07.16 creation date
 */
public interface ProductConverterService extends ContextSetter
{
    void convertDtoToEntity(ProductDto dto, ProductItemEntity entity);

    void convertDtoToTemplateEntity(ProductDto dto, ProductTemplateEntity entity);

    void convertTemplateEntityToDto(ProductTemplateEntity entity, ProductTemplateDto dto);

    void convertEntitiesToDto(ProductTemplateEntity templateEntity, ProductItemEntity entity, ProductDto dto);

    String getDoubleAsString(Double price);
}
