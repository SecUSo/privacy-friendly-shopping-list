package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.ShoppingListService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.baseactivity.BaseActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.listeners.AddOnClickListener;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.listeners.ShowDeleteListsOnClickListener;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.listeners.SortOnClickListener;

import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 17.05.16 creation date
 */
public class MainActivity extends BaseActivity
{
    public static final String LIST_NAME_KEY = "list.name";
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

//        WelcomeDialog welcomeDialog = new WelcomeDialog();
//        welcomeDialog.show(getFragmentManager(), "WelcomeDialog");

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
        return R.id.nav_example;
    }

    public void updateListView()
    {
        cache.getListAdapter().setShoppingList(shoppingListService.getAllListDtos());
        cache.getListAdapter().notifyDataSetChanged();
    }

    public void reorderListView(List<ListDto> sortedList)
    {
        cache.getListAdapter().setShoppingList(sortedList);
        cache.getListAdapter().notifyDataSetChanged();
    }
}
