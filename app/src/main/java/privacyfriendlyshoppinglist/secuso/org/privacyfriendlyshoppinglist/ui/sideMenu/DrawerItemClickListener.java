package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.sideMenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Chris on 11.06.2016.
 */
public class DrawerItemClickListener implements ListView.OnItemClickListener
{

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Toast.makeText(view.getContext(), "Item Clicked", Toast.LENGTH_LONG).show();

        //selectItem(position);
    }

    /*private void selectItem(int position){
        Fragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putInt(ListItemFragment.ARG_LISTITEM_NUMBER, position);
        fragment.setArguments(args);
    }*/


}
