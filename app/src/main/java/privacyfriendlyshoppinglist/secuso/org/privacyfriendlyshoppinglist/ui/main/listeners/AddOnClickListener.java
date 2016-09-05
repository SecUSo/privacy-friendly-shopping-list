package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.listeners;

import android.view.View;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.ShoppingListActivityCache;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist.ListDialogFragment;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.07.16 creation date
 */
public class AddOnClickListener implements View.OnClickListener
{
    private ShoppingListActivityCache cache;

    public AddOnClickListener(ShoppingListActivityCache cache)
    {
        this.cache = cache;

    }

    @Override
    public void onClick(View v)
    {
        ListDto dto = new ListDto();
        String priority = "1";
        dto.setPriority(priority);

        if ( !ListDialogFragment.isOpened() )
        {
            ListDialogFragment listDialogFragment = ListDialogFragment.newAddInstance(dto, cache);
            listDialogFragment.show(cache.getActivity().getSupportFragmentManager(), "DialogFragment");
        }
    }
}
