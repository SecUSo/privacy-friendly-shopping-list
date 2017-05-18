package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.business.PFACache;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.fab.FabScrollListenerForCreateActivities;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist.listadapter.ListsAdapter;

import java.util.ArrayList;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.07.16 creation date
 */
public class ShoppingListActivityCache extends PFACache
{
    private AppCompatActivity activity;
    private FloatingActionButton newListFab;
    private ListsAdapter listAdapter;
    private LinearLayout noListsLayout;
    private LinearLayout alertTextView;

    public ShoppingListActivityCache(AppCompatActivity activity)
    {
        this.activity = activity;

        listAdapter = new ListsAdapter(new ArrayList<>(), this);

        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(listAdapter);

        newListFab = (FloatingActionButton) activity.findViewById(R.id.fab_new_list);
        alertTextView = (LinearLayout) activity.findViewById(R.id.insert_alert);

        noListsLayout = (LinearLayout) activity.findViewById(R.id.no_lists_layout);

        recyclerView.addOnScrollListener(new FabScrollListenerForCreateActivities(newListFab));
    }

    public AppCompatActivity getActivity()
    {
        return activity;
    }

    public FloatingActionButton getNewListFab()
    {
        return newListFab;
    }

    public ListsAdapter getListAdapter()
    {
        return listAdapter;
    }

    public LinearLayout getNoListsLayout()
    {
        return noListsLayout;
    }

    public LinearLayout getAlertTextView()
    {
        return alertTextView;
    }
}
