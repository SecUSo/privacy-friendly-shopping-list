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
        int ascendingFactor = getAsecndingFactor(ascending);
        return (lhs, rhs) -> (lhs.getListName().compareTo(rhs.getListName()) * ascendingFactor);
    }

    public static Comparator<ListDto> getPriorityComparator(boolean ascending)
    {
        // priority: 1 is lower than 0. Therefore we invert the ascendingFactor
        int ascendingFactor = getAsecndingFactor(ascending) * -1;
        return (lhs, rhs) -> (lhs.getPriority().compareTo(rhs.getPriority()) * ascendingFactor);
    }

    public static int getAsecndingFactor(boolean ascending)
    {
        int asc = 1;
        if ( !ascending )
        {
            asc = -1;
        }
        return asc;
    }
}
