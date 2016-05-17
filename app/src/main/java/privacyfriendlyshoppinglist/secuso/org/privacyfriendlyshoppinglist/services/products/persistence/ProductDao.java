package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.services.products.persistence;

import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.services.products.persistence.entity.ProductEntity;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.05.16 14:59 creation date
 */
public interface ProductDao
{
    Long save(ProductEntity entity);

    ProductEntity getById(Long id);
}
