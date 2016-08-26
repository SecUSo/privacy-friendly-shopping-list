package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.listeners;

import android.app.Activity;
import android.view.MenuItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.sort.SortProductsDialog;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 22.07.16 creation date
 */
public class SortProductsOnClickListener implements MenuItem.OnMenuItemClickListener
{
    private Activity activity;
    private String listId;

    public SortProductsOnClickListener(Activity activity, String listId)
    {
        this.activity = activity;
        this.listId = listId;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        SortProductsDialog sortProductsDialog = SortProductsDialog.newInstance(activity, listId);
        sortProductsDialog.show(activity.getFragmentManager(), "SortDialog");
        return true;
    }
}
