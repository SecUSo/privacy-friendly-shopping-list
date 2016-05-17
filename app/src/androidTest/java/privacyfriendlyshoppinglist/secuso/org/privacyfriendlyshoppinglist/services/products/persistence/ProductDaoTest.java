package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.services.products.persistence;

import android.test.AndroidTestCase;
import org.junit.Test;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.services.products.persistence.entity.ProductEntity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.services.products.persistence.impl.ProductDaoImpl;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.05.16 18:33 creation date
 */
public class ProductDaoTest extends AndroidTestCase
{
    ProductDao productDao;

    @Test
    public void testSave() throws Exception
    {
        productDao = new ProductDaoImpl(getContext());

        String aProductName = "Product_Name";

        ProductEntity entity = getEntity(aProductName);
        Long id = productDao.save(entity);
        assertNotNull(id);
    }


    @Test
    public void testGetById() throws Exception
    {
        productDao = new ProductDaoImpl(getContext());
        String expectedName = "Product_Name";

        ProductEntity entity = getEntity(expectedName);
        Long id = productDao.save(entity);

        ProductEntity newEntity = productDao.getById(id);
        assertEquals(expectedName, newEntity.getProductName());
    }

    @Test
    public void testUpdate() throws Exception
    {
        productDao = new ProductDaoImpl(getContext());
        String product1 = "Product 1";
        String product2 = "Product 2";
        String product3 = "Product 3";

        ProductEntity entity1 = getEntity(product1);
        ProductEntity entity2 = getEntity(product2);
        ProductEntity entity3 = getEntity(product3);

        productDao.save(entity1);
        Long id2 = productDao.save(entity2);

        entity3.setId(id2);
        productDao.save(entity3);

        ProductEntity updatedEntity = productDao.getById(id2);
        assertEquals(product3, updatedEntity.getProductName());


    }

    private ProductEntity getEntity(String productName)
    {
        ProductEntity entity = new ProductEntity();
        entity.setProductName(productName);
        return entity;
    }
}