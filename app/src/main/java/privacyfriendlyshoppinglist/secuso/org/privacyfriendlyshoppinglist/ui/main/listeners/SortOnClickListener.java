package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.listeners;

import android.view.MenuItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.ShoppingListActivityCache;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.sort.SortListsDialog;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.07.16 creation date
 */
public class SortOnClickListener implements MenuItem.OnMenuItemClickListener
{

    private ShoppingListActivityCache cache;

    public SortOnClickListener(ShoppingListActivityCache cache)
    {
        this.cache = cache;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        SortListsDialog sortListsDialog = SortListsDialog.newInstance(cache.getActivity());
        sortListsDialog.show(cache.getActivity().getFragmentManager(), "SortDialog");
        return true;
    }
}
