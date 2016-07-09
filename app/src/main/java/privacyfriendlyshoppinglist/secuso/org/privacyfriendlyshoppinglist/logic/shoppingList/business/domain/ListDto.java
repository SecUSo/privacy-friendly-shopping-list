package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain;

import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.business.AbstractDto;

import java.util.Date;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 11.06.16 creation date
 */
public class ListDto extends AbstractDto
{

    public enum ErrorFieldName
    {
        LIST_NAME
    }

    private String listName;
    private String priority;
    private int icon;
    private Date deadline;
    private String notes;
    private String nrItems;

    public String getListName()
    {
        return listName;
    }

    public void setListName(String listName)
    {
        this.listName = listName;
    }

    public String getPriority()
    {
        return priority;
    }

    public void setPriority(String priority)
    {
        this.priority = priority;
    }

    public int getIcon()
    {
        return icon;
    }

    public void setIcon(int icon)
    {
        this.icon = icon;
    }

    public Date getDeadline()
    {
        return deadline;
    }

    public void setDeadline(Date deadline)
    {
        this.deadline = deadline;
    }

    public String getNotes()
    {
        return notes;
    }

    public void setNotes(String notes)
    {
        this.notes = notes;
    }

    public String getNrItems()
    {
        return nrItems;
    }

    public void setNrItems(String nrItems)
    {
        this.nrItems = nrItems;
    }

    @Override
    public String toString()
    {
        return "ListDto{" +
                "listName='" + listName + '\'' +
                ", priority='" + priority + '\'' +
                ", icon=" + icon +
                ", deadline=" + deadline +
                ", notes='" + notes + '\'' +
                ", nrItems='" + nrItems + '\'' +
                '}';
    }
}
