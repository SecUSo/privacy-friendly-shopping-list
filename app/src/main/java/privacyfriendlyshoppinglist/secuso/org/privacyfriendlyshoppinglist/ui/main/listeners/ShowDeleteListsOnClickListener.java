package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.listeners;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.deletelists.DeleteListsActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.ShoppingListActivityCache;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.07.16 creation date
 */
public class ShowDeleteListsOnClickListener implements MenuItem.OnMenuItemClickListener
{
    private ShoppingListActivityCache cache;

    public ShowDeleteListsOnClickListener(ShoppingListActivityCache cache)
    {
        this.cache = cache;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        AppCompatActivity activity = cache.getActivity();
        Intent intent = new Intent(activity, DeleteListsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        return true;
    }
}
