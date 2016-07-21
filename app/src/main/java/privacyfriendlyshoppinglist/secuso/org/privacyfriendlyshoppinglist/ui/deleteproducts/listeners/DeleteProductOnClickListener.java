package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.deleteproducts.listeners;

import android.support.design.widget.Snackbar;
import android.view.View;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.ProductService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.deleteproducts.DeleteProductsCache;

import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 21.07.16 creation date
 */
public class DeleteProductOnClickListener implements View.OnClickListener
{
    private ProductService productService;
    private DeleteProductsCache cache;

    public DeleteProductOnClickListener(DeleteProductsCache cache)
    {
        this.cache = cache;
        AbstractInstanceFactory instanceFactory = new InstanceFactory(cache.getActivity().getApplicationContext());
        this.productService = (ProductService) instanceFactory.createInstance(ProductService.class);
    }

    @Override
    public void onClick(View v)
    {
        Snackbar.make(v, R.string.delele_products_confirmation, Snackbar.LENGTH_LONG)
                .setAction(R.string.okay, new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        List<ProductDto> productList = cache.getDeleteProductsAdapter().getProductsList();
                        productService.deleteSelected(productList);
                        updateListView();
                    }
                }).show();
    }

    public void updateListView()
    {
        cache.getDeleteProductsAdapter().setProductsList(productService.getAllProducts(cache.getListId()));
        cache.getDeleteProductsAdapter().notifyDataSetChanged();
    }
}
