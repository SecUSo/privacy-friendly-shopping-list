package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.impl.converter;

import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.ContextSetter;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.entity.ShoppingListEntity;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 11.06.16 creation date
 */
public interface ShoppingListConverter extends ContextSetter
{
    void convertItemToEntity(ListItem item, ShoppingListEntity entity);

    void convertEntityToItem(ShoppingListEntity entity, ListItem item);
}
