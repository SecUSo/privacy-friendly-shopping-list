package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence.impl;

import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.AbstractDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence.ProductItemDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence.entity.ProductItemEntity;

import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 11.06.16 creation date
 */
public class ProductItemDaoImpl extends AbstractDao<ProductItemEntity> implements ProductItemDao
{
    @Override
    public Long save(ProductItemEntity entity)
    {
        return saveOrUpdate(entity);
    }

    @Override
    public ProductItemEntity getById(Long id)
    {
        return getById(id, ProductItemEntity.class);
    }

    @Override
    public List<ProductItemEntity> getAllEntities()
    {
        return getAllEntities(ProductItemEntity.class);
    }

    @Override
    public Boolean deleteById(Long id)
    {
        return deleteById(id, ProductItemEntity.class);
    }
}
