package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.products.persistence;

import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.ContextSetter;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.products.persistence.entity.ProductTemplateEntity;

import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 09.06.16 creation date
 */
public interface ProductTemplateDao extends ContextSetter
{
    Long save(ProductTemplateEntity entity);

    ProductTemplateEntity getById(Long id);

    List<ProductTemplateEntity> getAllEntities();

    Boolean deleteById(Long id);
}
