package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.impl.converter;

import org.joda.time.DateTime;
import org.junit.Test;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.AbstractDatabaseTest;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactoryForTests;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductTemplateDto;
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
        dto.setLastTimePurchased(purchaseDate);
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
        dto.setLastTimePurchased(lastTimePurchased);
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
    public void testConvertTemplateEntityToDto() throws Exception
    {
        Long templateId = 1L;
        String expectedProductName = "product";
        String expectedCategory = "category";
        int expectedHistoryCount = 5;
        Date expectedDate = new DateTime("2016-07-12").withHourOfDay(15).withMinuteOfHour(52).toDate();
        String expectedDefaultNotes = "default notes";
        String expectedDefaultStore = "store";

        ProductTemplateEntity entity = new ProductTemplateEntity();
        entity.setId(templateId);
        entity.setProductName(expectedProductName);
        entity.setCategory(expectedCategory);
        entity.setHistoryCount(expectedHistoryCount);
        entity.setLastTimePurchased(expectedDate);
        entity.setDefaultNotes(expectedDefaultNotes);
        entity.setDefaultStore(expectedDefaultStore);

        ProductTemplateDto dto = new ProductTemplateDto();
        converterService.convertTemplateEntityToDto(entity, dto);

        assertEquals(String.valueOf(templateId), dto.getId());
        assertEquals(expectedProductName, dto.getProductName());
        assertEquals(expectedCategory, dto.getProductCategory());
        assertEquals(String.valueOf(expectedHistoryCount), dto.getHistoryCount());
        String lastTimePurchased = "Tue 07/12/2016 15:52";
        assertEquals(lastTimePurchased, dto.getLastTimePurchased());
        assertEquals(expectedDefaultNotes, dto.getDefaultNotes());
        assertEquals(expectedDefaultStore, dto.getDefaultStore());
    }

    @Test
    public void testConvertEntitiesToDto() throws Exception
    {
        Long templateId = 1L;
        String expectedProductName = "product";
        String expectedCategory = "category";
        int expectedHistoryCount = 5;
        Date expectedDate = new DateTime("2016-07-12").withHourOfDay(15).withMinuteOfHour(52).toDate();
        String expectedDefaultNotes = "default notes";
        String expectedDefaultStore = "store";

        ProductTemplateEntity templateEntity = new ProductTemplateEntity();
        templateEntity.setId(templateId);
        templateEntity.setProductName(expectedProductName);
        templateEntity.setCategory(expectedCategory);
        templateEntity.setHistoryCount(expectedHistoryCount);
        templateEntity.setLastTimePurchased(expectedDate);
        templateEntity.setDefaultNotes(expectedDefaultNotes);
        templateEntity.setDefaultStore(expectedDefaultStore);

        Long expectedProductId = 5L;
        int expectedQuantity = 10;
        int expectedQuantityPurchased = 9;
        String expectedNotes = "Some Notes";
        String expectedStore = "Store";
        double expectedPrice = 10.0;

        ProductItemEntity entity = new ProductItemEntity();
        entity.setId(expectedProductId);
        entity.setQuantity(expectedQuantity);
        entity.setQuantityPurchased(expectedQuantityPurchased);
        entity.setNotes(expectedNotes);
        entity.setStore(expectedStore);
        entity.setPrice(expectedPrice);
        entity.setPurchasedDate(expectedDate);
        entity.setSelected(true);

        ProductDto dto = new ProductDto();
        converterService.convertEntitiesToDto(templateEntity, entity, dto);

        assertEquals(String.valueOf(templateId), dto.getId());
        assertEquals(expectedProductName, dto.getProductName());
        assertEquals(expectedCategory, dto.getProductCategory());
        assertEquals(String.valueOf(expectedHistoryCount), dto.getHistoryCount());
        String lastTimePurchased = "Tue 07/12/2016 15:52";
        assertEquals(lastTimePurchased, dto.getLastTimePurchased());
        assertEquals(expectedDefaultNotes, dto.getDefaultNotes());
        assertEquals(expectedDefaultStore, dto.getDefaultStore());

        assertEquals(String.valueOf(expectedProductId), dto.getProductId());
        assertEquals(String.valueOf(expectedQuantity), dto.getQuantity());
        assertEquals(String.valueOf(expectedQuantityPurchased), dto.getQuantityPurchased());
        assertEquals(expectedNotes, dto.getProductNotes());
        assertEquals(expectedStore, dto.getProductStore());
        assertEquals(String.valueOf(expectedPrice), dto.getProductPrice());
        assertTrue(entity.getSelected());
    }
}