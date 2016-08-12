package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.deletelists.listadapter;

import android.content.res.Resources;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.ProductService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist.listadapter.ListItemCache;

/**
 * Created by Chris on 05.06.2016.
 */
class DeleteListsItemViewHolder extends RecyclerView.ViewHolder
{

    private ListItemCache cache;
    private ProductService productService;

    DeleteListsItemViewHolder(final View parent, AppCompatActivity activity)
    {
        super(parent);
        this.cache = new ListItemCache(parent);
        AbstractInstanceFactory instanceFactory = new InstanceFactory(activity.getApplicationContext());
        this.productService = (ProductService) instanceFactory.createInstance(ProductService.class);
    }

    void processDto(ListDto dto)
    {
        cache.getListNameTextView().setText(dto.getListName());
        cache.getNotesTextView().setText(dto.getNotes());
        cache.getShowDetailsImageButton().setVisibility(View.GONE);

        int nrProducts = productService.getAllProducts(dto.getId()).size();
        cache.getNrProductsTextView().setText(String.valueOf(nrProducts));
        setupPriorityIcon(dto);

        updateVisibilityFormat(dto);

        cache.getListCard().setOnClickListener(v ->
        {
            dto.setSelected(!dto.isSelected());
            updateVisibilityFormat(dto);
        });
    }

    private void updateVisibilityFormat(ListDto dto)
    {
        CardView listCard = cache.getListCard();
        TextView listNameTextView = cache.getListNameTextView();
        TextView listNrProdTextView = cache.getNrProductsTextView();
        Resources resources = listCard.getContext().getResources();
        if ( dto.isSelected() )
        {
            listCard.setCardBackgroundColor(resources.getColor(R.color.transparent));
            listNameTextView.setTextColor(resources.getColor(R.color.middlegrey));
            listNameTextView.setPaintFlags(listNameTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            listNrProdTextView.setPaintFlags(listNrProdTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else
        {
            listCard.setCardBackgroundColor(resources.getColor(R.color.white));
            listNameTextView.setTextColor(resources.getColor(R.color.black));
            listNameTextView.setPaintFlags(listNameTextView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            listNrProdTextView.setPaintFlags(listNrProdTextView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }

    private void setupPriorityIcon(ListDto dto)
    {
        // todo: do not use hard code values here
        if ( "0".equals(dto.getPriority()) )
        {
            cache.getHighPriorityImageView().setVisibility(View.VISIBLE);
        }
        else
        {
            cache.getHighPriorityImageView().setVisibility(View.GONE);
        }
    }


}