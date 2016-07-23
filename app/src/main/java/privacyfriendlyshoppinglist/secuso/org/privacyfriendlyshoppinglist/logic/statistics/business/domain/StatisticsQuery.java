package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.domain;

import java.util.Observable;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 23.07.16 creation date
 */
public class StatisticsQuery extends Observable
{
    public static int MONTH = 0;
    public static int WEEK = 1;
    public static int DAY = 2;
    public static int CATEGORY = 3;
    public static int STORE = 4;
    public static int PRODUCT = 5;

    private String dateFrom;

    private String dateTo;

    private int groupBy;

    public String getDateFrom()
    {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom)
    {
        this.dateFrom = dateFrom;
        setChanged();
    }

    public String getDateTo()
    {
        return dateTo;
    }

    public void setDateTo(String dateTo)
    {
        this.dateTo = dateTo;
        setChanged();
    }

    public int getGroupBy()
    {
        return groupBy;
    }

    public void setGroupBy(int groupBy)
    {
        this.groupBy = groupBy;
        setChanged();
    }
}
