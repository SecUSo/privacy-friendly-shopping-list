package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.products.persistence;

import org.joda.time.DateTime;
import org.junit.Test;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.AbstractTest;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.dagger.context.config.AppContextModule;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.ContextManager;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.DB;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.products.persistence.entity.ProductEntity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.ShoppingListDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.entity.ShoppingListEntity;

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
    private ShoppingListDao shoppingListDao;

    @Override
    public void setupBeforeEachTest()
    {
        productDao = new ContextManager<ProductDao>().getInstance(getContext(), DB.TEST, ProductDao.class);
        shoppingListDao = new ContextManager<ShoppingListDao>().getInstance(getContext(), DB.TEST, ShoppingListDao.class);
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
    public void testSave()
    {
        ShoppingListEntity listEntity = new ShoppingListEntity()
                .setListName("listName");
        shoppingListDao.save(listEntity);

        ProductEntity entity = new ProductEntity()
                .setProductName("name")
                .setDescription("description")
                .setPrice(100.0)
                .setStore("store")
                .setCategory("category")
                .setLastDate(new DateTime("2015-05-31").toDate())
                .setShoppingList(listEntity);

        Long id = productDao.save(entity);

        assertNotNull(id);
    }


    @Test
    public void testGetById()
    {
        ShoppingListEntity listEntity = new ShoppingListEntity()
                .setListName("listName");
        Long expectedListId = shoppingListDao.save(listEntity);

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
                .setLastDate(expectedDate)
                .setShoppingList(listEntity);

        Long id = productDao.save(entity);

        ProductEntity newEntity = productDao.getById(id);
        assertEquals(expectedName, newEntity.getProductName());
        assertEquals(expectedDescription, newEntity.getDescription());
        assertEquals(expectedPrice, newEntity.getPrice());
        assertEquals(expectedStore, newEntity.getStore());
        assertEquals(expectedCategory, newEntity.getCategory());
        assertEquals(expectedDate, newEntity.getLastDate());
        assertEquals(expectedListId, newEntity.getShoppingList().getId());
    }

    @Test
    public void testUpdate()
    {
        ShoppingListEntity listEntity = new ShoppingListEntity()
                .setListName("listName");
        shoppingListDao.save(listEntity);

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
                .setLastDate(date)
                .setShoppingList(listEntity);

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
        ShoppingListEntity listEntity = new ShoppingListEntity()
                .setListName("listName");
        shoppingListDao.save(listEntity);

        ProductEntity entity1 = new ProductEntity().setProductName("name1").setShoppingList(listEntity);
        ProductEntity entity2 = new ProductEntity().setProductName("name2").setShoppingList(listEntity);
        ProductEntity entity3 = new ProductEntity().setProductName("name3").setShoppingList(listEntity);
        ProductEntity entity4 = new ProductEntity().setProductName("name4").setShoppingList(listEntity);
        ProductEntity entity5 = new ProductEntity().setProductName("name5").setShoppingList(listEntity);

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
        ShoppingListEntity listEntity = new ShoppingListEntity()
                .setListName("listName");
        shoppingListDao.save(listEntity);

        ProductEntity entity = new ProductEntity()
                .setShoppingList(listEntity);
        productDao.save(entity);
    }

    @Test(expected = Exception.class)
    public void testShoppingListCannotBeNull()
    {
        ProductEntity entity = new ProductEntity()
                .setProductName("name");
        productDao.save(entity);
    }

    @Test
    public void testDeleteById()
    {
        ShoppingListEntity listEntity = new ShoppingListEntity()
                .setListName("listName");
        shoppingListDao.save(listEntity);

        ProductEntity entity = new ProductEntity().setProductName("name").setShoppingList(listEntity);
        Long id = productDao.save(entity);
        boolean deleted = productDao.deleteById(id);
        assertTrue(deleted);

        List<ProductEntity> allEntities = productDao.getAllEntities();
        int expectedSize = 0;
        assertEquals(expectedSize, allEntities.size());


    }
}