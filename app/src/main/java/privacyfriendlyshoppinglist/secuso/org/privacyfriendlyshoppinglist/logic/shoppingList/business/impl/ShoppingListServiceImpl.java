package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.impl;

import android.content.Context;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.DB;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.ShoppingListService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.impl.converter.ShoppingListConverter;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.impl.validator.ShoppingListValidator;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.ShoppingListDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.entity.ShoppingListEntity;
import rx.Observable;

import javax.inject.Inject;
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
    private ShoppingListValidator shoppingListValidator;

    @Inject
    public ShoppingListServiceImpl(
            ShoppingListDao shoppingListDao,
            ShoppingListConverter shoppingListConverter,
            ShoppingListValidator shoppingListValidator
    )
    {
        this.shoppingListDao = shoppingListDao;
        this.shoppingListConverter = shoppingListConverter;
        this.shoppingListValidator = shoppingListValidator;
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
        shoppingListConverter.convertDtoToEntity(dto, entity);
        shoppingListValidator.validate(dto);
        if (!dto.hasErrors())
        {
            Long id = shoppingListDao.save(entity);
            dto.setId(id.toString());
        }
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
    public void deleteById(String id)
    {
        shoppingListDao.deleteById(Long.valueOf(id));
    }

    @Override
    public List<ListDto> getAllListDtos()
    {
        Observable<ListDto> dtos = Observable
                .from(shoppingListDao.getAllEntities())
                .map(this::getDto);
        return dtos.toList().toBlocking().single();
    }

    @Override
    public void deleteSelected(List<ListDto> shoppingListDtos)
    {
        Observable
                .from(shoppingListDtos)
                .filter(dto -> dto.isSelected())
                .subscribe(
                        dto -> deleteById(dto.getId())
                );
    }

    private ListDto getDto(ShoppingListEntity entity)
    {
        ListDto dto = new ListDto();
        shoppingListConverter.convertEntityToDto(entity, dto);
        return dto;
    }
}
