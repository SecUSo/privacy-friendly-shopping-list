package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.deleteproducts.listadapter;

import android.view.View;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.ui.AbstractAdapter;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.deleteproducts.DeleteProductsCache;

import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 21.07.16 creation date
 */
public class DeleteProductsAdapter extends AbstractAdapter<ProductItem, DeleteProductsCache, DeleteProductsItemViewHolder>
{
    public DeleteProductsAdapter(List<ProductItem> productsList, DeleteProductsCache cache)
    {
        super(
                productsList,
                cache,
                R.layout.delete_product_list_item);
    }

    @Override
    protected DeleteProductsItemViewHolder createNewViewHolder(View view)
    {
        return new DeleteProductsItemViewHolder(view, cache);
    }
}
