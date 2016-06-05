package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence;


import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.dao.LazyForeignCollection;
import org.junit.Test;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.AbstractTest;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.ContextManager;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.DB;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.products.persistence.ProductDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.products.persistence.entity.ProductEntity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.entity.ShoppingListEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 05.06.16 creation date
 */
public class ShoppingListDaoTest extends AbstractTest
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

    }

    @Test
    public void testSaveWithoutProducts()
    {
        ShoppingListEntity entity = new ShoppingListEntity()
                .setListName("name");

        Long id = shoppingListDao.save(entity);
        assertNotNull(id);
    }

    @Test
    public void testSaveWithProducts()
    {
        ShoppingListEntity listEntity = new ShoppingListEntity()
                .setListName("name");
        shoppingListDao.save(listEntity);

        ProductEntity p1 = new ProductEntity().setProductName("p1").setShoppingList(listEntity);
        ProductEntity p2 = new ProductEntity().setProductName("p2").setShoppingList(listEntity);
        ProductEntity p3 = new ProductEntity().setProductName("p3").setShoppingList(listEntity);
        ProductEntity p4 = new ProductEntity().setProductName("p4").setShoppingList(listEntity);

        productDao.save(p1);
        productDao.save(p2);
        productDao.save(p3);
        productDao.save(p4);

        List<ShoppingListEntity> allEntities = shoppingListDao.getAllEntities();
        int expectedListSize = 1;
        assertEquals(expectedListSize, allEntities.size());

        ShoppingListEntity retrievedListEntity = allEntities.get(0);
        ForeignCollection<ProductEntity> products = retrievedListEntity.getProducts();
        int actualNumberOfProducts = products.size();
        int expectedNumberOfProducts = 4;
        assertEquals(expectedNumberOfProducts, actualNumberOfProducts);
    }

    @Test
    public void testGetById()
    {

    }

    @Test
    public void testGetAllEntities()
    {

    }

    @Test
    public void testDeleteById()
    {

    }

}