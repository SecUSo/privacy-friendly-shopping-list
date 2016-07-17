package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.impl.comparators;

import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListDto;

import java.util.Comparator;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.07.16 creation date
 */
public class ListsComparators
{
    public static Comparator<ListDto> getNameComparator(boolean ascending)
    {
        int asc = 1;
        if ( !ascending )
        {
            asc = -1;
        }
        int finalAsc = asc;
        return (lhs, rhs) -> (lhs.getListName().compareTo(rhs.getListName()) * finalAsc);
    }
}
