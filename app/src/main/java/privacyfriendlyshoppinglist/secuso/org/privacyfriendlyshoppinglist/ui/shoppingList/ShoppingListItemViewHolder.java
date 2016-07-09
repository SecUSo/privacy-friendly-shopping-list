package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppingList;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
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

    public static ShoppingListItemViewHolder newInstance(View parent, FragmentManager fragmentManager)
    {
        TextView shoppingListName = (TextView) parent.findViewById(R.id.textview_list_name);

        CardView itemCard = (CardView) parent.findViewById(R.id.item_card_view);

        itemCard.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                // open ProductDialogFragement
                DialogFragment productFragement = new EditDialogFragment();
                productFragement.show(fragmentManager, "Liste");

            }
        });

        itemCard.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                DialogFragment productFragement = new EditDialogFragment();
                productFragement.show(fragmentManager, "Liste");

                return true;
            }
        });

            return new ShoppingListItemViewHolder(parent, shoppingListName);
    }

    public void setItemText(CharSequence text) {
        shoppingListName.setText(text);
    }

}