package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.listeners;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.ProductService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductActivityCache;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductsActivity;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 20.07.16 creation date
 */
public class AddProductOnClickListener implements View.OnClickListener
{
    private ProductActivityCache cache;
    private ProductService productService;

    public AddProductOnClickListener(ProductActivityCache cache)
    {
        this.cache = cache;
    }

    @Override
    public void onClick(View v)
    {
        AppCompatActivity activity = cache.getActivity();
        AbstractInstanceFactory instanceFactory = new InstanceFactory(activity.getApplicationContext());
        this.productService = (ProductService) instanceFactory.createInstance(ProductService.class);


        String expectedQuantity = "5";
        String expectedQuantityPurchased = "4";
        String expectedNotes = "Some Notes";
        String expectedStore = "Store";
        String expectedPrice = "10.0";
        String expectedProductName = "Product Name";
        String expectedCategory = "category";
        String expectedHistoryCount = "5";
        String lastTimePurchased = "Tue 07/12/2016 15:52";
        String expectedDefaultNotes = "default notes";
        String expectedDefaultStore = "store";

        ProductDto dto = new ProductDto();
        dto.setQuantity(expectedQuantity);
        dto.setQuantityPurchased(expectedQuantityPurchased);
        dto.setProductNotes(expectedNotes);
        dto.setProductStore(expectedStore);
        dto.setProductPrice(expectedPrice);
        dto.setLastTimePurchased(lastTimePurchased);
        dto.setSelected(false);
        dto.setProductName(expectedProductName);
        dto.setProductCategory(expectedCategory);
        dto.setHistoryCount(expectedHistoryCount);
        dto.setDefaultNotes(expectedDefaultNotes);
        dto.setDefaultStore(expectedDefaultStore);

        productService.saveOrUpdate(dto, cache.getListId());

        ProductsActivity productsActivity = (ProductsActivity) cache.getActivity();
        productsActivity.updateListView();
    }
}
