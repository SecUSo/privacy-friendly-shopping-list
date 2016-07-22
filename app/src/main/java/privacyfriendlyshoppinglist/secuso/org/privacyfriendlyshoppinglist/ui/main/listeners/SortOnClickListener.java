package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.listeners;

import android.view.View;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.ShoppingListActivityCache;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.sort.SortListsDialog;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.07.16 creation date
 */
public class SortOnClickListener implements View.OnClickListener
{

    private ShoppingListActivityCache cache;

    public SortOnClickListener(ShoppingListActivityCache cache)
    {
        this.cache = cache;
    }

    @Override
    public void onClick(View v)
    {
        SortListsDialog sortListsDialog = SortListsDialog.newInstance(cache.getActivity());
        sortListsDialog.show(cache.getActivity().getFragmentManager(), "SortDialog");
    }
}
