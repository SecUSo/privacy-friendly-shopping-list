package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.deletelists;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.comparators.PFAComparators;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.MessageUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.ShoppingListService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.deletelists.listeners.DeleteListsOnClickListener;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.settings.SettingsKeys;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 09.07.16 creation date
 */
public class DeleteListsActivity extends AppCompatActivity
{
    private ShoppingListService shoppingListService;
    private DeleteListsCache cache;

    @Override
    protected final void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_lists_activity);

        AbstractInstanceFactory instanceFactory = new InstanceFactory(getApplicationContext());
        this.shoppingListService = (ShoppingListService) instanceFactory.createInstance(ShoppingListService.class);
        cache = new DeleteListsCache(this);

        updateListView();

        cache.getDeleteFab().setOnClickListener(new DeleteListsOnClickListener(cache));

        overridePendingTransition(0, 0);
    }

    public void updateListView()
    {
        List<ListDto> allListDtos = new ArrayList<>();

        shoppingListService.getAllListDtos()
                .doOnNext(dto -> allListDtos.add(dto))
                .doOnCompleted(() ->
                {
                    // sort according to last sort selection
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
                    String sortBy = sharedPref.getString(SettingsKeys.LIST_SORT_BY, PFAComparators.SORT_BY_NAME);
                    boolean sortAscending = sharedPref.getBoolean(SettingsKeys.LIST_SORT_ASCENDING, true);
                    shoppingListService.sortList(allListDtos, sortBy, sortAscending);

                    cache.getDeleteListsAdapter().setShoppingList(allListDtos);
                    cache.getDeleteListsAdapter().notifyDataSetChanged();
                })
                .subscribe();
    }
}
