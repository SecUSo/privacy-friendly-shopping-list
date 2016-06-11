package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence;

import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.ContextSetter;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.entity.ShoppingListEntity;

import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 09.06.16 creation date
 */
public interface ShoppingListDao extends ContextSetter
{
    Long save(ShoppingListEntity entity);

    ShoppingListEntity getById(Long id);

    List<ShoppingListEntity> getAllEntities();

    Boolean deleteById(Long id);
}
