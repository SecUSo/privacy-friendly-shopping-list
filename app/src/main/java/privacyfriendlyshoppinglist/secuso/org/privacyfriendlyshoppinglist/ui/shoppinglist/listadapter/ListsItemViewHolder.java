package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist.listadapter;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
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
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.MainActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.ShoppingListActivityCache;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductsActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.settings.SettingsKeys;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist.EditDeleteListDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 05.06.2016.
 */
class ListsItemViewHolder extends AbstractViewHolder<ListItem, ShoppingListActivityCache>
{
    private static final String HIGH_PRIORITY_INDEX = "0";
    private ListItemCache listItemCache;
    private ProductService productService;
    private ShoppingListService shoppingListService;

    ListsItemViewHolder(final View parent, ShoppingListActivityCache cache)
    {
        super(parent, cache);
        this.listItemCache = new ListItemCache(parent);
        AbstractInstanceFactory instanceFactory = new InstanceFactory(cache.getActivity());
        this.productService = (ProductService) instanceFactory.createInstance(ProductService.class);
        this.shoppingListService = (ShoppingListService) instanceFactory.createInstance(ShoppingListService.class);
    }

    public void processItem(ListItem item)
    {
        listItemCache.getListNameTextView().setText(item.getListName());
        listItemCache.getDeadLineTextView().setText(item.getDeadlineDate());

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

        setupPriorityIcon(item);
        setupReminderIcon(item);

        final TotalItem[] totalItem = new TotalItem[ 1 ];
        productService.getInfo(item.getId())
                .doOnNext(result -> totalItem[ 0 ] = result)
                .doOnCompleted(() ->
                        {
                            listItemCache.getListDetails().setText(
                                    totalItem[ 0 ].getInfo(listItemCache.getCurrency(), cache.getActivity()) +
                                            item.getDetailInfo(listItemCache.getListCard().getContext()));
                            listItemCache.getNrProductsTextView().setText(String.valueOf(totalItem[ 0 ].getNrProducts()));
                        }
                )
                .doOnError(Throwable::printStackTrace)
                .subscribe();

        listItemCache.getListCard().setOnClickListener(v ->
        {
            Intent intent = new Intent(cache.getActivity(), ProductsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(MainActivity.LIST_ID_KEY, item.getId());
            cache.getActivity().startActivity(intent);
        });

        listItemCache.getListCard().setOnLongClickListener(view ->
        {

            DialogFragment editDeleteFragment = EditDeleteListDialog.newEditDeleteInstance(item, cache);
            editDeleteFragment.show(cache.getActivity().getSupportFragmentManager(), "List");

            return true;
        });

        ImageButton showDetailsButton = listItemCache.getShowDetailsImageButton();
        showDetailsButton.setOnClickListener(v ->
        {
            listItemCache.setDetailsVisible(!listItemCache.isDetailsVisible());
            if ( listItemCache.isDetailsVisible() )
            {
                showDetailsButton.setImageResource(R.drawable.ic_keyboard_arrow_up_white_48sp);
                listItemCache.getListDetails().setVisibility(View.VISIBLE);

            }
            else
            {
                showDetailsButton.setImageResource(R.drawable.ic_keyboard_arrow_down_white_48sp);
                listItemCache.getListDetails().setVisibility(View.GONE);
            }
        });

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
            AppCompatActivity activity = cache.getActivity();
            if ( !PreferenceManager.getDefaultSharedPreferences(activity).getBoolean(SettingsKeys.NOTIFICATIONS_ENABLED, true) )
            {
                listItemCache.getReminderImageView().setColorFilter(ContextCompat.getColor(activity, R.color.red));
            }
            else
            {
                listItemCache.getReminderImageView().setColorFilter(ContextCompat.getColor(activity, R.color.middlegrey));
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