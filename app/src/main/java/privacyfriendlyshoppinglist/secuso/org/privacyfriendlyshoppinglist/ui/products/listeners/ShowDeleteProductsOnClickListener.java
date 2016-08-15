package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.listeners;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.deleteproducts.DeleteProductsActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.MainActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductActivityCache;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 21.07.16 creation date
 */
public class ShowDeleteProductsOnClickListener implements MenuItem.OnMenuItemClickListener
{
    private ProductActivityCache cache;

    public ShowDeleteProductsOnClickListener(ProductActivityCache cache)
    {
        this.cache = cache;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        AppCompatActivity activity = cache.getActivity();
        Intent intent = new Intent(activity, DeleteProductsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(MainActivity.LIST_ID_KEY, cache.getListId());
        intent.putExtra(MainActivity.LIST_NAME_KEY, cache.getListName());
        activity.startActivity(intent);
        return true;
    }
}
