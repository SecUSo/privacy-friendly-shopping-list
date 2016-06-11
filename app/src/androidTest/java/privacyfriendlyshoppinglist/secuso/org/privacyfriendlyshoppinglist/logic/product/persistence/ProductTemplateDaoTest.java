package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence;

import org.joda.time.DateTime;
import org.junit.Test;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.AbstractDatabaseTest;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactoryForTests;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence.entity.ProductTemplateEntity;

import java.util.Date;
import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 09.06.16 creation date
 */
public class ProductTemplateDaoTest extends AbstractDatabaseTest
{
    private ProductTemplateDao productTemplateDao;

    @Override
    protected void setupBeforeEachTest()
    {
        InstanceFactoryForTests instanceFactory = new InstanceFactoryForTests();
        productTemplateDao = (ProductTemplateDao) instanceFactory.createInstance(getContext(), ProductTemplateDao.class);
    }

    @Test
    public void testSave()
    {
        ProductTemplateEntity entity = new ProductTemplateEntity();
        entity.setProductName("name");
        entity.setCategory("category");
        entity.setHistoryCount(5);
        entity.setLastTimePurchased(new DateTime("2016-06-09").toDate());
        entity.setDefaultStore("store");
        entity.setDefaultNotes("notes");
        entity.setDefaultIcon(10);

        Long id = productTemplateDao.save(entity);
        assertNotNull(id);
    }

    @Test
    public void testGetById()
    {
        String expectedName = "name";
        String expectedCategory = "category";
        Integer expectedHistoryCount = 5;
        Date expectedLastTimePurchased = new DateTime("2016-06-09").toDate();
        String expectedStore = "store";
        String expectedNotes = "notes";
        Integer expectedIcon = 10;

        ProductTemplateEntity entity = new ProductTemplateEntity();
        entity.setProductName(expectedName);
        entity.setCategory(expectedCategory);
        entity.setHistoryCount(expectedHistoryCount);
        entity.setLastTimePurchased(expectedLastTimePurchased);
        entity.setDefaultStore(expectedStore);
        entity.setDefaultNotes(expectedNotes);
        entity.setDefaultIcon(expectedIcon);
        Long id = productTemplateDao.save(entity);

        ProductTemplateEntity newEntity = productTemplateDao.getById(id);
        assertEquals(expectedName, newEntity.getProductName());
        assertEquals(expectedCategory, newEntity.getCategory());
        assertEquals(expectedHistoryCount, newEntity.getHistoryCount());
        assertEquals(expectedLastTimePurchased, newEntity.getLastTimePurchased());
        assertEquals(expectedNotes, newEntity.getDefaultNotes());
        assertEquals(expectedStore, newEntity.getDefaultStore());
        assertEquals(expectedIcon, entity.getDefaultIcon());
    }

    @Test
    public void testUpdate()
    {
        String name = "name";
        String category = "category";
        Integer historyCount = 5;
        Date lastTimePurchased = new DateTime("2016-06-09").toDate();
        String store = "store";
        String notes = "notes";
        Integer icon = 10;

        ProductTemplateEntity entity = new ProductTemplateEntity();
        entity.setProductName(name);
        entity.setCategory(category);
        entity.setHistoryCount(historyCount);
        entity.setLastTimePurchased(lastTimePurchased);
        entity.setDefaultStore(store);
        entity.setDefaultNotes(notes);
        entity.setDefaultIcon(icon);
        Long id = productTemplateDao.save(entity);

        String expectedName = "newName";
        String expectedCategory = "newCategory";
        Integer expectedHistoryCount = 10;
        Date expectedLastTimePurchased = new DateTime("2016-06-15").toDate();
        String expectedStore = "newStore";
        String expectedNotes = "newNotes";
        Integer expectedIcon = 20;

        ProductTemplateEntity newEntity = productTemplateDao.getById(id);
        newEntity.setProductName(expectedName);
        newEntity.setCategory(expectedCategory);
        newEntity.setHistoryCount(expectedHistoryCount);
        newEntity.setLastTimePurchased(expectedLastTimePurchased);
        newEntity.setDefaultStore(expectedStore);
        newEntity.setDefaultNotes(expectedNotes);
        newEntity.setDefaultIcon(expectedIcon);

        productTemplateDao.save(newEntity);

        ProductTemplateEntity updatedEntity = productTemplateDao.getById(id);
        assertEquals(expectedName, updatedEntity.getProductName());
        assertEquals(expectedCategory, updatedEntity.getCategory());
        assertEquals(expectedHistoryCount, updatedEntity.getHistoryCount());
        assertEquals(expectedLastTimePurchased, updatedEntity.getLastTimePurchased());
        assertEquals(expectedNotes, updatedEntity.getDefaultNotes());
        assertEquals(expectedStore, updatedEntity.getDefaultStore());
        assertEquals(expectedIcon, updatedEntity.getDefaultIcon());
    }

    @Test
    public void testGetAllEntities()
    {
        ProductTemplateEntity entity1 = new ProductTemplateEntity();
        ProductTemplateEntity entity2 = new ProductTemplateEntity();
        ProductTemplateEntity entity3 = new ProductTemplateEntity();
        entity1.setProductName("entity1");
        entity2.setProductName("entity2");
        entity3.setProductName("entity3");

        productTemplateDao.save(entity1);
        productTemplateDao.save(entity2);
        productTemplateDao.save(entity3);

        List<ProductTemplateEntity> allEntities = productTemplateDao.getAllEntities();
        int expectedSize = 3;
        assertEquals(expectedSize, allEntities.size());
    }

    @Test
    public void deleteById()
    {
        ProductTemplateEntity entity = new ProductTemplateEntity();
        entity.setProductName("entity");
        Long id = productTemplateDao.save(entity);

        Boolean deleted = productTemplateDao.deleteById(id);
        assertTrue(deleted);

        List<ProductTemplateEntity> allEntities = productTemplateDao.getAllEntities();
        int expectedSize = 0;
        assertEquals(expectedSize,allEntities.size());
    }

}
