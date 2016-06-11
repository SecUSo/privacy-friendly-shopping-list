package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.products.persistence;

import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.ContextSetter;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.products.persistence.entity.ProductEntity;

import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.05.16 14:59 creation date
 */
public interface ProductDao extends ContextSetter
{
    Long save(ProductEntity entity);

    ProductEntity getById(Long id);

    List<ProductEntity> getAllEntities();

    Boolean deleteById(Long id);
}
