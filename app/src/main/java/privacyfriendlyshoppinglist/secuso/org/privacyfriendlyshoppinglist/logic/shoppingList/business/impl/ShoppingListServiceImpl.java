package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.impl;

import android.content.Context;
import org.joda.time.DateTime;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.comparators.PFAComparators;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.DB;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.DateUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.StringUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.ShoppingListService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.impl.comparators.ListsComparators;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.impl.converter.ShoppingListConverter;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.ShoppingListDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.entity.ShoppingListEntity;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
    public Observable<Void> saveOrUpdate(ListDto dto)
    {
        Observable<Void> observable = Observable
                .fromCallable(() -> saveOrUpdateSync(dto))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private Void saveOrUpdateSync(ListDto dto)
    {
        if ( StringUtils.isEmpty(dto.getListName()) )
        {
            dto.setListName(context.getResources().getString(R.string.default_list_name));
        }
        ShoppingListEntity entity = new ShoppingListEntity();
        shoppingListConverter.convertDtoToEntity(dto, entity);
        Long id = shoppingListDao.save(entity);
        dto.setId(id.toString());
        return null;
    }

    @Override
    public Observable<ListDto> getById(String id)
    {
        Observable<ListDto> observable = Observable
                .fromCallable(() -> getByIdSync(id))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private ListDto getByIdSync(String id)
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
    public int getReminderStatusResource(ListDto dto, List<ProductDto> productDtos)
    {
        int reminderStatus = R.drawable.reminder_status_neutral;
        if ( productDtos.isEmpty() )
        {
            reminderStatus = R.drawable.reminder_status_done;
        }
        else if ( dto.getReminderCount() != null )
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
    public ShoppingListEntity getEntityByIdSync(String id)
    {
        return shoppingListDao.getById(Long.valueOf(id));
    }

    @Override
    public Observable<Void> deleteById(String id)
    {
        Observable<Void> observable = Observable
                .fromCallable(() -> deleteByIdSync(id))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private Void deleteByIdSync(String id)
    {
        shoppingListDao.deleteById(Long.valueOf(id));
        return null;
    }

    @Override
    public Observable<ListDto> getAllListDtos()
    {
        Observable<ListDto> observable = Observable
                .defer(() -> Observable.from(getAllListDtosSync()))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private List<ListDto> getAllListDtosSync()
    {
        List<ListDto> listDto = new ArrayList<>();
        Observable
                .from(shoppingListDao.getAllEntities())
                .map(this::getDto)
                .subscribe(dto -> listDto.add(dto));
        return listDto;
    }

    @Override
    public Observable<String> deleteSelected(List<ListDto> shoppingListDtos)
    {
        Observable<String> observable = Observable
                .defer(() -> Observable.from(deleteSelectedSync(shoppingListDtos)))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private List<String> deleteSelectedSync(List<ListDto> shoppingListDtos)
    {
        List<String> deletedIds = new ArrayList<>();
        Observable
                .from(shoppingListDtos)
                .filter(dto -> dto.isSelected())
                .subscribe(
                        dto ->
                        {
                            String id = dto.getId();
                            deleteByIdSync(id);
                            deletedIds.add(id);
                        }
                );
        return deletedIds;
    }

    @Override
    public List<ListDto> moveSelectedToEnd(List<ListDto> shoppingListDtos)
    {
        List<ListDto> nonSelectedDtos = new ArrayList<>();
        Observable
                .from(shoppingListDtos)
                .filter(dto -> !dto.isSelected())
                .subscribe(dto -> nonSelectedDtos.add(dto));

        List<ListDto> selectedDtos = new ArrayList<>();
        Observable
                .from(shoppingListDtos)
                .filter(dto -> dto.isSelected())
                .subscribe(dto -> selectedDtos.add(dto));

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

    @Override
    public String getShareableText(ListDto listDto, List<ProductDto> productDtos)
    {
        StringBuilder sb = new StringBuilder();

        sb.append(listDto.getListName());
        sb.append(StringUtils.NEW_LINE);

        if ( productDtos != null && !productDtos.isEmpty() )
        {
            Observable.from(productDtos)
                    .filter(dto -> !dto.isChecked())
                    .subscribe(dto ->
                    {
                        sb
                                .append(StringUtils.DASH)
                                .append(StringUtils.LEFT_BRACE)
                                .append(dto.getQuantity())
                                .append(StringUtils.RIGHT_BRACE)
                                .append(dto.getProductName())
                                .append(StringUtils.NEW_LINE);
                    });
        }
        else
        {
            sb.append(context.getResources().getString(R.string.no_products));
        }

        if ( !StringUtils.isEmpty(listDto.getNotes()) )
        {
            sb.append(StringUtils.NEW_LINE);
            sb.append(listDto.getNotes());
        }

        return sb.toString();
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
