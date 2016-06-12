package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.impl;

import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.AbstractDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.ShoppingListDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.entity.ShoppingListEntity;

import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 10.06.16 creation date
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
    public Boolean deleteById(Long id)
    {
        return deleteById(id, ShoppingListEntity.class);
    }
}
