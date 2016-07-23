package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.impl.converter.impl;

import android.content.Context;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.DB;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.DateUtils;
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

    private String language;
    private String dateLongPattern;
    private String datePattern;
    private String timePattern;


    @Override
    public void setContext(Context context, DB db)
    {
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
    }

    private Long getIdAsLong(ListDto dto)
    {
        String stringId = dto.getId();
        return stringId == null ? null : Long.valueOf(stringId);
    }

}
