package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.listeners;

import android.view.View;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductActivityCache;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.dialog.ProductDialogFragment;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 20.07.16 creation date
 */
public class AddProductOnClickListener implements View.OnClickListener
{
    private ProductActivityCache cache;

    public AddProductOnClickListener(ProductActivityCache cache)
    {
        this.cache = cache;
    }

    @Override
    public void onClick(View v)
    {
        ProductItem item = new ProductItem();
        if ( !ProductDialogFragment.isOpened() )
        {
            ProductDialogFragment productDialogFragment = ProductDialogFragment.newAddDialogInstance(item, cache);
            productDialogFragment.show(cache.getActivity().getSupportFragmentManager(), "ProductDialog");
        }
    }
}
