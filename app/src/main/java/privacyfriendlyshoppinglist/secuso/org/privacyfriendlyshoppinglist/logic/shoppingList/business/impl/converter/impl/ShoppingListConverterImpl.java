package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.impl.converter.impl;

import android.content.Context;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.comparators.PFAComparators;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.DB;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.DateUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.StringUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListDto;
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
    private static final String EMPTY = "";
    private static final String DETAIL_SEPARATOR = ": ";
    private static final String NEW_LINE = "\n";

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
    public void convertDtoToEntity(ListDto dto, ShoppingListEntity entity)
    {
        Long id = getIdAsLong(dto);
        entity.setId(id);
        entity.setListName(dto.getListName());
        entity.setSortAscending(dto.isSortAscending());
        entity.setSortCriteria(dto.getSortCriteria());

        String fullDate = dto.getDeadlineDate() + SPACE + dto.getDeadlineTime();
        if ( !SPACE.equals(fullDate) )
        {
            Date deadline = DateUtils.getDateFromString(fullDate, dateLongPattern, language).toDate();
            entity.setDeadline(deadline);
        }
        else
        {
            entity.setDeadline(null);
        }
        entity.setIcon(dto.getIcon());
        entity.setNotes(dto.getNotes());
        entity.setPriority(dto.getPriority());
    }

    @Override
    public void convertEntityToDto(ShoppingListEntity entity, ListDto dto)
    {
        dto.setId(entity.getId().toString());
        dto.setListName(entity.getListName());

        if ( entity.getSortCriteria() != null )
        {
            dto.setSortCriteria(entity.getSortCriteria());
            dto.setSortAscending(entity.getSortAscending());
        }
        else // default sort config
        {
            dto.setSortCriteria(PFAComparators.SORT_BY_NAME);
            dto.setSortAscending(true);
        }

        if ( entity.getDeadline() != null )
        {
            String deadlineDateAsString = DateUtils.getDateAsString(entity.getDeadline().getTime(), datePattern, language);
            String deadlineTimeAsString = DateUtils.getDateAsString(entity.getDeadline().getTime(), timePattern, language);
            dto.setDeadlineDate(deadlineDateAsString);
            dto.setDeadlineTime(deadlineTimeAsString);
        }
        else
        {
            dto.setDeadlineDate(EMPTY);
            dto.setDeadlineTime(EMPTY);
        }

        dto.setIcon(entity.getIcon());
        dto.setNotes(entity.getNotes());
        dto.setPriority(entity.getPriority());

        setInfo(dto);
    }

    private Long getIdAsLong(ListDto dto)
    {
        String stringId = dto.getId();
        return stringId == null ? null : Long.valueOf(stringId);
    }

    public void setInfo(ListDto dto)
    {
        String priorityLabel = context.getResources().getString(R.string.priority);
        String deadLineLabel = context.getResources().getString(R.string.deadline);
        String reminderLabel = context.getResources().getString(R.string.reminder);
        String notesLabel = context.getResources().getString(R.string.list_notes);

        StringBuilder sb = new StringBuilder();
        String priorityIndex = dto.getPriority();
        if ( !StringUtils.isEmpty(priorityIndex) )
        {
            String[] prioritiesArray = context.getResources().getStringArray(R.array.shopping_list_priority_spinner);
            String priority = prioritiesArray[ Integer.valueOf(priorityIndex) ];
            sb.append(priorityLabel);
            sb.append(DETAIL_SEPARATOR);
            sb.append(priority);
            sb.append(NEW_LINE);
        }
        if ( !StringUtils.isEmpty(dto.getDeadlineDate()) )
        {
            sb.append(deadLineLabel);
            sb.append(DETAIL_SEPARATOR);
            sb.append(dto.getDeadlineDate());
            sb.append(SPACE);
            sb.append(dto.getDeadlineTime());
            sb.append(NEW_LINE);
        }
        if ( !StringUtils.isEmpty(dto.getReminderDate()) )
        {
            sb.append(reminderLabel);
            sb.append(DETAIL_SEPARATOR);
            sb.append(dto.getReminderDate());
            sb.append(SPACE);
            sb.append(dto.getReminderTime());
            sb.append(NEW_LINE);
        }
        if ( !StringUtils.isEmpty(dto.getNotes()) )
        {
            sb.append(notesLabel);
            sb.append(DETAIL_SEPARATOR);
            sb.append(dto.getNotes());
            sb.append(NEW_LINE);
        }

        dto.setInfo(sb.toString());
    }
}
