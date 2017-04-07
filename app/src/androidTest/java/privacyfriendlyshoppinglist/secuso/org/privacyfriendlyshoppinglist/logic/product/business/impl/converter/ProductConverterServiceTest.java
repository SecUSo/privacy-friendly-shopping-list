package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.impl.converter;

import org.junit.Test;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.AbstractDatabaseTest;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactoryForTests;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence.entity.ProductItemEntity;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 17.07.16 creation date
 */
public class ProductConverterServiceTest extends AbstractDatabaseTest
{
    private ProductConverterService converterService;

    @Override
    protected void setupBeforeEachTest()
    {
        AbstractInstanceFactory instanceFactory = new InstanceFactoryForTests(getContext());
        converterService = (ProductConverterService) instanceFactory.createInstance(ProductConverterService.class);
    }

    @Test
    public void testConvertItemToEntity() throws Exception
    {
        String expectedProductId = "1";
        String expectedQuantity = "5";
        String expectedNotes = "Some Notes";
        String expectedStore = "Store";
        String expectedPrice = "10.00";

        ProductItem item = new ProductItem();
        item.setId(expectedProductId);
        item.setQuantity(expectedQuantity);
        item.setProductNotes(expectedNotes);
        item.setProductStore(expectedStore);
        item.setProductPrice(expectedPrice);
        item.setChecked(true);

        ProductItemEntity entity = new ProductItemEntity();
        converterService.convertItemToEntity(item, entity);

        assertEquals(Long.valueOf(expectedProductId), entity.getId());
        assertEquals(Integer.valueOf(expectedQuantity), entity.getQuantity());
        assertEquals(expectedNotes, entity.getNotes());
        assertEquals(expectedStore, entity.getStore());
        assertEquals(Double.valueOf(expectedPrice), entity.getPrice());
        assertTrue(entity.getSelected());
    }

    @Test
    public void testConvertEntitiesToItem() throws Exception
    {
        String expectedProductName = "product";
        String expectedCategory = "category";

        Long expectedProductId = 5L;
        int expectedQuantity = 10;
        int expectedQuantityPurchased = 9;
        String expectedNotes = "Some Notes";
        String expectedStore = "Store";
        double expectedPrice = 10.0;
        String expectedPriceString = "10.00";

        ProductItemEntity entity = new ProductItemEntity();
        entity.setCategory(expectedCategory);
        entity.setProductName(expectedProductName);
        entity.setId(expectedProductId);
        entity.setQuantity(expectedQuantity);
        entity.setNotes(expectedNotes);
        entity.setStore(expectedStore);
        entity.setPrice(expectedPrice);
        entity.setSelected(true);

        ProductItem item = new ProductItem();
        converterService.convertEntitiesToItem(entity, item);

        assertEquals(expectedProductName, item.getProductName());
        assertEquals(expectedCategory, item.getProductCategory());

        assertEquals(String.valueOf(expectedProductId), item.getId());
        assertEquals(String.valueOf(expectedQuantity), item.getQuantity());
        assertEquals(expectedNotes, item.getProductNotes());
        assertEquals(expectedStore, item.getProductStore());
        assertEquals(expectedPriceString, item.getProductPrice());
        assertTrue(entity.getSelected());
    }
}