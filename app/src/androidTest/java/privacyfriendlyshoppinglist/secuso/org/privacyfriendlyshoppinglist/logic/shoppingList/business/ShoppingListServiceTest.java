package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business;

import android.content.res.Resources;
import org.joda.time.DateTime;
import org.junit.Test;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.AbstractDatabaseTest;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactoryForTests;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.DateUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListDto;

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

        ListDto dto = new ListDto();
        dto.setListName(name);
        dto.setPriority(priority);
        dto.setIcon(icon);
        dto.setDeadlineDate(date);
        dto.setDeadlineTime(time);
        dto.setNotes(notes);

        // test save
        shoppingListService.saveOrUpdate(dto);
        String id = dto.getId();
        assertNotNull(id);

        // test getById
        ListDto newDto = shoppingListService.getById(id);
        assertEquals(id, newDto.getId());
        assertEquals(name, newDto.getListName());
        assertEquals(priority, newDto.getPriority());
        assertEquals(icon, newDto.getIcon());
        assertEquals(date, newDto.getDeadlineDate());
        assertEquals(time, newDto.getDeadlineTime());
        assertEquals(notes, newDto.getNotes());

        // test update
        String expectedName = "newName";
        newDto.setListName(expectedName);
        shoppingListService.saveOrUpdate(newDto);
        ListDto updatedDto = shoppingListService.getById(id);
        assertEquals(expectedName, updatedDto.getListName());

        // test getAllListDtos
        //      save another DTO in order to have 2 entities in the DB
        updatedDto.setId(null); // if id == null, then the dto will be saved as a new entity
        shoppingListService.saveOrUpdate(updatedDto);

        int expectedSize = 2;
        List<ListDto> allListDtos = shoppingListService.getAllListDtos();
        assertEquals(expectedSize, allListDtos.size());

        // test deleteById using the id of the updatedDto
        String newId = updatedDto.getId();
        shoppingListService.deleteById(newId);
        int expectedSizeAfterDelete = 1;
        List<ListDto> allListDtosAfterDelete = shoppingListService.getAllListDtos();
        assertEquals(expectedSizeAfterDelete, allListDtosAfterDelete.size());
    }


}