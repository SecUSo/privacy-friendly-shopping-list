package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.services.products.persistence.impl;

import android.util.Log;
import com.j256.ormlite.dao.Dao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.AbstractDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.DataBaseHelper;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.services.products.persistence.ProductDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.services.products.persistence.entity.ProductEntity;

import java.sql.SQLException;
import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.05.16 15:08 creation date
 */
public class ProductDaoImpl extends AbstractDao<ProductEntity> implements ProductDao
{
    @Override
    public Long save(ProductEntity entity)
    {
        return saveOrUpdate(entity);
    }

    @Override
    public ProductEntity getById(Long id)
    {
        return getById(id, ProductEntity.class);
    }

    @Override
    public List<ProductEntity> getAllEntities()
    {
        return getAllEntities(ProductEntity.class);
    }
}
