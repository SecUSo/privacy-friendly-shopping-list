package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.deletelists.listadapter;

import android.content.res.Resources;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist.listadapter.ListItemCache;

/**
 * Created by Chris on 05.06.2016.
 */
public class DeleteListsItemViewHolder extends RecyclerView.ViewHolder
{

    private ListItemCache cache;

    public DeleteListsItemViewHolder(final View parent, AppCompatActivity activity)
    {
        super(parent);
        this.cache = new ListItemCache(parent);
    }

    public void processDto(ListDto dto)
    {
        cache.getListNameTextView().setText(dto.getListName());
        cache.getNrProductsTextView().setText("0"); // todo: read from data base
        updateVisibilityFormat(dto);

        cache.getListCard().setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                dto.setSelected(!dto.isSelected());
                updateVisibilityFormat(dto);
            }
        });
    }

    private void updateVisibilityFormat(ListDto dto)
    {
        Resources resources = cache.getListCard().getContext().getResources();
        TextView listNameTextView = cache.getListNameTextView();
        TextView listNrProdTextView = cache.getNrProductsTextView();
        if ( dto.isSelected() )
        {
            cache.getListCard().setCardBackgroundColor(resources.getColor(R.color.transparent));
            listNameTextView.setTextColor(resources.getColor(R.color.middlegrey));
            listNameTextView.setPaintFlags(listNameTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            listNrProdTextView.setPaintFlags(listNrProdTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else
        {
            cache.getListCard().setCardBackgroundColor(resources.getColor(R.color.white));
            listNameTextView.setTextColor(resources.getColor(R.color.black));
            listNameTextView.setPaintFlags(listNameTextView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            listNrProdTextView.setPaintFlags(listNrProdTextView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }


}