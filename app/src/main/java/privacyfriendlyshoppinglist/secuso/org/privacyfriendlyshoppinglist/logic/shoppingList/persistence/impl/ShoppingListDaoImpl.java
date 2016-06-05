package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.impl;

import android.content.Context;
import com.j256.ormlite.dao.Dao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.AbstractDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.DB;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.products.persistence.entity.ProductEntity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.ShoppingListDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.entity.ShoppingListEntity;

import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 05.06.16 creation date
 */
public class ShoppingListDaoImpl extends AbstractDao<ShoppingListEntity> implements ShoppingListDao
{
    @Override
    public Long save(ShoppingListEntity entity)
    {
        return saveOrUpdate(entity);
    }

    @Override
    public ShoppingListEntity getById(Long id)
    {
        return getById(id, ShoppingListEntity.class);
    }

    @Override
    public List<ShoppingListEntity> getAllEntities()
    {
        return getAllEntities(ShoppingListEntity.class);
    }

    @Override
    public boolean deleteById(Long id)
    {
        return deleteById(id, ShoppingListEntity.class);
    }
}
