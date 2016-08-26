package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business;

import org.joda.time.DateTime;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.ContextSetter;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.entity.ShoppingListEntity;
import rx.Observable;

import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 11.06.16 creation date
 */
public interface ShoppingListService extends ContextSetter
{
    Observable<Void> saveOrUpdate(ListDto dto);

    Observable<ListDto> getById(String id);

    DateTime getReminderDate(ListDto dto);

    DateTime getDeadLine(ListDto dto);

    int getReminderStatusResource(ListDto dto, List<ProductDto> productDtos);

    ShoppingListEntity getEntityByIdSync(String id);

    Observable<Void> deleteById(String id);

    Observable<ListDto> getAllListDtos();

    Observable<String> deleteSelected(List<ListDto> shoppingListDtos);

    List<ListDto> moveSelectedToEnd(List<ListDto> shoppingListDtos);

    void sortList(List<ListDto> lists, String criteria, boolean ascending);

    String getShareableText(ListDto listDto, List<ProductDto> productDtos);
}
