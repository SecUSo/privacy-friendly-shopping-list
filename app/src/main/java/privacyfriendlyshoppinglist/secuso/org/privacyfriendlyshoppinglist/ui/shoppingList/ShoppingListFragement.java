package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppingList;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.sideMenu.DrawerItemClickListener;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.sideMenu.DrawerListAdapter;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.sideMenu.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 12.06.2016.
 */
public class ShoppingListFragement extends Fragment
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

    Activity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.shopping_list_layout, container, false);
        activity.setTitle(R.string.action_about);

        mMenuItems.add(new MenuItem("Home", "Home Screen", android.R.drawable.ic_menu_edit));
        mMenuItems.add(new MenuItem("About", "Version 1.0", android.R.drawable.ic_menu_edit));
        mMenuItems.add(new MenuItem("Help", "Get Help", android.R.drawable.ic_menu_edit));

        mDrawerLayout = (DrawerLayout) rootView.findViewById(R.id.drawer_layout);

        mDrawerPane = (CoordinatorLayout) rootView.findViewById(R.id.coordinatorLayout);
        mDrawerList = (ListView) rootView.findViewById(R.id.left_drawer);
        DrawerListAdapter adapter = new DrawerListAdapter(activity, mMenuItems);
        mDrawerList.setAdapter(adapter);

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener(activity, mDrawerLayout, mDrawerList));

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        setupRecyclerView(recyclerView);

        container.removeAllViews();
        return rootView;

    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext()));
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

}
