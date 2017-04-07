package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business;

import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.ContextSetter;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.AutoCompleteLists;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.TotalItem;
import rx.Observable;

import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 11.06.16 creation date
 */
public interface ProductService extends ContextSetter
{
    Observable<Void> saveOrUpdate(ProductItem item, String listId);

    Observable<Void> duplicateProducts(String listId);

    Observable<Void> copyToList(ProductItem product, String listId);

    Observable<Void> resetCheckedProducts(String listId);

    Observable<ProductItem> getById(String id);

    String getProductImagePath(String id);

    Observable<Void> deleteById(String id);

    Observable<Void> deleteOnlyImage(String id);

    Observable<Void> deleteSelected(List<ProductItem> productItems);

    Observable<ProductItem> getAllProducts(String listId);

    Observable<ProductItem> getAllProducts();

    Observable<TotalItem> getInfo(String listId);

    Observable<Void> deleteAllFromList(String listId);

    List<ProductItem> moveSelectedToEnd(List<ProductItem> productItems);

    TotalItem computeTotals(List<ProductItem> productItems);

    Boolean isSearched(String[] searchedTexts, ProductItem item);

    String getSharableText(ProductItem item);

    Observable<AutoCompleteLists> getAutoCompleteListsObservable();

    Observable<Boolean> deleteInvisibleProductsFromDb(List<String> listIds);

    void sortProducts(List<ProductItem> products, String criteria, boolean ascending);
}
