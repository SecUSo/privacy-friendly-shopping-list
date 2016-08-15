package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.impl;

import android.content.Context;
import org.joda.time.DateTime;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.comparators.PFAComparators;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.DB;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.DateUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.StringUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.ShoppingListService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.impl.comparators.ListsComparators;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.impl.converter.ShoppingListConverter;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.ShoppingListDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.entity.ShoppingListEntity;
import rx.Observable;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 11.06.16 creation date
 */
public class ShoppingListServiceImpl implements ShoppingListService
{

    private ShoppingListDao shoppingListDao;
    private ShoppingListConverter shoppingListConverter;
    private Context context;

    @Inject
    public ShoppingListServiceImpl(
            ShoppingListDao shoppingListDao,
            ShoppingListConverter shoppingListConverter
    )
    {
        this.shoppingListDao = shoppingListDao;
        this.shoppingListConverter = shoppingListConverter;
    }

    @Override
    public void setContext(Context context, DB db)
    {
        shoppingListDao.setContext(context, db);
        shoppingListConverter.setContext(context, db);
        this.context = context;
    }

    @Override
    public void saveOrUpdate(ListDto dto)
    {
        if ( StringUtils.isEmpty(dto.getListName()) )
        {
            dto.setListName(context.getResources().getString(R.string.default_list_name));
        }
        ShoppingListEntity entity = new ShoppingListEntity();
        shoppingListConverter.convertDtoToEntity(dto, entity);
        Long id = shoppingListDao.save(entity);
        dto.setId(id.toString());
    }

    @Override
    public ListDto getById(String id)
    {
        ListDto dto = new ListDto();
        ShoppingListEntity entity = shoppingListDao.getById(Long.valueOf(id));
        shoppingListConverter.convertEntityToDto(entity, dto);
        return dto;
    }

    @Override
    public DateTime getReminderDate(ListDto dto)
    {
        DateTime inputTime = getDeadLine(dto);

        String reminderCount = StringUtils.isEmpty(dto.getReminderCount()) ? "0" : dto.getReminderCount();
        int inputAmount = Integer.parseInt(reminderCount);
        int inputChoice = Integer.parseInt(dto.getReminderUnit());
        DateTime reminderTime = calculateReminderTime(inputTime, inputAmount, inputChoice);
        return reminderTime;
    }

    @Override
    public DateTime getDeadLine(ListDto dto)
    {
        String language = context.getResources().getString(R.string.language);
        String dateLongPattern = context.getResources().getString(R.string.date_long_pattern);
        return DateUtils.getDateFromString(dto.getDeadlineDate() + " " + dto.getDeadlineTime(), dateLongPattern, language);
    }

    @Override
    public int getReminderStatusResource(ListDto dto)
    {
        int reminderStatus = R.drawable.reminder_status_neutral;
        if ( dto.getReminderCount() != null )
        {
            DateTime deadLine = getDeadLine(dto);
            DateTime reminderDate = getReminderDate(dto);
            DateTime nowDate = new DateTime();
            if ( nowDate.isBefore(deadLine) && !nowDate.isBefore(reminderDate) )
            {
                reminderStatus = R.drawable.reminder_status_triggered;
            }
            else if ( deadLine.isBefore(nowDate) )
            {
                reminderStatus = R.drawable.reminder_status_time_over;
            }
        }
        return reminderStatus;
    }

    @Override
    public ShoppingListEntity getEntityById(String id)
    {
        return shoppingListDao.getById(Long.valueOf(id));
    }

    @Override
    public void deleteById(String id)
    {
        shoppingListDao.deleteById(Long.valueOf(id));
    }

    @Override
    public List<ListDto> getAllListDtos()
    {
        List<ShoppingListEntity> allEntities = shoppingListDao.getAllEntities();
        if ( allEntities != null )
        {
            Observable<ListDto> dtos = Observable
                    .from(allEntities)
                    .map(this::getDto);
            return dtos.toList().toBlocking().single();
        }
        return new ArrayList<>();
    }

    @Override
    public List<String> deleteSelected(List<ListDto> shoppingListDtos)
    {
        List<String> deletedIds = new ArrayList<>();
        Observable
                .from(shoppingListDtos)
                .filter(dto -> dto.isSelected())
                .subscribe(
                        dto ->
                        {
                            String id = dto.getId();
                            deleteById(id);
                            deletedIds.add(id);
                        }
                );
        return deletedIds;
    }

    @Override
    public List<ListDto> moveSelectedToEnd(List<ListDto> shoppingListDtos)
    {
        List<ListDto> nonSelectedDtos = Observable
                .from(shoppingListDtos)
                .filter(dto -> !dto.isSelected())
                .toList().toBlocking().single();

        List<ListDto> selectedDtos = Observable
                .from(shoppingListDtos)
                .filter(dto -> dto.isSelected())
                .toList().toBlocking().single();
        nonSelectedDtos.addAll(selectedDtos);
        shoppingListDtos = nonSelectedDtos;
        return shoppingListDtos;
    }

    @Override
    public void sortList(List<ListDto> lists, String criteria, boolean ascending)
    {
        if ( PFAComparators.SORT_BY_NAME.equals(criteria) )
        {
            Collections.sort(lists, ListsComparators.getNameComparator(ascending));
        }
        else if ( PFAComparators.SORT_BY_PRIORITY.equals(criteria) )
        {
            Collections.sort(lists, ListsComparators.getPriorityComparator(ascending));
        }

    }

    private ListDto getDto(ShoppingListEntity entity)
    {
        ListDto dto = new ListDto();
        shoppingListConverter.convertEntityToDto(entity, dto);
        return dto;
    }

    private DateTime calculateReminderTime(DateTime date, int inputAmount, int inputChoice)
    {
        DateTime dateTime = new DateTime();

        switch ( inputChoice )
        {
            case 0:
                dateTime = date.minusMinutes(inputAmount);
                break;
            case 1:
                dateTime = date.minusHours(inputAmount);
                break;
            case 2:
                dateTime = date.minusDays(inputAmount);
                break;
            case 3:
                dateTime = date.minusWeeks(inputAmount);
        }

        return dateTime;
    }
}
