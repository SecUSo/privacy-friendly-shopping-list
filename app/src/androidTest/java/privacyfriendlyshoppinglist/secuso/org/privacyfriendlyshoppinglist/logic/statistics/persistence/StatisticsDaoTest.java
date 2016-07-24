package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.persistence;

import org.joda.time.DateTime;
import org.junit.Test;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.AbstractDatabaseTest;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactoryForTests;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.persistence.entity.StatisticEntryEntity;

import java.util.Date;
import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 23.07.16 creation date
 */
public class StatisticsDaoTest extends AbstractDatabaseTest
{
    private StatisticsDao statisticsDao;

    @Override
    protected void setupBeforeEachTest()
    {
        AbstractInstanceFactory instanceFactory = new InstanceFactoryForTests(getContext());
        statisticsDao = (StatisticsDao) instanceFactory.createInstance(StatisticsDao.class);
    }

    @Test
    public void testSave() throws Exception
    {
        StatisticEntryEntity entity = getDefaultEntity();

        statisticsDao.save(entity);
        Long id1 = entity.getId();
        assertNotNull(id1);
    }

    @Test
    public void testGetById() throws Exception
    {
        StatisticEntryEntity entity = getDefaultEntity();

        statisticsDao.save(entity);
        Long id = entity.getId();

        StatisticEntryEntity retrievedEntity = statisticsDao.getById(id);
        assertEquals(entity.getProductName(), retrievedEntity.getProductName());
        // no necessary to check other properties
    }

    @Test
    public void testGetAllEntities() throws Exception
    {
        List<StatisticEntryEntity> entities;
        int expectedSize;

        entities = statisticsDao.getAllEntities();
        expectedSize = 0;
        assertEquals(expectedSize, entities.size());

        StatisticEntryEntity entity1 = getDefaultEntity();
        StatisticEntryEntity entity2 = getDefaultEntity();
        StatisticEntryEntity entity3 = getDefaultEntity();
        StatisticEntryEntity entity4 = getDefaultEntity();

        statisticsDao.save(entity1);
        statisticsDao.save(entity2);
        statisticsDao.save(entity3);
        statisticsDao.save(entity4);

        entities = statisticsDao.getAllEntities();
        expectedSize = 4;
        assertEquals(expectedSize, entities.size());
    }

    @Test
    public void testDeleteById() throws Exception
    {
        List<StatisticEntryEntity> entities;
        int expectedSize;

        entities = statisticsDao.getAllEntities();
        expectedSize = 0;
        assertEquals(expectedSize, entities.size());

        StatisticEntryEntity entity1 = getDefaultEntity();
        StatisticEntryEntity entity2 = getDefaultEntity();
        StatisticEntryEntity entity3 = getDefaultEntity();

        statisticsDao.save(entity1);
        statisticsDao.save(entity2);
        statisticsDao.save(entity3);

        entities = statisticsDao.getAllEntities();
        expectedSize = 3;
        assertEquals(expectedSize, entities.size());

        statisticsDao.deleteById(entity2.getId());

        entities = statisticsDao.getAllEntities();
        expectedSize = 2;
        assertEquals(expectedSize, entities.size());
    }

    public StatisticEntryEntity getDefaultEntity()
    {
        StatisticEntryEntity entity = new StatisticEntryEntity();
        String category = "Category";
        String productName = "ProductName";
        String productStore = "ProductStore";
        int quantity = 5;
        double price = 10.0;
        Date date = new DateTime("2016-07-23").toDate();

        entity.setProductCategory(category);
        entity.setProductName(productName);
        entity.setProductStore(productStore);
        entity.setQuantity(quantity);
        entity.setUnitPrice(price);
        entity.setRecordDate(date);
        return entity;
    }
}