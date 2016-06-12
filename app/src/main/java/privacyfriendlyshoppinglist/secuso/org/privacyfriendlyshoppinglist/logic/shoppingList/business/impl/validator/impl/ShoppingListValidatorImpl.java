package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.impl.validator.impl;

import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.logger.PFALogger;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.StringUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.impl.validator.ShoppingListValidator;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 12.06.16 creation date
 */
public class ShoppingListValidatorImpl implements ShoppingListValidator
{
    @Override
    public void validate(ListDto dto)
    {
        String listName = dto.getListName();
        if ( StringUtils.isEmpty(listName) )
        {
            dto.getValidationErrorsList().add(ListDto.ErrorFieldName.LIST_NAME.name());
            PFALogger.info(getClass().getSimpleName(), "validate", "validation error, DTO=" + dto.toString());
        }
    }
}
