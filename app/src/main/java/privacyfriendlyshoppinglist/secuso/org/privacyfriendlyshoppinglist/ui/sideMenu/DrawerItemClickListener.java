package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.sideMenu;

import android.app.Activity;
import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppingList.ShoppingListFragement;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.utils.MainActivityUtils;

/**
 * Created by Chris on 11.06.2016.
 */
public class DrawerItemClickListener implements ListView.OnItemClickListener
{
    private final Activity activity;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    public DrawerItemClickListener(Activity activity, DrawerLayout mDrawerLayout, ListView mdrawerList)
    {
        this.activity = activity;
        this.mDrawerLayout = mDrawerLayout;
        this.mDrawerList = mdrawerList;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Fragment fragment = getFragment(position);
        MainActivityUtils.replaceFragmentPlaceholder(fragment, activity);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    private Fragment getFragment(int position)
    {
        Fragment fragment;
        switch ( position )
        {
            case 0:
                fragment = new ShoppingListFragement();
                break;
            default:
                fragment = new AboutFragment();
                break;
        }
        return fragment;
    }
}