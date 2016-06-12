package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
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
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    List<MenuItem> mMenuItems = new ArrayList<>();
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

    }

    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle(getString(R.string.app_name));
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
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
