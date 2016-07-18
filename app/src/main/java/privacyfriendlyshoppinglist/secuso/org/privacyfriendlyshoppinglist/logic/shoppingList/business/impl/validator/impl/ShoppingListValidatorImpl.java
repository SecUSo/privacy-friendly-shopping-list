package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.impl.validator.impl;

import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.logger.PFALogger;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.impl.validator.ShoppingListValidator;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 12.06.16 creation date
 */
public class ShoppingListValidatorImpl implements ShoppingListValidator
{

    private static final int MAXIMUM_LIST_LENGTH = 40;

    @Override
    public void validate(ListDto dto)
    {
        String listName = dto.getListName();
        if ( listName != null )
        {
            if ( listName.length() > MAXIMUM_LIST_LENGTH )
            {
                dto.getValidationErrorsList().add(ListDto.ErrorFieldName.LIST_NAME.name());
                PFALogger.debug(getClass().getSimpleName(), "validate", "validation error, DTO=" + dto.toString());
            }
        }
    }
}
