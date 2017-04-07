package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist.listadapter;

import android.preference.PreferenceManager;
import android.view.View;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.ui.AbstractAdapter;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.ShoppingListActivityCache;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.settings.SettingsKeys;

import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 21.07.16 creation date
 */
public class ListsAdapter extends AbstractAdapter<ListItem, ShoppingListActivityCache, ListsItemViewHolder>
{
    public ListsAdapter(List<ListItem> shoppingList, ShoppingListActivityCache cache)
    {
        super(
                shoppingList,
                cache,
                R.layout.shopping_list_item);

        setLayoutId(getListItemLayout());
    }

    private int getListItemLayout()
    {
        int listItemLayout;
        if ( PreferenceManager.getDefaultSharedPreferences(cache.getActivity()).getBoolean(SettingsKeys.CHECKBOX_POSITION_PREF, true) )
        {
            listItemLayout = R.layout.shopping_list_item;
        }
        else
        {
            listItemLayout = R.layout.shopping_list_item_left_hand;
        }
        return listItemLayout;
    }

    @Override
    protected ListsItemViewHolder createNewViewHolder(View view)
    {
        return new ListsItemViewHolder(view, cache);
    }
}
