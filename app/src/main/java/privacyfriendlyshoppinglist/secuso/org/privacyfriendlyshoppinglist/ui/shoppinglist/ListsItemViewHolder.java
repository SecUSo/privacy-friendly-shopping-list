package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListDto;

/**
 * Created by Chris on 05.06.2016.
 */
public class ListsItemViewHolder extends RecyclerView.ViewHolder
{

    private ListsCache cache;

    public ListsItemViewHolder(final View parent, AppCompatActivity activity)
    {
        super(parent);
        this.cache = new ListsCache(parent);

        cache.getListCard().setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(activity, ListsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(intent);
            }
        });

        cache.getListCard().setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                DialogFragment productFragement = new EditDialogFragment();
                productFragement.show(activity.getSupportFragmentManager(), "Liste");

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