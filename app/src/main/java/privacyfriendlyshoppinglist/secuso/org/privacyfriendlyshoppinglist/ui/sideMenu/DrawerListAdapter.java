package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.sideMenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 12.06.2016.
 */
public class DrawerListAdapter extends BaseAdapter
{

    Context mContext;
    List<MenuItem> mMenuItems;

    public DrawerListAdapter(Context context, List<MenuItem> menuItems) {
        mContext = context;
        mMenuItems = menuItems;
    }

    @Override
    public int getCount() {
        return mMenuItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mMenuItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.drawer_list_item, null);
        }
        else {
            view = convertView;
        }

        TextView titleView = (TextView) view.findViewById(R.id.menu_item);
        TextView subtitleView = (TextView) view.findViewById(R.id.menu_subTitle );
        ImageView iconView = (ImageView) view.findViewById(R.id.icon);

        titleView.setText( mMenuItems.get(position).mTitle );
        subtitleView.setText( mMenuItems.get(position).mSubtitle );
        iconView.setImageResource(mMenuItems.get(position).mIcon);

        return view;
    }
}