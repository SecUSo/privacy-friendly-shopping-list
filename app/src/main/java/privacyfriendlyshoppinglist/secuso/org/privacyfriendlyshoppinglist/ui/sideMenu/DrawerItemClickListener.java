package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.sideMenu;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;


import android.support.v4.app.ListFragment;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.MainActivity;

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
        Fragment fragment;

        switch ( position )
        {
            case 0:
                fragment = new AboutFragment();
                break;
            default:
                fragment = new AboutFragment();
                break;
        }

        FragmentManager fragmentManager = activity.getFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        mDrawerLayout.closeDrawer(mDrawerList);


    }
}