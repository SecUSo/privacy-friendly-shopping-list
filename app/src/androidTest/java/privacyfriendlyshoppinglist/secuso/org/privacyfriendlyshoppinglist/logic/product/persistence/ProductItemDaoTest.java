package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence;

import com.j256.ormlite.dao.ForeignCollection;
import org.junit.Test;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.AbstractDatabaseTest;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactoryForTests;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence.entity.ProductItemEntity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.ShoppingListDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.entity.ShoppingListEntity;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 11.06.16 creation date
 */
public class ProductItemDaoTest extends AbstractDatabaseTest
{

    private ShoppingListDao shoppingListDao;
    private ProductItemDao productItemDao;
    private ShoppingListEntity shoppingList;


    @Override
    protected void setupBeforeEachTest()
    {
        AbstractInstanceFactory instanceFactory = new InstanceFactoryForTests(getContext());

        shoppingListDao = (ShoppingListDao) instanceFactory.createInstance(ShoppingListDao.class);
        productItemDao = (ProductItemDao) instanceFactory.createInstance(ProductItemDao.class);

        shoppingList = new ShoppingListEntity();

        shoppingList.setListName("shoppingList");
        shoppingListDao.save(shoppingList);
    }

    @Test
    public void testSave()
    {
        ProductItemEntity entity = new ProductItemEntity();
        entity.setShoppingList(shoppingList);

        Long id = productItemDao.save(entity);
        assertNotNull(id);

        // Shopping List and Product Template must be able to find the new Product Item
        int expectedSize = 1;
        ShoppingListEntity shoppingList = shoppingListDao.getAllEntities().get(0);
        ForeignCollection<ProductItemEntity> products1 = shoppingList.getProducts();
        assertEquals(expectedSize, products1.size());
    }

    @Test(expected = Exception.class)
    public void testSaveWithoutProductTemplate()
    {
        ProductItemEntity entity = new ProductItemEntity();
        entity.setShoppingList(shoppingList);

        productItemDao.save(entity);
    }

    @Test(expected = Exception.class)
    public void testSaveWithoutShoppingList()
    {
        ProductItemEntity entity = new ProductItemEntity();
        productItemDao.save(entity);
    }
}