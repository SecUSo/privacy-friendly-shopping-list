package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.impl;

import android.content.Context;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.DB;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.ShoppingListService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.impl.converter.ShoppingListConverter;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.ShoppingListDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.entity.ShoppingListEntity;

import javax.inject.Inject;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 11.06.16 creation date
 */
public class ShoppingListServiceImpl implements ShoppingListService
{

    private ShoppingListDao shoppingListDao;
    private ShoppingListConverter shoppingListConverter;

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
    }

    @Override
    public void saveOrUpdate(ListDto dto)
    {
        ShoppingListEntity entity = new ShoppingListEntity();
        shoppingListConverter.copyDtoToEntity(dto, entity);
        Long id = shoppingListDao.save(entity);
        dto.setId(id);
    }
}
