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
    public void save() throws Exception
    {
        String expectedName = "Product_Name";

        ProductDao productDao = new ProductDaoImpl();
        ProductEntity entity = new ProductEntity();
        entity.setId(1L);
        entity.setProductName(expectedName);
        Long id = productDao.save(context, entity);
        assertNotNull(id);

        ProductEntity entity2 = productDao.getById(context, id);
        assertEquals(expectedName, entity2.getProductName());
    }

}