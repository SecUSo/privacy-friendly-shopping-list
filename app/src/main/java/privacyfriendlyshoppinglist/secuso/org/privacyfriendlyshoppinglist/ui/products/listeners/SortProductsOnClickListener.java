package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.listeners;

import android.view.MenuItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductActivityCache;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.sort.SortProductsDialog;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 22.07.16 creation date
 */
public class SortProductsOnClickListener implements MenuItem.OnMenuItemClickListener
{
    private ProductActivityCache cache;

    public SortProductsOnClickListener(ProductActivityCache cache)
    {
        this.cache = cache;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        SortProductsDialog sortProductsDialog = SortProductsDialog.newInstance(cache);
        sortProductsDialog.show(cache.getActivity().getFragmentManager(), "SortDialog");
        return true;
    }
}
