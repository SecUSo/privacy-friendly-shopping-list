package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business;

import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.ContextSetter;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.AutoCompleteLists;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.TotalDto;
import rx.Observable;

import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 11.06.16 creation date
 */
public interface ProductService extends ContextSetter
{
    Observable<Void> saveOrUpdate(ProductDto dto, String listId);

    Observable<ProductDto> getById(String id);

    String getProductImagePath(String id);

    Observable<Void> deleteById(String id);

    Observable<Void> deleteOnlyImage(String id);

    Observable<Void> deleteSelected(List<ProductDto> productDtos);

    Observable<ProductDto> getAllProducts(String listId);

    Observable<TotalDto> getInfo(String listId);

    Observable<Void> deleteAllFromList(String listId);

    List<ProductDto> moveSelectedToEnd(List<ProductDto> productDtos);

    TotalDto computeTotals(List<ProductDto> productDtos);

    Boolean isSearched(String[] searchedTexts, ProductDto dto);

    String getSharableText(ProductDto dto);

    Observable<AutoCompleteLists> getAutoCompleteListsObservable();

    Observable<Boolean> deleteInvisibleProductsFromDb(List<String> listIds);

    void sortProducts(List<ProductDto> products, String criteria, boolean ascending);
}
