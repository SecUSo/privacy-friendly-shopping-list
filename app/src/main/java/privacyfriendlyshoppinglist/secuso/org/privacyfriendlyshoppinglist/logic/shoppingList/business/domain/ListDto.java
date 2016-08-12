package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain;

import android.content.Context;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.business.AbstractDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.StringUtils;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 11.06.16 creation date
 */
public class ListDto extends AbstractDto
{
    private static final String DETAIL_SEPARATOR = ": ";
    private static final String NEW_LINE = "\n";
    private static final String SPACE = " ";
    
    private String listName;
    private String priority;
    private int icon;
    private String deadlineDate;
    private String deadlineTime;
    private String notes;
    private String nrItems;
    private boolean selected;
    private boolean sortAscending;
    private String sortCriteria;
    private boolean reminderEnabled;
    private String reminderCount;
    private String reminderUnit;

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

    public String getDeadlineDate()
    {
        return deadlineDate;
    }

    public void setDeadlineDate(String deadlineDate)
    {
        this.deadlineDate = deadlineDate;
    }

    public String getDeadlineTime()
    {
        return deadlineTime;
    }

    public void setDeadlineTime(String deadlineTime)
    {
        this.deadlineTime = deadlineTime;
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

    public boolean isSelected()
    {
        return selected;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    public boolean isSortAscending()
    {
        return sortAscending;
    }

    public void setSortAscending(boolean sortAscending)
    {
        this.sortAscending = sortAscending;
    }

    public String getSortCriteria()
    {
        return sortCriteria;
    }

    public void setSortCriteria(String sortCriteria)
    {
        this.sortCriteria = sortCriteria;
    }

    public String getReminderCount()
    {
        return reminderCount;
    }

    public void setReminderCount(String reminderCount)
    {
        this.reminderCount = reminderCount;
    }

    public String getReminderUnit()
    {
        return reminderUnit;
    }

    public void setReminderUnit(String reminderUnit)
    {
        this.reminderUnit = reminderUnit;
    }

    public boolean isReminderEnabled()
    {
        return reminderEnabled;
    }

    public void setReminderEnabled(boolean reminderEnabled)
    {
        this.reminderEnabled = reminderEnabled;
    }

    public String getDetailInfo(Context context)
    {
        String priorityLabel = context.getResources().getString(R.string.priority);
        String deadLineLabel = context.getResources().getString(R.string.deadline);
        String notesLabel = context.getResources().getString(R.string.list_notes);

        StringBuilder sb = new StringBuilder();
        String priorityIndex = this.getPriority();
        if ( !StringUtils.isEmpty(priorityIndex) )
        {
            String[] prioritiesArray = context.getResources().getStringArray(R.array.shopping_list_priority_spinner);
            String priority = prioritiesArray[ Integer.valueOf(priorityIndex) ];
            sb.append(priorityLabel);
            sb.append(DETAIL_SEPARATOR);
            sb.append(priority);
            sb.append(NEW_LINE);
        }
        if ( !StringUtils.isEmpty(this.getDeadlineDate()) )
        {
            sb.append(deadLineLabel);
            sb.append(DETAIL_SEPARATOR);
            sb.append(this.getDeadlineDate());
            sb.append(SPACE);
            sb.append(this.getDeadlineTime());
            sb.append(NEW_LINE);
        }
        if ( !StringUtils.isEmpty(this.getReminderCount()) )
        {
            String[] timeUnitArray = context.getResources().getStringArray(R.array.shopping_list_reminder_spinner);
            String reminderUnit = timeUnitArray[ Integer.valueOf(this.getReminderUnit()) ];
            String reminderText = context.getResources().getString(R.string.reminder_text, Integer.valueOf(this.getReminderCount()), reminderUnit);
            sb.append(reminderText);
            sb.append(NEW_LINE);
        }
        if ( !StringUtils.isEmpty(this.getNotes()) )
        {
            sb.append(notesLabel);
            sb.append(DETAIL_SEPARATOR);
            sb.append(this.getNotes());
            sb.append(NEW_LINE);
        }

        return sb.toString();
    }
    
    @Override
    public String toString()
    {
        return "ListDto{" +
                "listName='" + listName + '\'' +
                ", priority='" + priority + '\'' +
                ", icon=" + icon +
                ", deadlineDate='" + deadlineDate + '\'' +
                ", deadlineTime='" + deadlineTime + '\'' +
                ", notes='" + notes + '\'' +
                ", nrItems='" + nrItems + '\'' +
                ", selected=" + selected +
                ", sortAscending=" + sortAscending +
                ", sortCriteria='" + sortCriteria + '\'' +
                ", reminderEnabled=" + reminderEnabled +
                ", reminderCount='" + reminderCount + '\'' +
                ", reminderUnit='" + reminderUnit + '\'' +
                '}';
    }

}
