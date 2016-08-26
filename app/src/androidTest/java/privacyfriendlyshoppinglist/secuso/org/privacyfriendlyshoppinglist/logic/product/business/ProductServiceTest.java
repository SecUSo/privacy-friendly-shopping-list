package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business;

import android.content.res.Resources;
import org.joda.time.DateTime;
import org.junit.Test;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.AbstractDatabaseTest;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactoryForTests;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.DateUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence.ProductItemDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.ShoppingListService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListDto;

import java.util.Arrays;
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
    private ProductItemDao productItemDao;
    private String listId;
    private String shortDatePattern;
    private String timePattern;
    private String language;

    @Override
    protected void setupBeforeEachTest()
    {
        AbstractInstanceFactory instanceFactory = new InstanceFactoryForTests(getContext());
        productService = (ProductService) instanceFactory.createInstance(ProductService.class);
        shoppingListService = (ShoppingListService) instanceFactory.createInstance(ShoppingListService.class);
        productItemDao = (ProductItemDao) instanceFactory.createInstance(ProductItemDao.class);

        Resources resources = getContext().getResources();
        shortDatePattern = resources.getString(R.string.date_short_pattern);
        timePattern = resources.getString(R.string.time_pattern);
        language = resources.getString(R.string.language);

        String name = "name";
        String priority = "HIGH";
        int icon = 10;
        DateTime datetime = new DateTime("2016-07-05").withHourOfDay(10).withMinuteOfHour(30);
        String date = DateUtils.getDateAsString(datetime.getMillis(), shortDatePattern, language);
        String time = DateUtils.getDateAsString(datetime.getMillis(), timePattern, language);
        String notes = "notes";

        ListDto dto = new ListDto();
        dto.setListName(name);
        dto.setPriority(priority);
        dto.setIcon(icon);
        dto.setDeadlineDate(date);
        dto.setDeadlineTime(time);
        dto.setNotes(notes);

        // save a default list! Needed to save products
        shoppingListService.saveOrUpdate(dto).toBlocking().single();
        listId = dto.getId();
    }

    @Test
    public void testSaveProductDto()
    {
        ProductDto dto = getDefaultDto();

        productService.saveOrUpdate(dto, listId).toBlocking().single();
        assertNotNull(dto.getId());
        assertNotNull(dto.getId());
    }

    @Test
    public void testGetById()
    {
        ProductDto dto = getDefaultDto();
        productService.saveOrUpdate(dto, listId).toBlocking().single();

        ProductDto retrievedDto = productService.getById(dto.getId()).toBlocking().single();
        assertEquals(dto, retrievedDto);
    }

    @Test
    public void testDeleteById()
    {
        ProductDto dto = getDefaultDto();
        productService.saveOrUpdate(dto, listId).toBlocking().single();

        productService.deleteById(dto.getId()).toBlocking().single();

        ProductDto retrivedDto = productService.getById(dto.getId()).toBlocking().single();
        assertNull(retrivedDto);
    }

    @Test
    public void testDeleteSelected()
    {
        ProductDto dto1 = getDefaultDto();
        dto1.setSelectedForDeletion(false);
        productService.saveOrUpdate(dto1, listId).toBlocking().single();

        ProductDto dto2 = getDefaultDto();
        // change ids so we have "another" product
        dto2.setId("2"); // templateId
        dto2.setId("4"); // productId
        dto2.setSelectedForDeletion(true);
        productService.saveOrUpdate(dto2, listId).toBlocking().single();

        List<ProductDto> dtos = Arrays.asList(dto1, dto2);
        productService.deleteSelected(dtos).toBlocking().single();

        int expectedSize = 1;
        int actualSize = productItemDao.getAllEntities().size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void testGetAllProducts()
    {
        ProductDto dto1 = getDefaultDto();
        productService.saveOrUpdate(dto1, listId).toBlocking().single();

        ProductDto dto2 = getDefaultDto();
        dto2.setId("2");
        dto2.setId("4");
        productService.saveOrUpdate(dto2, listId).toBlocking().single();


        List<ProductDto> productDtos = productService.getAllProducts(listId).toList().toBlocking().single();

        int expectedSize = 2;
        int actualSize = productDtos.size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void testMoveSelectedToEnd()
    {
        ProductDto dto1 = getDefaultDto();
        dto1.setChecked(true);
        ProductDto dto2 = getDefaultDto();
        dto2.setChecked(false);

        List<ProductDto> productDtos = Arrays.asList(dto1, dto2);
        List<ProductDto> sortedDtos = productService.moveSelectedToEnd(productDtos);
        assertEquals(productDtos.get(0), sortedDtos.get(1));
        assertEquals(productDtos.get(1), sortedDtos.get(0));
    }

    @Test
    public void testDeleteProductsWhenListIsDeleted()
    {
        ProductDto dto1 = getDefaultDto();
        dto1.setId(null);
        dto1.setId(null);
        productService.saveOrUpdate(dto1, listId).toBlocking().single();

        ProductDto dto2 = getDefaultDto();
        dto2.setId(null);
        dto2.setId(null);
        productService.saveOrUpdate(dto2, listId).toBlocking().single();

        List<ProductDto> products = productService.getAllProducts(listId).toList().toBlocking().single();
        assertEquals(2, products.size());

        productService.deleteAllFromList(listId).toBlocking().single();
        shoppingListService.deleteById(listId).toBlocking().single();

        products = productService.getAllProducts(listId).toList().toBlocking().single();
        assertEquals(0, products.size());
    }


    private ProductDto getDefaultDto()
    {
        String expectedProductId = "1";
        String expectedQuantity = "5";
        String expectedNotes = "Some Notes";
        String expectedStore = "Store";
        String expectedPrice = "10.00";
        String templateId = "3";
        String expectedProductName = "product";
        String expectedCategory = "category";

        ProductDto dto = new ProductDto();
        dto.setId(expectedProductId);
        dto.setQuantity(expectedQuantity);
        dto.setProductNotes(expectedNotes);
        dto.setProductStore(expectedStore);
        dto.setProductPrice(expectedPrice);
        dto.setChecked(false);
        dto.setId(templateId);
        dto.setProductName(expectedProductName);
        dto.setProductCategory(expectedCategory);
        return dto;
    }
}