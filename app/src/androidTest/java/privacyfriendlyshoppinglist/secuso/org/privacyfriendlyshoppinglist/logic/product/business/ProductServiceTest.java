package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business;

import org.joda.time.DateTime;
import org.junit.Test;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.AbstractDatabaseTest;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactoryForTests;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.ShoppingListService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListDto;

import java.util.Date;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 17.07.16 creation date
 */
public class ProductServiceTest extends AbstractDatabaseTest
{

    private ProductService productService;
    private ShoppingListService shoppingListService;
    private String listId;

    @Override
    protected void setupBeforeEachTest()
    {
        AbstractInstanceFactory instanceFactory = new InstanceFactoryForTests(getContext());
        productService = (ProductService) instanceFactory.createInstance(ProductService.class);
        shoppingListService = (ShoppingListService) instanceFactory.createInstance(ShoppingListService.class);

        String name = "name";
        String priority = "HIGH";
        int icon = 10;
        Date deadline = new DateTime("2016-06-11").toDate();
        String notes = "notes";

        ListDto dto = new ListDto();
        dto.setListName(name);
        dto.setPriority(priority);
        dto.setIcon(icon);
        dto.setDeadline(deadline);
        dto.setNotes(notes);

        // save a default list! Needed to save products
        shoppingListService.saveOrUpdate(dto);
        listId = dto.getId();
    }

    @Test
    public void testSaveProductDto()
    {
        ProductDto dto = getDefaultDto();

        productService.saveOrUpdate(dto, listId);
        assertNotNull(dto.getProductId());
        assertNotNull(dto.getId());
    }

    @Test
    public void testGetById()
    {
        ProductDto dto = getDefaultDto();
        productService.saveOrUpdate(dto, listId);

        ProductDto retrievedDto = productService.getById(dto.getProductId());
        assertEquals(dto, retrievedDto);
    }


    private ProductDto getDefaultDto()
    {
        String expectedProductId = "1";
        String expectedQuantity = "5";
        String expectedQuantityPurchased = "4";
        String expectedNotes = "Some Notes";
        String expectedStore = "Store";
        String expectedPrice = "10.0";
        String templateId = "3";
        String expectedProductName = "product";
        String expectedCategory = "category";
        String expectedHistoryCount = "5";
        String lastTimePurchased = "Tue 07/12/2016 15:52";
        String expectedDefaultNotes = "default notes";
        String expectedDefaultStore = "store";

        ProductDto dto = new ProductDto();
        dto.setProductId(expectedProductId);
        dto.setQuantity(expectedQuantity);
        dto.setQuantityPurchased(expectedQuantityPurchased);
        dto.setProductNotes(expectedNotes);
        dto.setProductStore(expectedStore);
        dto.setProductPrice(expectedPrice);
        dto.setLastTimePurchased(lastTimePurchased);
        dto.setSelected(true);
        dto.setId(templateId);
        dto.setProductName(expectedProductName);
        dto.setProductCategory(expectedCategory);
        dto.setHistoryCount(expectedHistoryCount);
        dto.setDefaultNotes(expectedDefaultNotes);
        dto.setDefaultStore(expectedDefaultStore);
        return dto;
    }
}