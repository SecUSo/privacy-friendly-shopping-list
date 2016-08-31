package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.listeners;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.deleteproducts.DeleteProductsActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.MainActivity;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 21.07.16 creation date
 */
public class ShowDeleteProductsOnClickListener implements MenuItem.OnMenuItemClickListener
{
    private Activity activity;
    private String listId;

    public ShowDeleteProductsOnClickListener(Activity activity, String listId)
    {
        this.activity = activity;
        this.listId = listId;

    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        CancelSearchOnClick.performClick();
        Intent intent = new Intent(activity, DeleteProductsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(MainActivity.LIST_ID_KEY, listId);
        activity.startActivity(intent);
        return true;
    }
}
