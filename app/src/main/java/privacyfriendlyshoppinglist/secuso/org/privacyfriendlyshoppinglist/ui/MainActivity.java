package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppingList.ShoppingListFragement;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.sideMenu.DrawerItemClickListener;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.sideMenu.DrawerListAdapter;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.sideMenu.MenuItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.utils.MainActivityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 17.05.16 creation date
 */
public class MainActivity extends AppCompatActivity
{
    private DrawerLayout drawerLayout;
    private ListView drawerList;

    private CoordinatorLayout mDrawerPane;

    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;


    @Override
    protected final void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerPane = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        List<MenuItem> menuItems = createMenuItems();

        mTitle = getTitle();
        mDrawerTitle = "Navigation";

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getActionBar().setTitle(mDrawerTitle);
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        // Set the drawer toggle as the DrawerListener
        drawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();


        mDrawerPane = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        DrawerListAdapter adapter = new DrawerListAdapter(this, menuItems);

        drawerList = (ListView) findViewById(R.id.left_drawer);
        drawerList.setAdapter(adapter);

        drawerList.setOnItemClickListener(new DrawerItemClickListener(this, drawerLayout, drawerList));

        Fragment initialFragment = new ShoppingListFragement();
        MainActivityUtils.replaceFragmentPlaceholder(initialFragment, this);

    }

    private List<MenuItem> createMenuItems()
    {
        List<MenuItem> menuItems = new ArrayList<>();

        String homeTitle = getResources().getString(R.string.menu_home_title);
        String homeSubtitle = getResources().getString(R.string.menu_home_subtitle);
        String settingsTitle = getResources().getString(R.string.menu_settings_title);
        String settingsSubtitle = getResources().getString(R.string.menu_settings_subtitle);
        String aboutTitle = getResources().getString(R.string.menu_about_title);
        String aboutSubtitle = getResources().getString(R.string.menu_about_subtitle);
        String helpTitle = getResources().getString(R.string.menu_help_title);
        String helpSubtitle = getResources().getString(R.string.menu_help_subtitle);
        String statisticsTitle = getResources().getString(R.string.menu_statistics_title);
        String statisticsSubtitle = getResources().getString(R.string.menu_statistics_subtitle);

        menuItems.add(new MenuItem(homeTitle, homeSubtitle, R.drawable.ic_menu_home));
        menuItems.add(new MenuItem(settingsTitle, settingsSubtitle, android.R.drawable.ic_menu_preferences));
        menuItems.add(new MenuItem(aboutTitle, aboutSubtitle, android.R.drawable.ic_menu_info_details));
        menuItems.add(new MenuItem(helpTitle, helpSubtitle, android.R.drawable.ic_menu_help));
        menuItems.add(new MenuItem(statisticsTitle, statisticsSubtitle, android.R.drawable.ic_menu_edit));
        return menuItems;
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

    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle(getString(R.string.app_name));
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected((android.view.MenuItem) item)) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }

        /*switch (item.getItemId()) {
            // THIS IS YOUR DRAWER/HAMBURGER BUTTON
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);  // OPEN DRAWER
                return true;
        }*/

        return super.onOptionsItemSelected((android.view.MenuItem) item);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view

        /*super.onPrepareOptionsMenu(menu);
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        menu.findItem(R.id.menu_search).setVisible(!drawerOpen);*/
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
}
