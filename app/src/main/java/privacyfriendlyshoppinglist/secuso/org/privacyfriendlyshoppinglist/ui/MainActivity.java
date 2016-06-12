package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppingList.ShoppingListAdapter;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.sideMenu.DrawerItemClickListener;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.sideMenu.DrawerListAdapter;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.sideMenu.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 17.05.16 creation date
 */
public class MainActivity extends AppCompatActivity
{
    private String[] listItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private static final String HELP = "Help";

    private ArrayAdapter<String> mAdapter;

    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    private ActionBarDrawerToggle getmDrawerToggle;

    ArrayList<MenuItem> mMenuItems = new ArrayList<MenuItem>();
    CoordinatorLayout mDrawerPane;


    @Override
    protected final void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();

        mMenuItems.add(new MenuItem("Home", "Home Screen", android.R.drawable.ic_menu_edit));
        mMenuItems.add(new MenuItem("About", "Version 1.0", android.R.drawable.ic_menu_edit));
        mMenuItems.add(new MenuItem("Help", "Get Help", android.R.drawable.ic_menu_edit));

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerPane = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mMenuItems);
        mDrawerList.setAdapter(adapter);

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener(this, mDrawerLayout, mDrawerList));


       /* // TODO Put Help and About in the String.xml
        String[] listItemTitles = {HELP, "About"};
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.drawer_list_item, R.id.menu_item, listItemTitles);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener(this, mDrawerLayout, mDrawerList));
*/
       /* mDraw erToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout
                android.R.layout.ic_menu_sort_by_size);


        )*/

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        setupRecyclerView(recyclerView);
    }

    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle(getString(R.string.app_name));
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ShoppingListAdapter shoppingListAdapter = new ShoppingListAdapter(createItemList());
        recyclerView.setAdapter(shoppingListAdapter);
    }

    private List<String> createItemList() {
        List<String> itemList = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                itemList.add("Item " + i);
            }
        return itemList;
    }

    @Override
    protected final void onStart()
    {
        super.onStart();
    }

    @Override
    protected final void onStop()
    {
        super.onStop();
    }
}
