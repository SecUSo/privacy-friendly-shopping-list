package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.services.products.persistence;

import android.test.AndroidTestCase;
import org.junit.Test;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.ContextManager;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.services.products.persistence.entity.ProductEntity;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.05.16 18:33 creation date
 */
public class ProductDaoTest extends AndroidTestCase
{
    ProductDao productDao;

    @Override
    public void setUp()
    {
        productDao = new ContextManager<ProductDao>().getInstance(getContext(), ProductDao.class);
    }

    @Test
    public void testSave() throws Exception
    {

        String aProductName = "Product_Name";

        ProductEntity entity = getEntity(aProductName);
        Long id = productDao.save(entity);
        assertNotNull(id);
    }


    @Test
    public void testGetById() throws Exception
    {
        String expectedName = "Product_Name";

        ProductEntity entity = getEntity(expectedName);
        Long id = productDao.save(entity);

        ProductEntity newEntity = productDao.getById(id);
        assertEquals(expectedName, newEntity.getProductName());
    }

    @Test
    public void testUpdate() throws Exception
    {
        String product1 = "Product 1";
        String product2 = "Product 2";
        String product3 = "Product 3";

        ProductEntity entity1 = getEntity(product1);
        ProductEntity entity2 = getEntity(product2);
        ProductEntity entity3 = getEntity(product3);

        this.productDao.save(entity1);
        Long id2 = this.productDao.save(entity2);

        entity3.setId(id2);
        this.productDao.save(entity3);

        ProductEntity updatedEntity = this.productDao.getById(id2);
        assertEquals(product3, updatedEntity.getProductName());


    }

    private ProductEntity getEntity(String productName)
    {
        ProductEntity entity = new ProductEntity();
        entity.setProductName(productName);
        return entity;
    }
}