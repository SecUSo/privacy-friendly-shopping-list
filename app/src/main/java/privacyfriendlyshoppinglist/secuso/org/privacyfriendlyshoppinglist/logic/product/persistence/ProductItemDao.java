package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence;

import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.ContextSetter;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence.entity.ProductItemEntity;

import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 11.06.16 creation date
 */
public interface ProductItemDao extends ContextSetter
{
    Long save(ProductItemEntity entity);

    ProductItemEntity getById(Long id);

    List<ProductItemEntity> getAllEntities();

    Boolean deleteById(Long id);
}
