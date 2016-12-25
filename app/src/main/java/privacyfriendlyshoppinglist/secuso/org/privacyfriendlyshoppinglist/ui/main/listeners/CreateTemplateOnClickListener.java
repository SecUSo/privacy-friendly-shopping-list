package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.listeners;

import android.view.MenuItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.MessageUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.ProductService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.ShoppingListActivityCache;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist.ListDialogFragment;
import rx.Observable;

/**
 * Description: <br>
 * Author: Grebiel Jose Ifill Brito<br>
 * Created: 26.11.2016
 */
public class CreateTemplateOnClickListener implements MenuItem.OnMenuItemClickListener
{
    private ShoppingListActivityCache cache;
    private ProductService productService;

    public CreateTemplateOnClickListener(ShoppingListActivityCache cache)
    {
        this.cache = cache;
        AbstractInstanceFactory instanceFactory = new InstanceFactory(cache.getActivity().getApplicationContext());
        productService = (ProductService) instanceFactory.createInstance(ProductService.class);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem)
    {
        Observable<Object> showDialog = Observable.fromCallable(() ->
        {
            ListDialogFragment listDialogFragment = ListDialogFragment.newAddInstanceForTemplate(cache);
            listDialogFragment.show(cache.getActivity().getSupportFragmentManager(), "DialogFragment");
            return null;
        });

        MessageUtils.showInfoDialog(
                cache.getActivity(),
                R.string.template_titel,
                R.string.template_message,
                showDialog
        );
        return true;
    }
}
