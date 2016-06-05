package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppingList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;

/**
 * Created by Chris on 05.06.2016.
 */
public class ShoppingListItemViewHolder extends RecyclerView.ViewHolder {

    private final TextView shoppingListName;

    public ShoppingListItemViewHolder(final View parent, TextView shoppingListName) {
        super(parent);
        this.shoppingListName = shoppingListName;
    }

    public static ShoppingListItemViewHolder newInstance(View parent) {
        TextView shoppingListName = (TextView) parent.findViewById(R.id.shoppinglist_name_textview);
        return new ShoppingListItemViewHolder(parent, shoppingListName);
    }

    public void setItemText(CharSequence text) {
        shoppingListName.setText(text);
    }

}