package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.sideMenu;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;

/**
 * Created by Chris on 26.06.2016.
 */
public class SettingsFragment extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.settings_fragment, container, false);
        getActivity().setTitle(R.string.menu_item_help);

        container.removeAllViews();
        return rootView;
    }
}
