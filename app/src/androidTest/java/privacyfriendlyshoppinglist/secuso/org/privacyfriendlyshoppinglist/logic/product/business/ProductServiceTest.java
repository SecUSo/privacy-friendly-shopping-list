package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business;

import android.content.res.Resources;
import org.joda.time.DateTime;
import org.junit.Test;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.AbstractDatabaseTest;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactoryForTests;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.DateUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence.ProductItemDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.ShoppingListService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListItem;

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

        ListItem item = new ListItem();
        item.setListName(name);
        item.setPriority(priority);
        item.setIcon(icon);
        item.setDeadlineDate(date);
        item.setDeadlineTime(time);
        item.setNotes(notes);

        // save a default list! Needed to save products
        shoppingListService.saveOrUpdate(item).toBlocking().single();
        listId = item.getId();
    }

    @Test
    public void testSaveProductItem()
    {
        ProductItem item = getDefaultItem();

        productService.saveOrUpdate(item, listId).toBlocking().single();
        assertNotNull(item.getId());
        assertNotNull(item.getId());
    }

    @Test
    public void testGetById()
    {
        ProductItem item = getDefaultItem();
        productService.saveOrUpdate(item, listId).toBlocking().single();

        ProductItem retrievedItem = productService.getById(item.getId()).toBlocking().single();
        assertEquals(item, retrievedItem);
    }

    @Test
    public void testDeleteById()
    {
        ProductItem item = getDefaultItem();
        productService.saveOrUpdate(item, listId).toBlocking().single();

        productService.deleteById(item.getId()).toBlocking().single();

        ProductItem retrivedItem = productService.getById(item.getId()).toBlocking().single();
        assertNull(retrivedItem);
    }

    @Test
    public void testDeleteSelected()
    {
        ProductItem item1 = getDefaultItem();
        item1.setSelectedForDeletion(false);
        productService.saveOrUpdate(item1, listId).toBlocking().single();

        ProductItem item2 = getDefaultItem();
        // change ids so we have "another" product
        item2.setId("2"); // templateId
        item2.setId("4"); // productId
        item2.setSelectedForDeletion(true);
        productService.saveOrUpdate(item2, listId).toBlocking().single();

        List<ProductItem> items = Arrays.asList(item1, item2);
        productService.deleteSelected(items).toBlocking().single();

        int expectedSize = 1;
        int actualSize = productItemDao.getAllEntities().size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void testGetAllProducts()
    {
        ProductItem item1 = getDefaultItem();
        productService.saveOrUpdate(item1, listId).toBlocking().single();

        ProductItem item2 = getDefaultItem();
        item2.setId("2");
        item2.setId("4");
        productService.saveOrUpdate(item2, listId).toBlocking().single();


        List<ProductItem> productItems = productService.getAllProducts(listId).toList().toBlocking().single();

        int expectedSize = 2;
        int actualSize = productItems.size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void testMoveSelectedToEnd()
    {
        ProductItem item1 = getDefaultItem();
        item1.setChecked(true);
        ProductItem item2 = getDefaultItem();
        item2.setChecked(false);

        List<ProductItem> productItems = Arrays.asList(item1, item2);
        List<ProductItem> sortedItems = productService.moveSelectedToEnd(productItems);
        assertEquals(productItems.get(0), sortedItems.get(1));
        assertEquals(productItems.get(1), sortedItems.get(0));
    }

    @Test
    public void testDeleteProductsWhenListIsDeleted()
    {
        ProductItem item1 = getDefaultItem();
        item1.setId(null);
        item1.setId(null);
        productService.saveOrUpdate(item1, listId).toBlocking().single();

        ProductItem item2 = getDefaultItem();
        item2.setId(null);
        item2.setId(null);
        productService.saveOrUpdate(item2, listId).toBlocking().single();

        List<ProductItem> products = productService.getAllProducts(listId).toList().toBlocking().single();
        assertEquals(2, products.size());

        productService.deleteAllFromList(listId).toBlocking().single();
        shoppingListService.deleteById(listId).toBlocking().single();

        products = productService.getAllProducts(listId).toList().toBlocking().single();
        assertEquals(0, products.size());
    }


    private ProductItem getDefaultItem()
    {
        String expectedProductId = "1";
        String expectedQuantity = "5";
        String expectedNotes = "Some Notes";
        String expectedStore = "Store";
        String expectedPrice = "10.00";
        String templateId = "3";
        String expectedProductName = "product";
        String expectedCategory = "category";

        ProductItem item = new ProductItem();
        item.setId(expectedProductId);
        item.setQuantity(expectedQuantity);
        item.setProductNotes(expectedNotes);
        item.setProductStore(expectedStore);
        item.setProductPrice(expectedPrice);
        item.setChecked(false);
        item.setId(templateId);
        item.setProductName(expectedProductName);
        item.setProductCategory(expectedCategory);
        return item;
    }
}