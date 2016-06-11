package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.products.persistence;

import com.j256.ormlite.dao.ForeignCollection;
import org.junit.Test;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.AbstractDatabaseTest;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.ContextManager;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.DB;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.products.persistence.entity.ProductItemEntity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.products.persistence.entity.ProductTemplateEntity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.ShoppingListDaoNew;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.entity.ShoppingListEntityNew;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 11.06.16 creation date
 */
public class ProductItemDaoTest extends AbstractDatabaseTest
{

    private ShoppingListDaoNew shoppingListDao;
    private ProductTemplateDao productTemplateDao;
    private ProductItemDao productItemDao;
    private ProductTemplateEntity productTemplate;
    private ShoppingListEntityNew shoppingList;


    @Override
    protected void setupBeforeEachTest()
    {
        shoppingListDao = new ContextManager<ShoppingListDaoNew>().getInstance(getContext(), DB.TEST, ShoppingListDaoNew.class);
        productTemplateDao = new ContextManager<ProductTemplateDao>().getInstance(getContext(), DB.TEST, ProductTemplateDao.class);
        productItemDao = new ContextManager<ProductItemDao>().getInstance(getContext(), DB.TEST, ProductItemDao.class);

        shoppingList = new ShoppingListEntityNew();
        productTemplate = new ProductTemplateEntity();

        shoppingList.setListName("shoppingList");
        shoppingListDao.save(shoppingList);

        productTemplate.setProductName("product");
        productTemplateDao.save(productTemplate);
    }

    @Test
    public void testSave()
    {
        ProductItemEntity entity = new ProductItemEntity();
        entity.setProductTemplate(productTemplate);
        entity.setShoppingList(shoppingList);

        Long id = productItemDao.save(entity);
        assertNotNull(id);

        // Shopping List and Product Template must be able to find the new Product Item
        int expectedSize = 1;
        ShoppingListEntityNew shoppingList = shoppingListDao.getAllEntities().get(0);
        ForeignCollection<ProductItemEntity> products1 = shoppingList.getProducts();
        assertEquals(expectedSize, products1.size());

        ProductTemplateEntity productTemplate = productTemplateDao.getAllEntities().get(0);
        ForeignCollection<ProductItemEntity> products2 = productTemplate.getProducts();
        assertEquals(expectedSize, products2.size());
    }

    @Test(expected = Exception.class)
    public void testSaveWithoutProduct()
    {
        ProductItemEntity entity = new ProductItemEntity();
        entity.setShoppingList(shoppingList);

        productItemDao.save(entity);
    }

    @Test(expected = Exception.class)
    public void testSaveWithoutShoppingList()
    {
        ProductItemEntity entity = new ProductItemEntity();
        entity.setProductTemplate(productTemplate);

        productItemDao.save(entity);
    }
}