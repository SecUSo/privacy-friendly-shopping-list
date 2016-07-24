package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.ProductService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.TotalDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.MainActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.listadapter.ProductsAdapter;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.listeners.AddProductOnClickListener;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.listeners.ShowDeleteProductsOnClickListener;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.listeners.SortProductsOnClickListener;

import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 09.07.16 creation date
 */
public class ProductsActivity extends AppCompatActivity
{
    private static final long DURATION = 1000L;
    private ProductService productService;
    private ProductActivityCache cache;

    @Override
    protected final void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_activity);

        String listName = getIntent().getStringExtra(MainActivity.LIST_NAME_KEY);
        setTitle(listName);

        String listId = getIntent().getStringExtra(MainActivity.LIST_ID_KEY);
        cache = new ProductActivityCache(this, listId);

        AbstractInstanceFactory instanceFactory = new InstanceFactory(getApplicationContext());
        this.productService = (ProductService) instanceFactory.createInstance(ProductService.class);

        updateListView();

        cache.getNewListFab().setOnClickListener(new AddProductOnClickListener(cache));
        cache.getDeleteImageView().setOnClickListener(new ShowDeleteProductsOnClickListener(cache));
        cache.getSortImageView().setOnClickListener(new SortProductsOnClickListener(cache));

        overridePendingTransition(0, 0);
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        updateListView();
    }

    public void updateListView()
    {
        cache.getProductsAdapter().setProductsList(productService.getAllProducts(cache.getListId()));
        cache.getProductsAdapter().notifyDataSetChanged();

        reorderProductViewBySelection();
        updateTotals();
    }

    public void updateTotals()
    {
        TotalDto totalDto = productService.computeTotals(cache.getProductsAdapter().getProductsList());
        cache.getTotalAmountTextView().setText(totalDto.getTotalAmount());
        cache.getTotalCheckedTextView().setText(totalDto.getCheckedAmount());

        if ( totalDto.isEqualsZero() )
        {
            cache.getTotalLayout().animate().alpha(0.0f).setDuration(DURATION);
            cache.getTotalLayout().setVisibility(View.GONE);
        }
        else
        {
            cache.getTotalLayout().setVisibility(View.VISIBLE);
            cache.getTotalLayout().animate().alpha(1.0f).setDuration(DURATION);
        }
    }

    public void changeItemPosition(ProductDto dto)
    {
        ProductsAdapter productsAdapter = cache.getProductsAdapter();
        List<ProductDto> productsList = productsAdapter.getProductsList();
        List<ProductDto> productDtos = productService.moveSelectedToEnd(productsList);
        productsAdapter.setProductsList(productDtos);

        int initialPosition = productsList.indexOf(dto);
        int finalPosition = productDtos.indexOf(dto);
        productsAdapter.notifyItemMoved(initialPosition, finalPosition);
        // Animation ends in final position when the initial position is equals zero.
        // Therefore the animation needs to be fix by scrolling back to position 0.
        if ( initialPosition == 0 )
        {
            cache.getRecyclerView().scrollToPosition(0);
        }
    }

    public void reorderProductViewBySelection()
    {
        ProductsAdapter productsAdapter = cache.getProductsAdapter();
        List<ProductDto> productsList = productsAdapter.getProductsList();
        List<ProductDto> productDtos = productService.moveSelectedToEnd(productsList);
        productsAdapter.setProductsList(productDtos);
        productsAdapter.notifyDataSetChanged();
    }

    public void reorderProductView(List<ProductDto> sortedProducts)
    {
        cache.getProductsAdapter().setProductsList(sortedProducts);
        cache.getProductsAdapter().notifyDataSetChanged();
    }
}
