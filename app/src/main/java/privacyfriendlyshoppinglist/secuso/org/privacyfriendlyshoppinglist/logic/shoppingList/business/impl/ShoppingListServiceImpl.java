package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.impl;

import android.content.Context;
import org.joda.time.DateTime;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.comparators.PFAComparators;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.DB;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.DateUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.StringUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.ShoppingListService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListItem;
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
    public Observable<Void> saveOrUpdate(ListItem item)
    {
        Observable<Void> observable = Observable
                .fromCallable(() -> saveOrUpdateSync(item))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    @Override
    public Void saveOrUpdateSync(ListItem item)
    {
        if ( StringUtils.isEmpty(item.getListName()) )
        {
            item.setListName(context.getResources().getString(R.string.default_list_name));
        }
        ShoppingListEntity entity = new ShoppingListEntity();
        shoppingListConverter.convertItemToEntity(item, entity);
        Long id = shoppingListDao.save(entity);
        item.setId(id.toString());
        return null;
    }

    @Override
    public Observable<ListItem> getById(String id)
    {
        Observable<ListItem> observable = Observable
                .fromCallable(() -> getByIdSync(id))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private ListItem getByIdSync(String id)
    {
        ListItem item = new ListItem();
        ShoppingListEntity entity = shoppingListDao.getById(Long.valueOf(id));
        shoppingListConverter.convertEntityToItem(entity, item);
        return item;
    }

    @Override
    public DateTime getReminderDate(ListItem item)
    {
        DateTime inputTime = getDeadLine(item);

        String reminderCount = StringUtils.isEmpty(item.getReminderCount()) ? "0" : item.getReminderCount();
        int inputAmount = Integer.parseInt(reminderCount);
        int inputChoice = Integer.parseInt(item.getReminderUnit());
        DateTime reminderTime = calculateReminderTime(inputTime, inputAmount, inputChoice);
        return reminderTime;
    }

    @Override
    public DateTime getDeadLine(ListItem item)
    {
        String language = context.getResources().getString(R.string.language);
        String dateLongPattern = context.getResources().getString(R.string.date_long_pattern);
        return DateUtils.getDateFromString(item.getDeadlineDate() + " " + item.getDeadlineTime(), dateLongPattern, language);
    }

    @Override
    public int getReminderStatusResource(ListItem item, List<ProductItem> productItems)
    {
        int reminderStatus = R.drawable.reminder_status_neutral;
        DateTime nowDate = new DateTime();
        if ( productItems.isEmpty() )
        {
            reminderStatus = R.drawable.reminder_status_done;
        }
        else if ( item.getReminderCount() != null )
        {
            DateTime deadLine = getDeadLine(item);
            DateTime reminderDate = getReminderDate(item);
            if ( nowDate.isBefore(deadLine) && !nowDate.isBefore(reminderDate) )
            {
                reminderStatus = R.drawable.reminder_status_triggered;
            }
            else if ( deadLine.isBefore(nowDate) )
            {
                reminderStatus = R.drawable.reminder_status_time_over;
            }
        }
        else if ( !StringUtils.isEmpty(item.getDeadlineDate()) )
        {
            DateTime deadLine = getDeadLine(item);
            if ( deadLine.isBefore(nowDate) )
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
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private Void deleteByIdSync(String id)
    {
        shoppingListDao.deleteById(Long.valueOf(id));
        return null;
    }

    @Override
    public Observable<ListItem> getAllListItems()
    {
        Observable<ListItem> observable = Observable
                .defer(() -> Observable.from(getAllListItemsSync()))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private List<ListItem> getAllListItemsSync()
    {
        List<ListItem> listItem = new ArrayList<>();
        Observable
                .from(shoppingListDao.getAllEntities())
                .map(this::getItem)
                .subscribe(item -> listItem.add(item));
        return listItem;
    }

    @Override
    public Observable<String> deleteSelected(List<ListItem> shoppingListItems)
    {
        Observable<String> observable = Observable
                .defer(() -> Observable.from(deleteSelectedSync(shoppingListItems)))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private List<String> deleteSelectedSync(List<ListItem> shoppingListItems)
    {
        List<String> deletedIds = new ArrayList<>();
        Observable
                .from(shoppingListItems)
                .filter(item -> item.isSelected())
                .subscribe(
                        item ->
                        {
                            String id = item.getId();
                            deleteByIdSync(id);
                            deletedIds.add(id);
                        }
                );
        return deletedIds;
    }

    @Override
    public List<ListItem> moveSelectedToEnd(List<ListItem> shoppingListItems)
    {
        List<ListItem> nonSelectedItems = new ArrayList<>();
        Observable
                .from(shoppingListItems)
                .filter(item -> !item.isSelected())
                .subscribe(item -> nonSelectedItems.add(item));

        List<ListItem> selectedItems = new ArrayList<>();
        Observable
                .from(shoppingListItems)
                .filter(item -> item.isSelected())
                .subscribe(item -> selectedItems.add(item));

        nonSelectedItems.addAll(selectedItems);
        shoppingListItems = nonSelectedItems;
        return shoppingListItems;
    }

    @Override
    public void sortList(List<ListItem> lists, String criteria, boolean ascending)
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
    public String getShareableText(ListItem listItem, List<ProductItem> productItems)
    {
        StringBuilder sb = new StringBuilder();

        sb.append(listItem.getListName());
        sb.append(StringUtils.NEW_LINE);

        if ( productItems != null && !productItems.isEmpty() )
        {
            Observable.from(productItems)
                    .filter(item -> !item.isChecked())
                    .subscribe(item ->
                    {
                        sb
                                .append(StringUtils.DASH)
                                .append(StringUtils.LEFT_BRACE)
                                .append(item.getQuantity())
                                .append(StringUtils.RIGHT_BRACE)
                                .append(item.getProductName())
                                .append(StringUtils.NEW_LINE);
                    });
        }
        else
        {
            sb.append(context.getResources().getString(R.string.no_products));
        }

        if ( !StringUtils.isEmpty(listItem.getNotes()) )
        {
            sb.append(StringUtils.NEW_LINE);
            sb.append(listItem.getNotes());
        }

        return sb.toString();
    }

    private ListItem getItem(ShoppingListEntity entity)
    {
        ListItem item = new ListItem();
        shoppingListConverter.convertEntityToItem(entity, item);
        return item;
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
