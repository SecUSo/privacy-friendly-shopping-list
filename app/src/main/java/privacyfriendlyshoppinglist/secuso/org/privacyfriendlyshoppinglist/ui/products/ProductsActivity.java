package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.ProductService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.listeners.AddProductOnClickListener;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist.listadapter.ListsItemViewHolder;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 09.07.16 creation date
 */
public class ProductsActivity extends AppCompatActivity
{
    private ProductService productService;
    private ProductActivityCache cache;

    @Override
    protected final void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_activity);

        String listName = getIntent().getStringExtra(ListsItemViewHolder.LIST_NAME_KEY);
        setTitle(listName);

        String listId = getIntent().getStringExtra(ListsItemViewHolder.LIST_ID_KEY);
        cache = new ProductActivityCache(this, listId);

        AbstractInstanceFactory instanceFactory = new InstanceFactory(getApplicationContext());
        this.productService = (ProductService) instanceFactory.createInstance(ProductService.class);
        cache = new ProductActivityCache(this, listId);

        updateListView();

        cache.getNewListFab().setOnClickListener(new AddProductOnClickListener(cache));

        overridePendingTransition(0, 0);
    }

    public void updateListView()
    {
        cache.getProductsAdapter().setProductsList(productService.getAllProducts(cache.getListId()));
        cache.getProductsAdapter().notifyDataSetChanged();
    }
}
