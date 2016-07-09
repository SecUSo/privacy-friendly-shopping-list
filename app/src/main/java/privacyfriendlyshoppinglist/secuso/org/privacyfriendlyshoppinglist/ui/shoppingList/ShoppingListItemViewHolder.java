package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppingList;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListDto;

/**
 * Created by Chris on 05.06.2016.
 */
public class ShoppingListItemViewHolder extends RecyclerView.ViewHolder {

    private ListCache cache;

    public ShoppingListItemViewHolder(final View parent, FragmentManager fragmentManager)
    {
        super(parent);
        this.cache = new ListCache(parent);

        cache.getListCard().setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                DialogFragment productFragement = new EditDialogFragment();
                productFragement.show(fragmentManager, "Liste");
            }
        });

        cache.getListCard().setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                DialogFragment productFragement = new EditDialogFragment();
                productFragement.show(fragmentManager, "Liste");

                return true;
            }
        });
    }

    public void processDto(ListDto dto)
    {
        cache.getListNameTextView().setText(dto.getListName());
        cache.getNrProductsTextView().setText("0");
    }

}