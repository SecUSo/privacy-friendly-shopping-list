package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.deletelists.listadapter;

import android.content.res.Resources;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.ui.AbstractViewHolder;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.StringUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.ProductService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.TotalItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.ShoppingListService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.deletelists.DeleteListsCache;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.settings.SettingsKeys;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist.listadapter.ListItemCache;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 05.06.2016.
 */
class DeleteListsItemViewHolder extends AbstractViewHolder<ListItem, DeleteListsCache>
{

    private static final String HIGH_PRIORITY_INDEX = "0";
    private ListItemCache listItemCache;
    private ProductService productService;
    private ShoppingListService shoppingListService;

    DeleteListsItemViewHolder(final View parent, DeleteListsCache deleteListsCache)
    {
        super(parent, deleteListsCache);
        this.listItemCache = new ListItemCache(parent);
        AbstractInstanceFactory instanceFactory = new InstanceFactory(deleteListsCache.getActivity());
        this.productService = (ProductService) instanceFactory.createInstance(ProductService.class);
        this.shoppingListService = (ShoppingListService) instanceFactory.createInstance(ShoppingListService.class);
    }

    public void processItem(ListItem item)
    {
        listItemCache.getListNameTextView().setText(item.getListName());
        listItemCache.getDeadLineTextView().setText(item.getDeadlineDate());

        listItemCache.getShowDetailsImageButton().setVisibility(View.GONE);

        List<ProductItem> productItems = new ArrayList<>();
        productService.getAllProducts(item.getId())
                .filter(productItem -> !productItem.isChecked())
                .doOnNext(productItem -> productItems.add(productItem))
                .doOnCompleted(() ->
                {
                    int reminderStatus = shoppingListService.getReminderStatusResource(item, productItems);
                    listItemCache.getReminderBar().setImageResource(reminderStatus);
                })
                .doOnError(Throwable::printStackTrace)
                .subscribe();

        final TotalItem[] totalItem = new TotalItem[ 1 ];
        productService.getInfo(item.getId())
                .doOnNext(result -> totalItem[ 0 ] = result)
                .doOnError(Throwable::printStackTrace)
                .doOnCompleted(() ->
                        listItemCache.getNrProductsTextView().setText(String.valueOf(totalItem[ 0 ].getNrProducts()))
                ).subscribe();

        setupPriorityIcon(item);
        setupReminderIcon(item);

        updateVisibilityFormat(item);

        listItemCache.getListCard().setOnClickListener(v ->
        {
            item.setSelected(!item.isSelected());
            updateVisibilityFormat(item);
        });
    }

    private void updateVisibilityFormat(ListItem item)
    {
        CardView listCard = listItemCache.getListCard();
        TextView listNameTextView = listItemCache.getListNameTextView();
        TextView listNrProdTextView = listItemCache.getNrProductsTextView();
        Resources resources = listCard.getContext().getResources();
        if ( item.isSelected() )
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

    private void setupReminderIcon(ListItem item)
    {
        if ( StringUtils.isEmpty(item.getReminderCount()) )
        {
            listItemCache.getReminderImageView().setVisibility(View.GONE);
        }
        else
        {
            listItemCache.getReminderImageView().setVisibility(View.VISIBLE);
            if ( !PreferenceManager.getDefaultSharedPreferences(cache.getActivity()).getBoolean(SettingsKeys.NOTIFICATIONS_ENABLED, true) )
            {
                listItemCache.getReminderImageView().setColorFilter(ContextCompat.getColor(cache.getActivity(), R.color.red));
            }
            else
            {
                listItemCache.getReminderImageView().setColorFilter(ContextCompat.getColor(cache.getActivity(), R.color.middlegrey));
            }
        }
    }

    private void setupPriorityIcon(ListItem item)
    {
        if ( HIGH_PRIORITY_INDEX.equals(item.getPriority()) )
        {
            listItemCache.getHighPriorityImageView().setVisibility(View.VISIBLE);
        }
        else
        {
            listItemCache.getHighPriorityImageView().setVisibility(View.GONE);
        }
    }


}