package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.impl.converter.impl;

import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.impl.converter.ShoppingListConverter;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.entity.ShoppingListEntity;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 11.06.16 creation date
 */
public class ShoppingListConverterImpl implements ShoppingListConverter
{
    @Override
    public void copyDtoToEntity(ListDto dto, ShoppingListEntity entity)
    {
        entity.setId(dto.getId());
        entity.setListName(dto.getListName());
        entity.setDeadline(dto.getDeadline());
        entity.setIcon(dto.getIcon());
        entity.setNotes(dto.getNotes());
        entity.setPriority(dto.getPriority());
    }

    @Override
    public void copyEntityToDto(ShoppingListEntity entity, ListDto dto)
    {
        dto.setId(entity.getId());
        dto.setListName(entity.getListName());
        dto.setDeadline(entity.getDeadline());
        dto.setIcon(entity.getIcon());
        dto.setNotes(entity.getNotes());
        dto.setPriority(entity.getPriority());
    }
}
