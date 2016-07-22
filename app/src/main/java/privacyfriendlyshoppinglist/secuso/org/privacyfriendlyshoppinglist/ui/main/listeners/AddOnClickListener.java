package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.listeners;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.ShoppingListService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.ShoppingListActivityCache;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist.EditDialogFragment;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.07.16 creation date
 */
public class AddOnClickListener implements View.OnClickListener
{
    private ShoppingListActivityCache cache;
    private ShoppingListService shoppingListService;

    public AddOnClickListener(ShoppingListActivityCache cache)
    {
        AppCompatActivity activity = cache.getActivity();
        AbstractInstanceFactory instanceFactory = new InstanceFactory(activity.getApplicationContext());
        this.shoppingListService = (ShoppingListService) instanceFactory.createInstance(ShoppingListService.class);
        this.cache = cache;

    }

    @Override
    public void onClick(View v)
    {
        ListDto dto = new ListDto();
        String priority = "1";
        dto.setPriority(priority);
        EditDialogFragment editDialogFragment = EditDialogFragment.newInstance(dto, cache);

        editDialogFragment.show(cache.getActivity().getSupportFragmentManager(), "DialogFragment");



    }
}
