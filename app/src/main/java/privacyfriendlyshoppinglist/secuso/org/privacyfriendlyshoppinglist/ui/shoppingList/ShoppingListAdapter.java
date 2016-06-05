package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppingList;

import android.content.Context;
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

    public ShoppingListAdapter(List<String> shoppingList) {
        this.shoppingList = shoppingList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.shoppinglist_item, parent, false);
        return ShoppingListItemViewHolder.newInstance(view);
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
