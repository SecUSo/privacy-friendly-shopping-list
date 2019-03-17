package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.deletelists.listeners;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.MessageUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.NotificationUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.ProductService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.ShoppingListService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.deletelists.DeleteListsCache;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.MainActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist.reminder.ReminderReceiver;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist.reminder.ReminderSchedulingService;
import rx.Observable;
import rx.schedulers.Schedulers;

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
        MessageUtils.showAlertDialog(cache.getActivity(), R.string.delete_confirmation_title, R.string.delete_lists_confirmation, deleteLists());
    }

    private Observable<Void> deleteLists()
    {
        Observable observable = Observable
                .defer(() -> Observable.just(deleteListsSync()))
                .doOnError(Throwable::printStackTrace)
                .subscribeOn(Schedulers.computation());
        return observable;
    }

    private Void deleteListsSync()
    {
        // delete lists
        List<ListItem> shoppingList = cache.getDeleteListsAdapter().getList();
        shoppingListService.deleteSelected(shoppingList)
                .doOnNext(id ->
                {
                    // delete all products
                    productService.deleteAllFromList(id)
                            .doOnError(Throwable::printStackTrace).subscribe();

                    // delete reminder
                    ReminderReceiver alarm = new ReminderReceiver();
                    Intent intent = new Intent(cache.getActivity(), ReminderSchedulingService.class);
                    alarm.cancelAlarm(cache.getActivity(), intent, id);
                    NotificationUtils.removeNotification(cache.getActivity(), id);
                })
                .doOnError(Throwable::printStackTrace)
                .subscribe();

        // go back to list overview
        AppCompatActivity activity = cache.getActivity();
        Intent intent = new Intent(activity, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        return null;
    }
}
