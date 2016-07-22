package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business;

import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.ContextSetter;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.entity.ShoppingListEntity;

import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 11.06.16 creation date
 */
public interface ShoppingListService extends ContextSetter
{
    String SORT_BY_NAME = "name";
    String SORT_BY_PRIORITY = "priority";

    void saveOrUpdate(ListDto dto);

    ListDto getById (String id);

    ShoppingListEntity getEntityById(String id);

    void deleteById (String id);

    List<ListDto> getAllListDtos();

    List<String> deleteSelected(List<ListDto> shoppingListDtos);

    List<ListDto> moveSelectedToEnd(List<ListDto> shoppingListDtos);

    void sortList(List<ListDto> lists, String criteria, boolean ascending);
}
