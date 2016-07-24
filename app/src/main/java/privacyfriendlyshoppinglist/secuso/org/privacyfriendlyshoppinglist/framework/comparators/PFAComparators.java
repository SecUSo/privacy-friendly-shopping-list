package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.comparators;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 22.07.16 creation date
 */
public abstract class PFAComparators
{
    public static final String SORT_BY_NAME = "sort.by.name";
    public static final String SORT_BY_PRIORITY = "sort.by.priority";
    public static final String SORT_BY_PRICE = "sort.by.price";
    public static final String SORT_BY_QUANTITY = "sort.by.quantity";
    public static final String SORT_BY_STORE = "sort.by.store";
    public static final String SORT_BY_CATEGORY = "sort.by.category";

    protected static int getAscendingFactor(boolean ascending)
    {
        int asc = 1;
        if ( !ascending )
        {
            asc = -1;
        }
        return asc;
    }
}
