package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.listeners;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.ProductService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductActivityCache;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.dialog.ProductDialogFragment;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 20.07.16 creation date
 */
public class AddProductOnClickListener implements View.OnClickListener
{
    private ProductActivityCache cache;
    private ProductService productService;
    private int temporaryCounterForFakeData;

    public AddProductOnClickListener(ProductActivityCache cache)
    {
        this.cache = cache;
        AppCompatActivity activity = cache.getActivity();
        AbstractInstanceFactory instanceFactory = new InstanceFactory(activity.getApplicationContext());
        this.productService = (ProductService) instanceFactory.createInstance(ProductService.class);
    }

    @Override
    public void onClick(View v)
    {
        //ProductDto dto = createFakeDto();

        ProductDto dto = new ProductDto();
        //dto.setQuantity("0");
        //dto.setProductPrice("0.00");
        //dto.setHistoryCount("0");
        ProductDialogFragment productDialogFragment = ProductDialogFragment.newAddDialogInstance(dto, cache);

        productDialogFragment.show(cache.getActivity().getSupportFragmentManager(), "ProductDialog");

        //productService.saveOrUpdate(dto, cache.getListId());

        // ProductsActivity productsActivity = (ProductsActivity) cache.getActivity();
        // productsActivity.updateListView();
    }

    private ProductDto createFakeDto()
    {
        // todo: temporary test data
        int mod3 = temporaryCounterForFakeData % 3;
        int mod2 = temporaryCounterForFakeData % 2;
        int mod5 = temporaryCounterForFakeData % 5;
        String expectedQuantity = String.valueOf(mod3);

        String expectedQuantityPurchased = "4";
        String expectedNotes = "Some Notes";
        String expectedStore = "";
        if ( mod5 != 0 )
        {
            expectedStore = "Store " + mod2;
        }
        String expectedPrice = String.valueOf(temporaryCounterForFakeData);
        String expectedProductName = "Product Name " + temporaryCounterForFakeData++;
        String expectedCategory = "";
        if ( mod3 != 0 )
        {
            expectedCategory = "category " + mod5;
        }
        String expectedHistoryCount = "5";
        String lastTimePurchased = "Tue 07/12/2016 15:52";
        String expectedDefaultNotes = "default notes";
        String expectedDefaultStore = "store";

        // todo: this data has to be set in a dialog!
        ProductDto dto = new ProductDto();
        dto.setQuantity(expectedQuantity);
        dto.setQuantityPurchased(expectedQuantityPurchased);
        dto.setProductNotes(expectedNotes);
        dto.setProductStore(expectedStore);
        dto.setProductPrice(expectedPrice);
        dto.setLastTimePurchased(lastTimePurchased);
        dto.setChecked(false);
        dto.setProductName(expectedProductName);
        dto.setProductCategory(expectedCategory);
        dto.setHistoryCount(expectedHistoryCount);
        dto.setDefaultNotes(expectedDefaultNotes);
        dto.setDefaultStore(expectedDefaultStore);
        return dto;
    }
}
