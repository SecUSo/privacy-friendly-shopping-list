package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business;

import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.ContextSetter;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListDto;

import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 11.06.16 creation date
 */
public interface ShoppingListService extends ContextSetter
{
    String SORT_BY_NAME = "name";
    String SORT_BY_PRIORITY = "name";

    void saveOrUpdate(ListDto dto);

    ListDto getById (String id);

    void deleteById (String id);

    List<ListDto> getAllListDtos();

    void deleteSelected(List<ListDto> shoppingListDtos);

    List<ListDto> moveSelectedToEnd(List<ListDto> shoppingListDtos);

    void getSortedList(List<ListDto> lists, String criteria, boolean ascending);
}
