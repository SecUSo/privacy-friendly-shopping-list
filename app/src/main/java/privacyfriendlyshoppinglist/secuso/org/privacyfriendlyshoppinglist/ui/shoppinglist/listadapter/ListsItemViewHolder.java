package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist.listadapter;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.ShoppingListCache;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductsActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist.EditDialogFragment;

/**
 * Created by Chris on 05.06.2016.
 */
public class ListsItemViewHolder extends RecyclerView.ViewHolder
{

    public static final String LIST_NAME_KEY = "list.name";
    public static final String LIST_ID_KEY = "list.id";
    private ListItemCache listItemCache;
    private ShoppingListCache shoppingListCache;

    public ListsItemViewHolder(final View parent, ShoppingListCache cache)
    {
        super(parent);
        this.listItemCache = new ListItemCache(parent);
        this.shoppingListCache = cache;

    }

    public void processDto(ListDto dto)
    {
        listItemCache.getListNameTextView().setText(dto.getListName());
        listItemCache.getNrProductsTextView().setText("0");

        listItemCache.getListCard().setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(shoppingListCache.getActivity(), ProductsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(LIST_NAME_KEY, dto.getListName());
                intent.putExtra(LIST_ID_KEY, dto.getId());
                shoppingListCache.getActivity().startActivity(intent);
            }
        });

        listItemCache.getListCard().setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                DialogFragment productFragement = EditDialogFragment.newInstance(dto, shoppingListCache);
                productFragement.show(shoppingListCache.getActivity().getSupportFragmentManager(), "Liste");

                return true;
            }
        });

    }


}