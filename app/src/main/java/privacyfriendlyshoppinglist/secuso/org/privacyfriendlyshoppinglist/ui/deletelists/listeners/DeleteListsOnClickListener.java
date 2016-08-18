package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.deletelists.listeners;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.NotificationUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.ProductService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.ShoppingListService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.deletelists.DeleteListsCache;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.MainActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist.reminder.ReminderReceiver;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist.reminder.ReminderSchedulingService;
import rx.Observable;

import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.07.16 creation date
 */
public class DeleteListsOnClickListener implements View.OnClickListener
{
    private ShoppingListService shoppingListService;
    private ProductService productService;
    private DeleteListsCache cache;

    public DeleteListsOnClickListener(DeleteListsCache cache)
    {
        this.cache = cache;
        AbstractInstanceFactory instanceFactory = new InstanceFactory(cache.getActivity().getApplicationContext());
        this.shoppingListService = (ShoppingListService) instanceFactory.createInstance(ShoppingListService.class);
        this.productService = (ProductService) instanceFactory.createInstance(ProductService.class);
    }

    @Override
    public void onClick(View v)
    {
        Snackbar.make(v, R.string.delele_lists_confirmation, Snackbar.LENGTH_LONG)
                .setAction(R.string.okay, new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        // delete product
                        List<ListDto> shoppingList = cache.getDeleteListsAdapter().getShoppingList();
                        List<String> deletedIds = shoppingListService.deleteSelected(shoppingList);
                        Observable.from(deletedIds).subscribe(
                                id -> productService.deleteAllFromList(id),
                                t ->
                                {
                                },
                                () ->
                                {
                                    for ( String id : deletedIds )
                                    {
                                        ReminderReceiver alarm = new ReminderReceiver();
                                        Intent intent = new Intent(cache.getActivity(), ReminderSchedulingService.class);
                                        alarm.cancelAlarm(cache.getActivity(), intent, id);
                                        NotificationUtils.removeNotification(cache.getActivity(), id);
                                    }
                                }
                        );

                        // go back to list overview
                        AppCompatActivity activity = cache.getActivity();
                        Intent intent = new Intent(activity, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        activity.startActivity(intent);
                    }
                }).show();
    }
}
