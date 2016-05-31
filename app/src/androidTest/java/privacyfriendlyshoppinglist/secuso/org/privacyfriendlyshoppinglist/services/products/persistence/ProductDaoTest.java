package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.services.products.persistence;

import org.joda.time.DateTime;
import org.junit.Test;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.AbstractTest;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.ContextManager;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.DB;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.services.products.persistence.entity.ProductEntity;

import java.util.Date;
import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.05.16 18:33 creation date
 */
public class ProductDaoTest extends AbstractTest
{
    private ProductDao productDao;

    @Override
    public void setupBeforeEachTest()
    {
        productDao = new ContextManager<ProductDao>().getInstance(getContext(), DB.TEST, ProductDao.class);
        // delete database before each test
        getContext().deleteDatabase(DB.TEST.getDbName());
    }

    @Override
    protected void cleanAfterEachTest()
    {
        // uncomment to delete data base after each test
        // getContext().deleteDatabase(DB.TEST.getDbName());
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
                .setLastDate(expectedDate);

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
        Date date = new DateTime("2015-05-31").toDate();

        ProductEntity entity1 = new ProductEntity()
                .setProductName(name)
                .setDescription(description)
                .setPrice(price)
                .setStore(store)
                .setCategory(category)
                .setLastDate(date);

        // save an entity
        Long id = productDao.save(entity1);

        String expectedName = "newName";
        String expectedDescription = "newDescription";
        double expectedPrice = 200.0;
        String expectedStore = "newStore";
        String expectedCategory = "newCategory";
        Date expectedDate = new DateTime("2015-06-20").toDate();

        // retrieve entity and update the fields
        ProductEntity newEntity = productDao.getById(id)
                .setProductName(expectedName)
                .setDescription(expectedDescription)
                .setPrice(expectedPrice)
                .setStore(expectedStore)
                .setCategory(expectedCategory)
                .setLastDate(expectedDate);

        // save updated entity
        productDao.save(newEntity);

        // get entity again from DB
        ProductEntity updatedEntity = productDao.getById(id);

        // verify that the values were updated
        assertEquals(expectedName, updatedEntity.getProductName());
        assertEquals(expectedDescription, updatedEntity.getDescription());
        assertEquals(expectedPrice, updatedEntity.getPrice());
        assertEquals(expectedStore, updatedEntity.getStore());
        assertEquals(expectedCategory, updatedEntity.getCategory());
        assertEquals(expectedDate, updatedEntity.getLastDate());
    }

    @Test
    public void testGetAllEntities()
    {
        ProductEntity entity1 = new ProductEntity().setProductName("name1");
        ProductEntity entity2 = new ProductEntity().setProductName("name2");
        ProductEntity entity3 = new ProductEntity().setProductName("name3");
        ProductEntity entity4 = new ProductEntity().setProductName("name4");
        ProductEntity entity5 = new ProductEntity().setProductName("name5");

        productDao.save(entity1);
        productDao.save(entity2);
        productDao.save(entity3);
        productDao.save(entity4);
        productDao.save(entity5);

        List<ProductEntity> allEntities = productDao.getAllEntities();
        int expectedNumberOfEntities = 5;
        assertEquals(expectedNumberOfEntities, allEntities.size());
    }

    @Test(expected = Exception.class)
    public void testNameCannotBeNull()
    {
        ProductEntity entity = new ProductEntity();
        productDao.save(entity);
    }

    @Test
    public void testDeleteById()
    {
        ProductEntity entity = new ProductEntity().setProductName("name");
        Long id = productDao.save(entity);
        boolean deleted = productDao.deleteById(id);
        assertTrue(deleted);

        List<ProductEntity> allEntities = productDao.getAllEntities();
        int expectedSize = 0;
        assertEquals(expectedSize, allEntities.size());


    }
}