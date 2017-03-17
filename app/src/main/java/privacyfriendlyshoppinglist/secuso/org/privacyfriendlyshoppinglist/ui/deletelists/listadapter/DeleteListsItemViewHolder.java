package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.deletelists.listadapter;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.StringUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.ProductService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.TotalItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.ShoppingListService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.settings.SettingsKeys;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist.listadapter.ListItemCache;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 05.06.2016.
 */
class DeleteListsItemViewHolder extends RecyclerView.ViewHolder
{

    private static final String HIGH_PRIORITY_INDEX = "0";
    private ListItemCache cache;
    private ProductService productService;
    private ShoppingListService shoppingListService;
    private Activity activity;

    DeleteListsItemViewHolder(final View parent, AppCompatActivity activity)
    {
        super(parent);
        this.activity = activity;
        this.cache = new ListItemCache(parent);
        AbstractInstanceFactory instanceFactory = new InstanceFactory(activity.getApplicationContext());
        this.productService = (ProductService) instanceFactory.createInstance(ProductService.class);
        this.shoppingListService = (ShoppingListService) instanceFactory.createInstance(ShoppingListService.class);
    }

    void processItem(ListItem item)
    {
        cache.getListNameTextView().setText(item.getListName());
        cache.getDeadLineTextView().setText(item.getDeadlineDate());

        cache.getShowDetailsImageButton().setVisibility(View.GONE);

        List<ProductItem> productItems = new ArrayList<>();
        productService.getAllProducts(item.getId())
                .filter(productItem -> !productItem.isChecked())
                .doOnNext(productItem -> productItems.add(productItem))
                .doOnCompleted(() ->
                {
                    int reminderStatus = shoppingListService.getReminderStatusResource(item, productItems);
                    cache.getReminderBar().setImageResource(reminderStatus);
                })
                .subscribe();

        final TotalItem[] totalItem = new TotalItem[ 1 ];
        productService.getInfo(item.getId())
                .doOnNext(result -> totalItem[ 0 ] = result)
                .doOnCompleted(() ->
                        cache.getNrProductsTextView().setText(String.valueOf(totalItem[ 0 ].getNrProducts()))
                ).subscribe();

        setupPriorityIcon(item);
        setupReminderIcon(item);

        updateVisibilityFormat(item);

        cache.getListCard().setOnClickListener(v ->
        {
            item.setSelected(!item.isSelected());
            updateVisibilityFormat(item);
        });
    }

    private void updateVisibilityFormat(ListItem item)
    {
        CardView listCard = cache.getListCard();
        TextView listNameTextView = cache.getListNameTextView();
        TextView listNrProdTextView = cache.getNrProductsTextView();
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
            cache.getReminderImageView().setVisibility(View.GONE);
        }
        else
        {
            cache.getReminderImageView().setVisibility(View.VISIBLE);
            if ( !PreferenceManager.getDefaultSharedPreferences(activity).getBoolean(SettingsKeys.NOTIFICATIONS_ENABLED, true) )
            {
                cache.getReminderImageView().setColorFilter(ContextCompat.getColor(activity, R.color.red));
            }
            else
            {
                cache.getReminderImageView().setColorFilter(ContextCompat.getColor(activity, R.color.middlegrey));
            }
        }
    }

    private void setupPriorityIcon(ListItem item)
    {
        if ( HIGH_PRIORITY_INDEX.equals(item.getPriority()) )
        {
            cache.getHighPriorityImageView().setVisibility(View.VISIBLE);
        }
        else
        {
            cache.getHighPriorityImageView().setVisibility(View.GONE);
        }
    }


}