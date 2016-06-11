package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business;

import org.joda.time.DateTime;
import org.junit.Test;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.AbstractDatabaseTest;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.DB;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListDto;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 11.06.16 creation date
 */
public class ShoppingListServiceTest extends AbstractDatabaseTest
{

    private ShoppingListService shoppingListService;

    @Override
    protected void setupBeforeEachTest()
    {
        shoppingListService = new InstanceFactory<ShoppingListService>(DB.TEST).createInstance(getContext(), ShoppingListService.class);
    }

    @Test
    public void testSaveOrUpdate()
    {
        ListDto dto = new ListDto();
        dto.setListName("name");
        dto.setPriority("HIGH");
        dto.setIcon(10);
        dto.setDeadline(new DateTime("2016-06-11").toDate());
        dto.setNotes("notes");

        shoppingListService.saveOrUpdate(dto);
        assertNotNull(dto.getId()); //todo gif
    }
}