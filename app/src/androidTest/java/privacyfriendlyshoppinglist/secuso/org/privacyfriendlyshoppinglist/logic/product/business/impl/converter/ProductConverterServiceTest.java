package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.impl.converter;

import org.joda.time.DateTime;
import org.junit.Test;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.AbstractDatabaseTest;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactoryForTests;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence.entity.ProductItemEntity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence.entity.ProductTemplateEntity;

import java.util.Date;

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
    public void testConvertDtoToEntity() throws Exception
    {
        String expectedProductId = "1";
        String expectedQuantity = "5";
        String expectedQuantityPurchased = "4";
        String expectedNotes = "Some Notes";
        String expectedStore = "Store";
        String expectedPrice = "10.00";
        String purchaseDate = "Tue 07/12/2016 15:52";

        ProductDto dto = new ProductDto();
        dto.setProductId(expectedProductId);
        dto.setQuantity(expectedQuantity);
        dto.setQuantityPurchased(expectedQuantityPurchased);
        dto.setProductNotes(expectedNotes);
        dto.setProductStore(expectedStore);
        dto.setProductPrice(expectedPrice);
        dto.setPurchasedDate(purchaseDate);
        dto.setSelected(true);

        ProductItemEntity entity = new ProductItemEntity();
        converterService.convertDtoToEntity(dto, entity);

        assertEquals(Long.valueOf(expectedProductId), entity.getId());
        assertEquals(Integer.valueOf(expectedQuantity), entity.getQuantity());
        assertEquals(Integer.valueOf(expectedQuantityPurchased), entity.getQuantityPurchased());
        assertEquals(expectedNotes, entity.getNotes());
        assertEquals(expectedStore, entity.getStore());
        assertEquals(Double.valueOf(expectedPrice), entity.getPrice());
        assertTrue(entity.getSelected());

        Date expectedDate = new DateTime("2016-07-12").withHourOfDay(15).withMinuteOfHour(52).toDate();
        assertEquals(expectedDate, entity.getPurchasedDate());
    }

    @Test
    public void testConvertDtoToTemplateEntity() throws Exception
    {
        String templateId = "1";
        String expectedProductName = "product";
        String expectedCategory = "category";
        String expectedHistoryCount = "5";
        String lastTimePurchased = "Tue 07/12/2016 15:52";
        String expectedDefaultNotes = "default notes";
        String expectedDefaultStore = "store";

        ProductDto dto = new ProductDto();
        dto.setId(templateId);
        dto.setProductName(expectedProductName);
        dto.setProductCategory(expectedCategory);
        dto.setHistoryCount(expectedHistoryCount);
        dto.setPurchasedDate(lastTimePurchased);
        dto.setDefaultNotes(expectedDefaultNotes);
        dto.setDefaultStore(expectedDefaultStore);

        ProductTemplateEntity templateEntity = new ProductTemplateEntity();
        converterService.convertDtoToTemplateEntity(dto, templateEntity);

        assertEquals(Long.valueOf(templateId), templateEntity.getId());
        assertEquals(expectedProductName, templateEntity.getProductName());
        assertEquals(expectedCategory, templateEntity.getCategory());
        assertEquals(Integer.valueOf(expectedHistoryCount), templateEntity.getHistoryCount());

        Date expectedDate = new DateTime("2016-07-12").withHourOfDay(15).withMinuteOfHour(52).toDate();
        assertEquals(expectedDate, templateEntity.getLastTimePurchased());
        assertEquals(expectedDefaultNotes, templateEntity.getDefaultNotes());
        assertEquals(expectedDefaultStore, templateEntity.getDefaultStore());

    }

    @Test
    public void convertTemplateEntityToDto() throws Exception
    {

    }

    @Test
    public void convertEntitiesToDto() throws Exception
    {

    }
}