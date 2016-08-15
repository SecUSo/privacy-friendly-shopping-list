package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist.listadapter;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.ProductService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.TotalDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.MainActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.ShoppingListActivityCache;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductsActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist.EditDeleteListDialog;

/**
 * Created by Chris on 05.06.2016.
 */
class ListsItemViewHolder extends RecyclerView.ViewHolder
{
    private static final String HIGH_PRIORITY_INDEX = "0";
    private ListItemCache listItemCache;
    private ShoppingListActivityCache shoppingListCache;
    private ProductService productService;

    ListsItemViewHolder(final View parent, ShoppingListActivityCache cache)
    {
        super(parent);
        this.listItemCache = new ListItemCache(parent);
        this.shoppingListCache = cache;
        AbstractInstanceFactory instanceFactory = new InstanceFactory(cache.getActivity());
        this.productService = (ProductService) instanceFactory.createInstance(ProductService.class);
    }

    void processDto(ListDto dto)
    {
        listItemCache.getListNameTextView().setText(dto.getListName());
        listItemCache.getNotesTextView().setText(dto.getNotes());

        // todo: remove these block of ifs. It is only there to visualize the colors
        int tmpColorBarCode = Integer.valueOf(dto.getId()) % 3;
        if ( tmpColorBarCode == 0 )
        {
            listItemCache.getReminderBar().setImageResource(R.drawable.reminder_status_neutral);
        }
        else if ( tmpColorBarCode == 1 )
        {
            listItemCache.getReminderBar().setImageResource(R.drawable.reminder_status_triggered);
        }
        else if ( tmpColorBarCode == 2 )
        {
            listItemCache.getReminderBar().setImageResource(R.drawable.reminder_status_time_over);
        }

        setupPriorityIcon(dto);

        TotalDto totalDto = productService.getInfo(dto.getId());
        listItemCache.getListDetails().setText(
                totalDto.getInfo(listItemCache.getCurrency(), shoppingListCache.getActivity()) +
                        dto.getDetailInfo(listItemCache.getListCard().getContext()));
        listItemCache.getNrProductsTextView().setText(String.valueOf(totalDto.getNrProducts()));


        listItemCache.getListCard().setOnClickListener(v ->
        {
            Intent intent = new Intent(shoppingListCache.getActivity(), ProductsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(MainActivity.LIST_ID_KEY, dto.getId());
            shoppingListCache.getActivity().startActivity(intent);
        });

        listItemCache.getListCard().setOnLongClickListener(view ->
        {

            DialogFragment editDeleteFragment = EditDeleteListDialog.newEditDeleteInstance(dto, shoppingListCache);
            editDeleteFragment.show(shoppingListCache.getActivity().getSupportFragmentManager(), "Liste");

            return true;
        });

        ImageButton showDetailsButton = listItemCache.getShowDetailsImageButton();
        showDetailsButton.setOnClickListener(v ->
        {
            listItemCache.setDetailsVisible(!listItemCache.isDetailsVisible());
            if ( listItemCache.isDetailsVisible() )
            {
                showDetailsButton.setImageResource(R.drawable.ic_keyboard_arrow_up_white_48dp);
                listItemCache.getListDetails().setVisibility(View.VISIBLE);

            }
            else
            {
                showDetailsButton.setImageResource(R.drawable.ic_keyboard_arrow_down_white_48dp);
                listItemCache.getListDetails().setVisibility(View.GONE);
            }
        });

    }

    private void setupPriorityIcon(ListDto dto)
    {
        if ( HIGH_PRIORITY_INDEX.equals(dto.getPriority()) )
        {
            listItemCache.getHighPriorityImageView().setVisibility(View.VISIBLE);
        }
        else
        {
            listItemCache.getHighPriorityImageView().setVisibility(View.GONE);
        }
    }


}