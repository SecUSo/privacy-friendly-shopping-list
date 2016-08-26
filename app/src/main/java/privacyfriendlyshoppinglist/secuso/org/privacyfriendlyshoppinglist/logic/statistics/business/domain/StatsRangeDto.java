package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.domain;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 26.08.16 creation date
 */
public class StatsRangeDto
{
    private String maxDate;
    private String mindDate;

    public String getMaxDate()
    {
        return maxDate;
    }

    public void setMaxDate(String maxDate)
    {
        this.maxDate = maxDate;
    }

    public String getMindDate()
    {
        return mindDate;
    }

    public void setMindDate(String mindDate)
    {
        this.mindDate = mindDate;
    }
}
