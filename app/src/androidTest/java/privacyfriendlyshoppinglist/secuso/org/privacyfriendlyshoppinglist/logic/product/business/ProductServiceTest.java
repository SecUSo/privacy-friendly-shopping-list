package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business;

import org.joda.time.DateTime;
import org.junit.Test;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.AbstractDatabaseTest;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactoryForTests;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence.ProductItemDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence.ProductTemplateDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence.entity.ProductTemplateEntity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.ShoppingListService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListDto;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 17.07.16 creation date
 */
public class ProductServiceTest extends AbstractDatabaseTest
{

    private ProductService productService;
    private ShoppingListService shoppingListService;
    private ProductTemplateDao productTemplateDao;
    private ProductItemDao productItemDao;
    private String listId;

    @Override
    protected void setupBeforeEachTest()
    {
        AbstractInstanceFactory instanceFactory = new InstanceFactoryForTests(getContext());
        productService = (ProductService) instanceFactory.createInstance(ProductService.class);
        shoppingListService = (ShoppingListService) instanceFactory.createInstance(ShoppingListService.class);
        productTemplateDao = (ProductTemplateDao) instanceFactory.createInstance(ProductTemplateDao.class);
        productItemDao = (ProductItemDao) instanceFactory.createInstance(ProductItemDao.class);

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

    @Test
    public void testDeleteById()
    {
        ProductDto dto = getDefaultDto();
        productService.saveOrUpdate(dto, listId);

        productService.deleteById(dto.getId());

        ProductDto retrivedDto = productService.getById(dto.getId());
        assertNull(retrivedDto);

        ProductTemplateEntity templateEntity = productTemplateDao.getById(Long.valueOf(dto.getId()));
        assertNotNull(templateEntity);

    }

    @Test
    public void testDeleteSelected()
    {
        ProductDto dto1 = getDefaultDto();
        dto1.setSelected(false);
        productService.saveOrUpdate(dto1, listId);

        ProductDto dto2 = getDefaultDto();
        // change ids so we have "another" product
        dto2.setId("2"); // templateId
        dto2.setProductId("4"); // productId
        dto2.setSelected(true);
        productService.saveOrUpdate(dto2, listId);

        List<ProductDto> dtos = Arrays.asList(dto1, dto2);
        productService.deleteSelected(dtos);

        int expectedTemplateSize = 2; // templates are not deleted
        int actualTemplateSize = productTemplateDao.getAllEntities().size();
        assertEquals(expectedTemplateSize, actualTemplateSize);

        int expectedSize = 1;
        int actualSize = productItemDao.getAllEntities().size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void testGetAllProducts()
    {
        ProductDto dto1 = getDefaultDto();
        productService.saveOrUpdate(dto1, listId);

        ProductDto dto2 = getDefaultDto();
        dto2.setId("2");
        dto2.setProductId("4");
        productService.saveOrUpdate(dto2, listId);


        List<ProductDto> productDtos = productService.getAllProducts(listId);

        int expectedSize = 2;
        int actualSize = productDtos.size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void testMoveSelectedToEnd()
    {
        ProductDto dto1 = getDefaultDto();
        dto1.setSelected(true);
        ProductDto dto2 = getDefaultDto();
        dto2.setSelected(false);

        List<ProductDto> productDtos = Arrays.asList(dto1, dto2);
        List<ProductDto> sortedDtos = productService.moveSelectedToEnd(productDtos);
        assertEquals(productDtos.get(0), sortedDtos.get(1));
        assertEquals(productDtos.get(1), sortedDtos.get(0));
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
        dto.setSelected(false);
        dto.setId(templateId);
        dto.setProductName(expectedProductName);
        dto.setProductCategory(expectedCategory);
        dto.setHistoryCount(expectedHistoryCount);
        dto.setDefaultNotes(expectedDefaultNotes);
        dto.setDefaultStore(expectedDefaultStore);
        return dto;
    }
}