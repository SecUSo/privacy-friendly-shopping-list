package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.impl;

import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.AbstractDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.ShoppingListDaoNew;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.entity.ShoppingListEntityNew;

import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 10.06.16 creation date
 */
public class ShoppingListDaoNewImpl extends AbstractDao<ShoppingListEntityNew> implements ShoppingListDaoNew
{
    @Override
    public Long save(ShoppingListEntityNew entity)
    {
        return saveOrUpdate(entity);
    }

    @Override
    public ShoppingListEntityNew getById(Long id)
    {
        return getById(id, ShoppingListEntityNew.class);
    }

    @Override
    public List<ShoppingListEntityNew> getAllEntities()
    {
        return getAllEntities(ShoppingListEntityNew.class);
    }

    @Override
    public Boolean deleteById(Long id)
    {
        return deleteById(id, ShoppingListEntityNew.class);
    }
}
