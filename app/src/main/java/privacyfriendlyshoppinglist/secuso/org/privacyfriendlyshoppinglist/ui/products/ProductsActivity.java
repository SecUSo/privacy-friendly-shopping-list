package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.ProductService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.TotalItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.ShoppingListService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.MainActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.listadapter.ProductsAdapter;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.listeners.*;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.settings.SettingsKeys;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 09.07.16 creation date
 */
public class ProductsActivity extends AppCompatActivity
{
    public static final String PRODUCT_ID_KEY = "product.id";
    public static final String PRODUCT_NAME = "product.name";
    public static final String FROM_DIALOG = "from.dialog";
    public static final String PHOTO_BITMAP = "photo.bitmap";
    public static final String SCHEDULED_FOR_DELETION = "scheduled.for.deletion";
    public static final int REQUEST_PHOTO_PREVIEW_FROM_ITEM = 3;

    private static final long DURATION = 1000L;
    private ProductService productService;
    private ShoppingListService shoppingListService;
    private ProductActivityCache cache;
    private String listId;
    private ListItem listItem;
    private Subscriber<Long> alertUpdateSubscriber;
    private Subscription alertSubscriber;
    private boolean menusVisible;

    @Override
    protected final void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_activity);

        menusVisible = false;

        AbstractInstanceFactory instanceFactory = new InstanceFactory(getApplicationContext());
        this.productService = (ProductService) instanceFactory.createInstance(ProductService.class);
        this.shoppingListService = (ShoppingListService) instanceFactory.createInstance(ShoppingListService.class);

        listId = getIntent().getStringExtra(MainActivity.LIST_ID_KEY);
        shoppingListService.getById(listId)
                .doOnNext(result -> listItem = result)
                .doOnCompleted(() ->
                {
                    setTitle(listItem.getListName());
                    cache = new ProductActivityCache(this, listId, listItem.getListName(), listItem.isStatisticEnabled());
                    cache.getNewListFab().setOnClickListener(new AddProductOnClickListener(cache));
                    cache.getSearchAutoCompleteTextView().addTextChangedListener(new SearchTextWatcher(cache));
                    cache.getCancelSarchButton().setOnClickListener(new CancelSearchOnClick(cache));
                    updateListView();
                })
                .doOnError(Throwable::printStackTrace)
                .subscribe();

        setupAlertSubscriber();

        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.products_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        MenuItem searchItem = menu.findItem(R.id.imageview_search);
        searchItem.setOnMenuItemClickListener(new ShowSearchFieldOnClickListener(this));

        MenuItem sortItem = menu.findItem(R.id.imageview_sort);
        sortItem.setOnMenuItemClickListener(new SortProductsOnClickListener(this, listId));

        MenuItem deleteItem = menu.findItem(R.id.imageview_delete);
        deleteItem.setOnMenuItemClickListener(new ShowDeleteProductsOnClickListener(this, listId));

        sortItem.setVisible(menusVisible);
        deleteItem.setVisible(menusVisible);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode == REQUEST_PHOTO_PREVIEW_FROM_ITEM && resultCode == RESULT_OK )
        {
            updateListView();
        }
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        updateListView();
    }

    public void updateListView()
    {
        List<ProductItem> allProducts = new ArrayList<>();

        productService.getAllProducts(cache.getListId())
                .doOnNext(item -> allProducts.add(item))
                .doOnCompleted(() ->
                {
                    if ( allProducts.isEmpty() )
                    {
                        cache.getNoProductsLayout().setVisibility(View.VISIBLE);
                        subscribeAlert();
                    }
                    else
                    {
                        cache.getNoProductsLayout().setVisibility(View.GONE);
                        unsubscribeAlert();
                    }

                    menusVisible = !allProducts.isEmpty();
                    invalidateOptionsMenu();

                    // sort according to last sort selection
                    final ListItem[] listItem = new ListItem[ 1 ];
                    shoppingListService.getById(listId)
                            .doOnNext(item -> listItem[ 0 ] = item)
                            .doOnCompleted(() ->
                            {
                                String sortBy = listItem[ 0 ].getSortCriteria();
                                boolean sortAscending = listItem[ 0 ].isSortAscending();
                                productService.sortProducts(allProducts, sortBy, sortAscending);

                                cache.getProductsAdapter().setList(allProducts);
                                cache.getProductsAdapter().notifyDataSetChanged();

                                reorderProductViewBySelection();
                                updateTotals();
                            })
                            .doOnError(Throwable::printStackTrace)
                            .subscribe();
                })
                .doOnError(Throwable::printStackTrace)
                .subscribe();
    }

    private void subscribeAlert()
    {
        alertSubscriber = Observable.interval(1L, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(x -> alertUpdateSubscriber.onNext(x))
                .doOnError(Throwable::printStackTrace)
                .subscribe();
    }

    private void unsubscribeAlert()
    {
        if (alertSubscriber != null && !alertSubscriber.isUnsubscribed())
        {
            cache.getAlertTextView().setVisibility(View.GONE);
            alertSubscriber.unsubscribe();
        }
    }

    public void updateTotals()
    {
        TotalItem totalItem = productService.computeTotals(cache.getProductsAdapter().getList());
        cache.getTotalAmountTextView().setText(totalItem.getTotalAmount());
        cache.getTotalCheckedTextView().setText(totalItem.getCheckedAmount());

        if ( totalItem.isEqualsZero() )
        {
            cache.getTotalLayout().animate().alpha(0.0f).translationY(100).setDuration(DURATION);
            cache.getTotalLayout().setVisibility(View.GONE);
        }
        else
        {
            cache.getTotalLayout().setVisibility(View.VISIBLE);
            cache.getTotalLayout().animate().alpha(1.0f).translationY(0).setDuration(DURATION);
        }
    }

    public void changeItemPosition(ProductItem item)
    {
        if ( PreferenceManager.getDefaultSharedPreferences(cache.getActivity()).getBoolean(SettingsKeys.MOVE_PRODUCTS_PREF, true) )
        {
            ProductsAdapter productsAdapter = cache.getProductsAdapter();
            List<ProductItem> productsList = productsAdapter.getList();
            List<ProductItem> productItems = productService.moveSelectedToEnd(productsList);
            productsAdapter.setList(productItems);

            int initialPosition = productsList.indexOf(item);
            int finalPosition = productItems.indexOf(item);
            productsAdapter.notifyItemMoved(initialPosition, finalPosition);
            // Animation ends in final position when the initial position is equals zero.
            // Therefore the animation needs to be fix by scrolling back to position 0.
            if ( initialPosition == 0 )
            {
                cache.getRecyclerView().scrollToPosition(0);
            }
        }
    }

    public void reorderProductViewBySelection()
    {
        if ( PreferenceManager.getDefaultSharedPreferences(cache.getActivity()).getBoolean(SettingsKeys.MOVE_PRODUCTS_PREF, true) )
        {
            ProductsAdapter productsAdapter = cache.getProductsAdapter();
            List<ProductItem> productsList = productsAdapter.getList();
            List<ProductItem> productItems = productService.moveSelectedToEnd(productsList);
            productsAdapter.setList(productItems);
            productsAdapter.notifyDataSetChanged();
        }
    }

    public void setProductsAndUpdateView(List<ProductItem> sortedProducts)
    {
        cache.getProductsAdapter().setList(sortedProducts);
        cache.getProductsAdapter().notifyDataSetChanged();
    }

    private void setupAlertSubscriber()
    {
        alertUpdateSubscriber = new Subscriber<Long>()
        {
            @Override
            public void onCompleted()
            {

            }

            @Override
            public void onError(Throwable e)
            {

            }

            @Override
            public void onNext(Long time)
            {
                if ( cache.getSearchTextInputLayout().getVisibility() == View.VISIBLE )
                {
                    cache.getAlertTextView().setVisibility(View.GONE);
                    return;
                }

                if ( time % 2 != 0 )
                {
                    cache.getAlertTextView().setVisibility(View.GONE);
                }
                else
                {
                    cache.getAlertTextView().setVisibility(View.VISIBLE);
                }
            }
        };
    }
}
