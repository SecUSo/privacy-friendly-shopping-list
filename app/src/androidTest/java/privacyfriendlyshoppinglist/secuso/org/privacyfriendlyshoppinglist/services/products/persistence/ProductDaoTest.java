package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.services.products.persistence;

import android.content.Context;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import org.junit.Before;
import org.junit.Test;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.services.products.persistence.entity.ProductEntity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.services.products.persistence.impl.ProductDaoImpl;

import static org.junit.Assert.*;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.05.16 18:33 creation date
 */
public class ProductDaoTest extends AndroidTestCase
{
    Context context;
    ProductDao productDao;

    @Before
    public void setUp() throws Exception
    {
        productDao = new ProductDaoImpl();
        context = new RenamingDelegatingContext(getContext(), "test_");
    }

    @Test
    public void testSave() throws Exception
    {
        String aProductName = "Product_Name";

        ProductEntity entity = getEntity(aProductName);
        Long id = productDao.save(context, entity);
        assertNotNull(id);
    }


    @Test
    public void testGetById() throws Exception
    {
        String expectedName = "Product_Name";

        ProductEntity entity = getEntity(expectedName);
        Long id = productDao.save(context, entity);

        ProductEntity newEntity = productDao.getById(context, id);
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

        productDao.save(context, entity1);
        Long id2 = productDao.save(context, entity2);

        entity3.setId(id2);
        productDao.save(context, entity3);

        ProductEntity updatedEntity = productDao.getById(context, id2);
        assertEquals(product3, updatedEntity.getProductName());


    }

    private ProductEntity getEntity(String productName)
    {
        ProductEntity entity = new ProductEntity();
        entity.setProductName(productName);
        return entity;
    }
}