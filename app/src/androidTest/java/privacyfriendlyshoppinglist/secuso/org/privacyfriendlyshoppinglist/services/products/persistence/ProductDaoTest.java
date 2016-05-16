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

    @Before
    public void setUp() throws Exception
    {
        context = new RenamingDelegatingContext(getContext(), "test_");

    }

    @Test
    public void testSave() throws Exception
    {
        ProductDao productDao = new ProductDaoImpl();
        String aProductName = "Product_Name";

        ProductEntity entity = getEntity(aProductName);
        Long id = productDao.save(context, entity);
        assertNotNull(id);
    }


    @Test
    public void testGetById() throws Exception
    {
        ProductDao productDao = new ProductDaoImpl();
        String expectedName = "Product_Name";

        ProductEntity entity = getEntity(expectedName);
        Long id = productDao.save(context, entity);

        ProductEntity newEntity = productDao.getById(context, id);
        assertEquals(expectedName, newEntity.getProductName());
    }

    private ProductEntity getEntity(String productName)
    {
        ProductEntity entity = new ProductEntity();
        entity.setProductName(productName);
        return entity;
    }
}