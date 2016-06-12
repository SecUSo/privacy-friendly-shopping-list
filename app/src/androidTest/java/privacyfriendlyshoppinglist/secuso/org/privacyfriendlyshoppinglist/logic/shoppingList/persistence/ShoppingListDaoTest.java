package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence;

import org.joda.time.DateTime;
import org.junit.Test;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.AbstractDatabaseTest;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactoryForTests;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.entity.ShoppingListEntity;

import java.util.Date;
import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 10.06.16 creation date
 */
public class ShoppingListDaoTest extends AbstractDatabaseTest
{
    private ShoppingListDao shoppingListDao;

    @Override
    protected void setupBeforeEachTest()
    {
        AbstractInstanceFactory instanceFactory = new InstanceFactoryForTests(getContext());
        shoppingListDao = (ShoppingListDao) instanceFactory.createInstance(ShoppingListDao.class);
    }

    @Test
    public void testSave()
    {
        ShoppingListEntity entity = new ShoppingListEntity();
        entity.setListName("name");
        entity.setPriority("HIGH");
        entity.setNotes("notes");
        entity.setDeadline(new DateTime("2016-06-10").toDate());
        entity.setIcon(10);

        Long id = shoppingListDao.save(entity);
        assertNotNull(id);
    }

    @Test
    public void testGetById()
    {
        String expectedName = "name";
        String expectedPriority = "HIGH";
        String expectedNotes = "notes";
        Date expectedDeadLine = new DateTime("2016-06-10").toDate();
        int expectedIcon = 10;

        ShoppingListEntity entity = new ShoppingListEntity();
        entity.setListName(expectedName);
        entity.setPriority(expectedPriority);
        entity.setNotes(expectedNotes);
        entity.setDeadline(expectedDeadLine);
        entity.setIcon(expectedIcon);

        Long id = shoppingListDao.save(entity);

        ShoppingListEntity newEntity = shoppingListDao.getById(id);
        assertEquals(expectedName, newEntity.getListName());
        assertEquals(expectedPriority, newEntity.getPriority());
        assertEquals(expectedNotes, newEntity.getNotes());
        assertEquals((Integer) expectedIcon, newEntity.getIcon());
        assertEquals(expectedDeadLine, newEntity.getDeadline());
    }

    @Test
    public void testGetAllEntities()
    {
        ShoppingListEntity entity1 = new ShoppingListEntity();
        ShoppingListEntity entity2 = new ShoppingListEntity();
        entity1.setListName("entity1");
        entity2.setListName("entity2");

        shoppingListDao.save(entity1);
        shoppingListDao.save(entity2);

        List<ShoppingListEntity> allEntities = shoppingListDao.getAllEntities();
        int expectedSize = 2;
        assertEquals(expectedSize, allEntities.size());
    }

    @Test
    public void testDeleteById()
    {
        ShoppingListEntity entity1 = new ShoppingListEntity();
        ShoppingListEntity entity2 = new ShoppingListEntity();
        entity1.setListName("entity1");
        entity2.setListName("entity2");

        Long id1 = shoppingListDao.save(entity1);
        Long id2 = shoppingListDao.save(entity2);

        List<ShoppingListEntity> allEntities = shoppingListDao.getAllEntities();
        int expectedSize = 2;
        assertEquals(expectedSize, allEntities.size());
    }
}