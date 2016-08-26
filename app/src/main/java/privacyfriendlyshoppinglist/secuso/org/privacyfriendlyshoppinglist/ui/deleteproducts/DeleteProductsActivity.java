package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.deleteproducts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.MessageUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.ProductService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.ShoppingListService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.deleteproducts.listeners.DeleteProductOnClickListener;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 21.07.16 creation date
 */
public class DeleteProductsActivity extends AppCompatActivity
{
    private ProductService productService;
    private ShoppingListService shoppingListService;
    private DeleteProductsCache cache;
    private String listId;
    private ListDto dto;

    @Override
    protected final void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_products_activity);

        MessageUtils.showTutorialDialog(this);

        AbstractInstanceFactory instanceFactory = new InstanceFactory(getApplicationContext());
        this.productService = (ProductService) instanceFactory.createInstance(ProductService.class);
        this.shoppingListService = (ShoppingListService) instanceFactory.createInstance(ShoppingListService.class);

        listId = getIntent().getStringExtra(MainActivity.LIST_ID_KEY);
        shoppingListService.getById(listId)
                .doOnNext(result -> dto = result)
                .doOnCompleted(() ->
                {
                    cache = new DeleteProductsCache(this, listId, dto.getListName());
                    cache.getDeleteFab().setOnClickListener(new DeleteProductOnClickListener(cache));
                    updateListView();
                })
                .subscribe();

        overridePendingTransition(0, 0);
    }

    public void updateListView()
    {
        List<ProductDto> allProducts = new ArrayList<>();

        productService.getAllProducts(cache.getListId())
                .doOnNext(dto -> allProducts.add(dto))
                .doOnCompleted(() ->
                {
                    // sort according to last sort selection
                    final ListDto[] listDto = new ListDto[ 1 ];
                    shoppingListService.getById(listId)
                            .doOnNext(result -> listDto[ 0 ] = result)
                            .doOnCompleted(() ->
                                    {
                                        String sortBy = listDto[ 0 ].getSortCriteria();
                                        boolean sortAscending = listDto[ 0 ].isSortAscending();
                                        productService.sortProducts(allProducts, sortBy, sortAscending);

                                        cache.getDeleteProductsAdapter().setProductsList(allProducts);
                                        cache.getDeleteProductsAdapter().notifyDataSetChanged();
                                    }
                            )
                            .subscribe();
                })
                .subscribe();
    }
}

