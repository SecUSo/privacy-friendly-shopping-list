package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence;

import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.ContextSetter;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.entity.ShoppingListEntityNew;

import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 09.06.16 creation date
 */
public interface ShoppingListDaoNew extends ContextSetter
{
    Long save(ShoppingListEntityNew entity);

    ShoppingListEntityNew getById(Long id);

    List<ShoppingListEntityNew> getAllEntities();

    Boolean deleteById(Long id);
}
