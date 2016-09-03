package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.comparators.PFAComparators;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.MessageUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.ShoppingListService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.baseactivity.BaseActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.listeners.AddOnClickListener;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.listeners.ShowDeleteListsOnClickListener;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.listeners.SortOnClickListener;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.settings.SettingsKeys;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 17.05.16 creation date
 */
public class MainActivity extends BaseActivity
{
    public static final String LIST_ID_KEY = "list.id";
    private ShoppingListService shoppingListService;
    private ShoppingListActivityCache cache;


    @Override
    protected final void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AbstractInstanceFactory instanceFactory = new InstanceFactory(getApplicationContext());
        this.shoppingListService = (ShoppingListService) instanceFactory.createInstance(ShoppingListService.class);
        cache = new ShoppingListActivityCache(this);

//        getApplicationContext().deleteDatabase(DB.APP.getDbName());

        updateListView();
        MessageUtils.showWelcomeDialog(SettingsKeys.WELCOME_PREF, this);
        MessageUtils.showTutorialDialog(this);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String sortBy = sharedPref.getString(SettingsKeys.LIST_SORT_BY, null);
        if ( sortBy == null )
        {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(SettingsKeys.LIST_SORT_BY, PFAComparators.SORT_BY_NAME);
            editor.putBoolean(SettingsKeys.LIST_SORT_ASCENDING, true);
            editor.commit();
        }

        cache.getNewListFab().setOnClickListener(new AddOnClickListener(cache));

        overridePendingTransition(0, 0);
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        updateListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.lists_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        MenuItem sortItem = menu.findItem(R.id.imageview_sort);
        sortItem.setOnMenuItemClickListener(new SortOnClickListener(cache));

        MenuItem deleteItem = menu.findItem(R.id.imageview_delete);
        deleteItem.setOnMenuItemClickListener(new ShowDeleteListsOnClickListener(cache));
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected int getNavigationDrawerID()
    {
        return R.id.nav_main;
    }

    public void updateListView()
    {
        List<ListDto> allListDtos = new ArrayList<>();

        shoppingListService.getAllListDtos()
                .doOnNext(dto -> allListDtos.add(dto))
                .doOnCompleted(() ->
                {
                    if ( allListDtos.isEmpty() )
                    {
                        cache.getNoListsLayout().setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        cache.getNoListsLayout().setVisibility(View.GONE);
                    }

                    // sort according to last sort selection
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
                    String sortBy = sharedPref.getString(SettingsKeys.LIST_SORT_BY, PFAComparators.SORT_BY_NAME);
                    boolean sortAscending = sharedPref.getBoolean(SettingsKeys.LIST_SORT_ASCENDING, true);
                    shoppingListService.sortList(allListDtos, sortBy, sortAscending);

                    cache.getListAdapter().setShoppingList(allListDtos);
                    cache.getListAdapter().notifyDataSetChanged();
                })
                .subscribe();
    }

    public void reorderListView(List<ListDto> sortedList)
    {
        cache.getListAdapter().setShoppingList(sortedList);
        cache.getListAdapter().notifyDataSetChanged();
    }
}
