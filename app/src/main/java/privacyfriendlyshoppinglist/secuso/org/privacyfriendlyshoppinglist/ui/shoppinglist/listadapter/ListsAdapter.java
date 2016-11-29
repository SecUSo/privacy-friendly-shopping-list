package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist.listadapter;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.ShoppingListActivityCache;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.settings.SettingsKeys;

import java.util.List;

/**
 * Created by Chris on 05.06.2016.
 */
public class ListsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    private List<ListDto> shoppingList;
    private ShoppingListActivityCache cache;

    public ListsAdapter(List<ListDto> shoppingList, ShoppingListActivityCache cache)
    {
        this.shoppingList = shoppingList;
        this.cache = cache;
    }

    public void setShoppingList(List<ListDto> shoppingList)
    {
        this.shoppingList = shoppingList;
    }

    public List<ListDto> getShoppingList()
    {
        return shoppingList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        int listItemLayout = getListItemLayout();
        View view = LayoutInflater.from(context).inflate(listItemLayout, parent, false);
        return new ListsItemViewHolder(view, cache);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position)
    {
        ListsItemViewHolder holder = (ListsItemViewHolder) viewHolder;
        ListDto dto = shoppingList.get(position);
        holder.processDto(dto);
    }

    @Override
    public int getItemCount()
    {
        return shoppingList == null ? 0 : shoppingList.size();
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

}
