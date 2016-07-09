package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppingList;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;

import java.util.List;

/**
 * Created by Chris on 05.06.2016.
 */
public class ShoppingListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> shoppingList;
    private FragmentManager fragmentManager;

    public ShoppingListAdapter(List<String> shoppingList, FragmentManager fragmentManager)
    {
        this.shoppingList = shoppingList;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.shopping_list_item, parent, false);
        return ShoppingListItemViewHolder.newInstance(view, fragmentManager);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ShoppingListItemViewHolder holder = (ShoppingListItemViewHolder) viewHolder;
        String itemName = shoppingList.get(position);
        holder.setItemText(itemName);
    }

    @Override
    public int getItemCount() {
        return shoppingList == null ? 0 : shoppingList.size();
    }

}
