package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain;

import android.content.Context;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.business.AbstractItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.StringUtils;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 11.06.16 creation date
 */
public class ListItem extends AbstractItem
{
    private String listName;
    private String priority;
    private int icon;
    private String deadlineDate;
    private String deadlineTime;
    private String notes;
    private boolean selected;
    private boolean sortAscending;
    private String sortCriteria;
    private boolean reminderEnabled;
    private boolean statisticEnabled;
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

    public boolean isStatisticEnabled()
    {
        return statisticEnabled;
    }

    public void setStatisticEnabled(boolean statisticEnabled)
    {
        this.statisticEnabled = statisticEnabled;
    }

    public String getDetailInfo(Context context)
    {
        String[] statisticsInfoArray = context.getResources().getStringArray(R.array.statistics_enabled_text);
        String priorityLabel = context.getResources().getString(R.string.priority);
        String deadLineLabel = context.getResources().getString(R.string.deadline);
        String notesLabel = context.getResources().getString(R.string.list_notes);

        StringBuilder sb = new StringBuilder();

        int statisticsInfoIndex = this.statisticEnabled ? 0 : 1;
        sb.append(statisticsInfoArray[ statisticsInfoIndex ]);
        sb.append(StringUtils.NEW_LINE);

        String priorityIndex = this.getPriority();
        if ( !StringUtils.isEmpty(priorityIndex) )
        {
            sb.append(StringUtils.NEW_LINE);
            String[] prioritiesArray = context.getResources().getStringArray(R.array.shopping_list_priority_spinner);
            String priority = prioritiesArray[ Integer.valueOf(priorityIndex) ];
            sb.append(priorityLabel);
            sb.append(StringUtils.DETAIL_SEPARATOR);
            sb.append(priority);
        }
        if ( !StringUtils.isEmpty(this.getDeadlineDate()) )
        {
            sb.append(StringUtils.NEW_LINE);
            sb.append(deadLineLabel);
            sb.append(StringUtils.DETAIL_SEPARATOR);
            sb.append(this.getDeadlineDate());
            sb.append(StringUtils.SPACE);
            sb.append(this.getDeadlineTime());
        }
        if ( !StringUtils.isEmpty(this.getReminderCount()) )
        {
            sb.append(StringUtils.NEW_LINE);
            String[] timeUnitArray = context.getResources().getStringArray(R.array.shopping_list_reminder_spinner);
            String reminderUnit = timeUnitArray[ Integer.valueOf(this.getReminderUnit()) ];
            String reminderText = context.getResources().getString(R.string.reminder_text, Integer.valueOf(this.getReminderCount()), reminderUnit);
            sb.append(reminderText);
        }
        if ( !StringUtils.isEmpty(this.getNotes()) )
        {
            sb.append(StringUtils.NEW_LINE);
            sb.append(notesLabel);
            sb.append(StringUtils.DETAIL_SEPARATOR);
            sb.append(this.getNotes());
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o)
    {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        ListItem item = (ListItem) o;

        if ( getIcon() != item.getIcon() ) return false;
        if ( isSelected() != item.isSelected() ) return false;
        if ( isSortAscending() != item.isSortAscending() ) return false;
        if ( isReminderEnabled() != item.isReminderEnabled() ) return false;
        if ( getListName() != null ? !getListName().equals(item.getListName()) : item.getListName() != null )
            return false;
        if ( getPriority() != null ? !getPriority().equals(item.getPriority()) : item.getPriority() != null )
            return false;
        if ( getDeadlineDate() != null ? !getDeadlineDate().equals(item.getDeadlineDate()) : item.getDeadlineDate() != null )
            return false;
        if ( getDeadlineTime() != null ? !getDeadlineTime().equals(item.getDeadlineTime()) : item.getDeadlineTime() != null )
            return false;
        if ( getNotes() != null ? !getNotes().equals(item.getNotes()) : item.getNotes() != null ) return false;
        if ( getSortCriteria() != null ? !getSortCriteria().equals(item.getSortCriteria()) : item.getSortCriteria() != null )
            return false;
        if ( getReminderCount() != null ? !getReminderCount().equals(item.getReminderCount()) : item.getReminderCount() != null )
            return false;
        return getReminderUnit() != null ? getReminderUnit().equals(item.getReminderUnit()) : item.getReminderUnit() == null;

    }

    @Override
    public int hashCode()
    {
        int result = getListName() != null ? getListName().hashCode() : 0;
        result = 31 * result + (getPriority() != null ? getPriority().hashCode() : 0);
        result = 31 * result + getIcon();
        result = 31 * result + (getDeadlineDate() != null ? getDeadlineDate().hashCode() : 0);
        result = 31 * result + (getDeadlineTime() != null ? getDeadlineTime().hashCode() : 0);
        result = 31 * result + (getNotes() != null ? getNotes().hashCode() : 0);
        result = 31 * result + (isSelected() ? 1 : 0);
        result = 31 * result + (isSortAscending() ? 1 : 0);
        result = 31 * result + (getSortCriteria() != null ? getSortCriteria().hashCode() : 0);
        result = 31 * result + (isReminderEnabled() ? 1 : 0);
        result = 31 * result + (getReminderCount() != null ? getReminderCount().hashCode() : 0);
        result = 31 * result + (getReminderUnit() != null ? getReminderUnit().hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return "ListItem{" +
                "listName='" + listName + '\'' +
                ", priority='" + priority + '\'' +
                ", icon=" + icon +
                ", deadlineDate='" + deadlineDate + '\'' +
                ", deadlineTime='" + deadlineTime + '\'' +
                ", notes='" + notes + '\'' +
                ", selected=" + selected +
                ", sortAscending=" + sortAscending +
                ", sortCriteria='" + sortCriteria + '\'' +
                ", reminderEnabled=" + reminderEnabled +
                ", statisticEnabled=" + statisticEnabled +
                ", reminderCount='" + reminderCount + '\'' +
                ", reminderUnit='" + reminderUnit + '\'' +
                '}';
    }

}
