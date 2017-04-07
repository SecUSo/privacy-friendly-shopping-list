package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.impl.comparators;

import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.comparators.PFAComparators;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListItem;

import java.util.Comparator;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.07.16 creation date
 */
public class ListsComparators extends PFAComparators
{
    public static Comparator<ListItem> getNameComparator(boolean ascending)
    {
        int ascendingFactor = getAscendingFactor(ascending);
        return (lhs, rhs) -> (lhs.getListName().compareTo(rhs.getListName()) * ascendingFactor);
    }

    public static Comparator<ListItem> getPriorityComparator(boolean ascending)
    {
        // priority: 1 is lower than 0. Therefore we invert the ascendingFactor
        int ascendingFactor = getAscendingFactor(ascending) * -1;
        return (lhs, rhs) -> (lhs.getPriority().compareTo(rhs.getPriority()) * ascendingFactor);
    }
}
