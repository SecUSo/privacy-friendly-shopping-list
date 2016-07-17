package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business;

import org.junit.Test;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.AbstractDatabaseTest;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactoryForTests;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 17.07.16 creation date
 */
public class ProductServiceTest extends AbstractDatabaseTest
{

    private ProductService productService;

    @Override
    protected void setupBeforeEachTest()
    {
        AbstractInstanceFactory instanceFactory = new InstanceFactoryForTests(getContext());
        productService = (ProductService) instanceFactory.createInstance(ProductService.class);
    }

    @Test
    public void testSaveProductDto() throws Exception
    {
        String expectedProductId = "1";
        String expectedQuantity = "5";
        String expectedQuantityPurchased = "4";
        String expectedNotes = "Some Notes";
        String expectedStore = "Store";
        String expectedPrice = "10.00";
        String purchaseDate = "Tue 07/12/2016 15:52";
        String templateId = "3";
        String expectedProductName = "product";
        String expectedCategory = "category";
        String expectedHistoryCount = "5";
        String lastTimePurchased = "Tue 07/12/2016 15:52";
        String expectedDefaultNotes = "default notes";
        String expectedDefaultStore = "store";


    }
}