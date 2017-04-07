package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.impl.converter.impl;

import android.content.Context;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.comparators.PFAComparators;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.DB;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.DateUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.StringUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.impl.converter.ShoppingListConverter;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.entity.ShoppingListEntity;

import java.util.Date;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 11.06.16 creation date
 */
public class ShoppingListConverterImpl implements ShoppingListConverter
{
    private static final String SPACE = " ";

    private String language;
    private String dateLongPattern;
    private String datePattern;
    private String timePattern;
    private Context context;

    @Override
    public void setContext(Context context, DB db)
    {
        this.context = context;
        this.language = context.getResources().getString(R.string.language);
        this.dateLongPattern = context.getResources().getString(R.string.date_long_pattern);
        this.datePattern = context.getResources().getString(R.string.date_short_pattern);
        this.timePattern = context.getResources().getString(R.string.time_pattern);
    }

    @Override
    public void convertItemToEntity(ListItem item, ShoppingListEntity entity)
    {
        Long id = getIdAsLong(item);
        entity.setId(id);
        entity.setListName(item.getListName());
        entity.setSortAscending(item.isSortAscending());
        entity.setSortCriteria(item.getSortCriteria());
        entity.setStatisticsEnabled(item.isStatisticEnabled());

        String fullDate = item.getDeadlineDate() + SPACE + item.getDeadlineTime();
        if ( !SPACE.equals(fullDate) )
        {
            Date deadline = DateUtils.getDateFromString(fullDate, dateLongPattern, language).toDate();
            entity.setDeadline(deadline);
            setReminder(item, entity);
        }
        else
        {
            entity.setDeadline(null);
            entity.setReminderCount(null);
            entity.setReminderUnit(null);
        }
        entity.setIcon(item.getIcon());
        entity.setNotes(item.getNotes());
        entity.setPriority(item.getPriority());
    }

    private void setReminder(ListItem item, ShoppingListEntity entity)
    {
        if ( item.getReminderCount() != null )
        {
            if ( item.isReminderEnabled() )
            {
                if ( !StringUtils.isEmpty(item.getReminderCount()) )
                {
                    entity.setReminderCount(Integer.valueOf(item.getReminderCount()));
                }
                else
                {
                    entity.setReminderCount(0);
                }
            }
            else
            {
                entity.setReminderCount(null);
            }
            entity.setReminderUnit(Integer.valueOf(item.getReminderUnit()));
        }
    }

    @Override
    public void convertEntityToItem(ShoppingListEntity entity, ListItem item)
    {
        item.setId(entity.getId().toString());
        item.setListName(entity.getListName());
        item.setStatisticEnabled(entity.getStatisticsEnabled());

        if ( entity.getSortCriteria() != null )
        {
            item.setSortCriteria(entity.getSortCriteria());
            item.setSortAscending(entity.getSortAscending());
        }
        else // default sort config
        {
            item.setSortCriteria(PFAComparators.SORT_BY_NAME);
            item.setSortAscending(true);
        }

        if ( entity.getDeadline() != null )
        {
            String deadlineDateAsString = DateUtils.getDateAsString(entity.getDeadline().getTime(), datePattern, language);
            String deadlineTimeAsString = DateUtils.getDateAsString(entity.getDeadline().getTime(), timePattern, language);
            item.setDeadlineDate(deadlineDateAsString);
            item.setDeadlineTime(deadlineTimeAsString);
        }
        else
        {
            item.setDeadlineDate(StringUtils.EMPTY);
            item.setDeadlineTime(StringUtils.EMPTY);
        }

        if ( entity.getReminderCount() != null )
        {
            item.setReminderCount(String.valueOf(entity.getReminderCount()));
            item.setReminderUnit(String.valueOf(entity.getReminderUnit()));
        }

        item.setIcon(entity.getIcon());
        item.setNotes(entity.getNotes());
        item.setPriority(entity.getPriority());
    }

    private Long getIdAsLong(ListItem item)
    {
        String stringId = item.getId();
        return stringId == null ? null : Long.valueOf(stringId);
    }
}
