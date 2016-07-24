package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.deletelists.listeners;

import android.support.design.widget.Snackbar;
import android.view.View;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.ProductService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.ShoppingListService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.deletelists.DeleteListsCache;
import rx.Observable;

import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.07.16 creation date
 */
public class DeleteOnClickListener implements View.OnClickListener
{
    private ShoppingListService shoppingListService;
    private ProductService productService;
    private DeleteListsCache cache;

    public DeleteOnClickListener(DeleteListsCache cache)
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
                        List<ListDto> shoppingList = cache.getDeleteListsAdapter().getShoppingList();
                        List<String> deletedIds = shoppingListService.deleteSelected(shoppingList);
                        Observable.from(deletedIds).subscribe(id -> productService.deleteAllFromList(id));
                        updateListView();
                    }
                }).show();
    }

    public void updateListView()
    {
        cache.getDeleteListsAdapter().setShoppingList(shoppingListService.getAllListDtos());
        cache.getDeleteListsAdapter().notifyDataSetChanged();
    }
}
