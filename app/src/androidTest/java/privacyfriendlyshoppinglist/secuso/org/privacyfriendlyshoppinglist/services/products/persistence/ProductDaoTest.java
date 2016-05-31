package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.services.products.persistence;

import android.test.AndroidTestCase;
import org.joda.time.DateTime;
import org.junit.Test;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.ContextManager;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.services.products.persistence.entity.ProductEntity;

import java.util.Date;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.05.16 18:33 creation date
 */
public class ProductDaoTest extends AndroidTestCase
{
    private ProductDao productDao;

    @Override
    public void setUp()
    {
        productDao = new ContextManager<ProductDao>().getInstance(getContext(), ProductDao.class);
        getContext().deleteDatabase("ShoppingList_test.db");
//        getContext().deleteDatabase("ShoppingList.db");
    }

    @Test
    public void testSave() throws Exception
    {
        ProductEntity entity = new ProductEntity()
                .setProductName("name")
                .setDescription("description")
                .setPrice(100.0)
                .setStore("store")
                .setCategory("category")
                .setLastDate(new DateTime("2015-05-31").toDate());

        Long id = productDao.save(entity);

        assertNotNull(id);
    }


    @Test
    public void testGetById() throws Exception
    {
        String expectedName = "name";
        String expectedDescription = "description";
        double expectedPrice = 100.0;
        String expectedStore = "store";
        String expectedCategory = "category";
        Date expectedDate = new DateTime("2015-05-31").toDate();

        ProductEntity entity = new ProductEntity()
                .setProductName(expectedName)
                .setDescription(expectedDescription)
                .setPrice(expectedPrice)
                .setStore(expectedStore)
                .setCategory(expectedCategory)
                .setLastDate(expectedDate);;

        Long id = productDao.save(entity);

        ProductEntity newEntity = productDao.getById(id);
        assertEquals(expectedName, newEntity.getProductName());
        assertEquals(expectedDescription, newEntity.getDescription());
        assertEquals(expectedPrice, newEntity.getPrice());
        assertEquals(expectedStore, newEntity.getStore());
        assertEquals(expectedCategory, newEntity.getCategory());
        assertEquals(expectedDate, newEntity.getLastDate());
    }

    @Test
    public void testUpdate() throws Exception
    {
        String name = "name";
        String description = "description";
        double price = 100.0;
        String store = "store";
        String category = "category";

        ProductEntity entity1 = new ProductEntity()
                .setProductName(name)
                .setDescription(description)
                .setPrice(price)
                .setStore(store)
                .setCategory(category);

        Long id = productDao.save(entity1);

        String expectedName = "name";
        String expectedDescription = "description";
        double expectedPrice = 100.0;
        String expectedStore = "store";
        String expectedCategory = "category";

        ProductEntity updatedEntity = productDao.getById(id)
                .setProductName(expectedName)
                .setDescription(expectedDescription)
                .setPrice(expectedPrice)
                .setStore(expectedStore)
                .setCategory(expectedCategory);

        assertEquals(expectedName, updatedEntity.getProductName());
        assertEquals(expectedDescription, updatedEntity.getDescription());
        assertEquals(expectedPrice, updatedEntity.getPrice());
        assertEquals(expectedStore, updatedEntity.getStore());
        assertEquals(expectedCategory, updatedEntity.getCategory());
    }
}