package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business;

import android.content.res.Resources;
import org.joda.time.DateTime;
import org.junit.Test;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.AbstractDatabaseTest;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactoryForTests;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.DateUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListItem;

import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 11.06.16 creation date
 */
public class ShoppingListServiceTest extends AbstractDatabaseTest
{

    private ShoppingListService shoppingListService;
    private String shortDatePattern;
    private String timePattern;
    private String language;

    @Override
    protected void setupBeforeEachTest()
    {
        AbstractInstanceFactory instanceFactory = new InstanceFactoryForTests(getContext());
        shoppingListService = (ShoppingListService) instanceFactory.createInstance(ShoppingListService.class);

        Resources resources = getContext().getResources();
        shortDatePattern = resources.getString(R.string.date_short_pattern);
        timePattern = resources.getString(R.string.time_pattern);
        language = resources.getString(R.string.language);
    }

    @Test
    public void testService()
    {
        String name = "name";
        String priority = "HIGH";
        int icon = 10;
        DateTime datetime = new DateTime("2016-07-05").withHourOfDay(10).withMinuteOfHour(30);
        String date = DateUtils.getDateAsString(datetime.getMillis(), shortDatePattern, language);
        String time = DateUtils.getDateAsString(datetime.getMillis(), timePattern, language);
        String notes = "notes";

        ListItem item = new ListItem();
        item.setListName(name);
        item.setPriority(priority);
        item.setIcon(icon);
        item.setDeadlineDate(date);
        item.setDeadlineTime(time);
        item.setNotes(notes);

        // test save
        shoppingListService.saveOrUpdate(item).toBlocking().single();
        String id = item.getId();
        assertNotNull(id);

        // test getById
        ListItem newItem = shoppingListService.getById(id).toBlocking().single();
        assertEquals(id, newItem.getId());
        assertEquals(name, newItem.getListName());
        assertEquals(priority, newItem.getPriority());
        assertEquals(icon, newItem.getIcon());
        assertEquals(date, newItem.getDeadlineDate());
        assertEquals(time, newItem.getDeadlineTime());
        assertEquals(notes, newItem.getNotes());

        // test update
        String expectedName = "newName";
        newItem.setListName(expectedName);
        shoppingListService.saveOrUpdate(newItem).toBlocking().single();
        ListItem updatedItem = shoppingListService.getById(id).toBlocking().single();
        assertEquals(expectedName, updatedItem.getListName());

        // test getAllListItems
        //      save another ITEM in order to have 2 entities in the DB
        updatedItem.setId(null); // if id == null, then the item will be saved as a new entity
        shoppingListService.saveOrUpdate(updatedItem).toBlocking().single();

        int expectedSize = 2;
        List<ListItem> allListItems = shoppingListService.getAllListItems().toList().toBlocking().single();
        assertEquals(expectedSize, allListItems.size());

        // test deleteById using the id of the updatedItem
        String newId = updatedItem.getId();
        shoppingListService.deleteById(newId).toBlocking().single();
        int expectedSizeAfterDelete = 1;
        List<ListItem> allListItemsAfterDelete = shoppingListService.getAllListItems().toList().toBlocking().single();
        assertEquals(expectedSizeAfterDelete, allListItemsAfterDelete.size());
    }


}