package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.listeners;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.ProductService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductActivityCache;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductsActivity;
import rx.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 31.08.16 creation date
 */
public class SearchTextWatcher implements TextWatcher
{
    private ProductService productService;
    private ProductActivityCache cache;
    private Subscription searchSubscription;

    public SearchTextWatcher(ProductActivityCache cache)
    {
        this.cache = cache;
        AbstractInstanceFactory instanceFactory = new InstanceFactory(cache.getActivity());
        this.productService = (ProductService) instanceFactory.createInstance(ProductService.class);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {
        if ( cache.getSearchTextInputLayout().getVisibility() == View.GONE )
        {
            return;
        }

        String[] searchedTexts = s.toString().split(" ");
        Map<String, ProductItem> productMap = new TreeMap<>();

        if ( searchSubscription != null )
        {
            searchSubscription.unsubscribe();
        }

        searchSubscription = productService.getAllProducts()
                .filter(item -> productService.isSearched(searchedTexts, item))
                .doOnNext(item ->
                {
                    ProductItem productItem = productMap.get(item.getProductName());
                    if ( productItem == null )
                    {
                        productMap.put(item.getProductName(), item);
                    }
                    else
                    {
                        if ( item.getListId().equals(cache.getListId()) )
                        {
                            productMap.put(item.getProductName(), item);
                        }
                    }
                })
                .doOnCompleted(() ->
                {
                    if ( productMap.isEmpty() )
                    {
                        cache.getNoProductsLayout().setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        cache.getNoProductsLayout().setVisibility(View.GONE);
                    }

                    List<ProductItem> products = new ArrayList<>();
                    products.addAll(productMap.values());

                    ProductsActivity activity = (ProductsActivity) cache.getActivity();
                    activity.setProductsAndUpdateView(products);
                    activity.reorderProductViewBySelection();
                    setErrorMessage(products);
                })
                .doOnError(Throwable::printStackTrace)
                .subscribe();
    }

    private void setErrorMessage(List<ProductItem> resultItems)
    {
        if ( resultItems.isEmpty() )
        {
            cache.getSearchTextInputLayout().setError(cache.getActivity().getResources().getString(R.string.no_products_found));
        }
        else
        {
            cache.getSearchTextInputLayout().setError(null);
        }
    }

    @Override
    public void afterTextChanged(Editable s)
    {

    }
}
