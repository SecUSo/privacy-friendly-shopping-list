package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist.listadapter.ListsAdapter;

import java.util.ArrayList;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.07.16 creation date
 */
public class ShoppingListCache
{
    private AppCompatActivity activity;
    private FloatingActionButton newListFab;
    private ListsAdapter listAdapter;
    private ImageView sortImageView;
    private ImageView deleteImageView;

    public ShoppingListCache(AppCompatActivity activity)
    {
        this.activity = activity;

        listAdapter = new ListsAdapter(new ArrayList<>(), this);

        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(listAdapter);

        newListFab = (FloatingActionButton) activity.findViewById(R.id.fab_new_list);
        sortImageView = (ImageView) activity.findViewById(R.id.imageview_sort);
        deleteImageView = (ImageView) activity.findViewById(R.id.imageview_delete);
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

    public ImageView getSortImageView()
    {
        return sortImageView;
    }

    public ImageView getDeleteImageView()
    {
        return deleteImageView;
    }
}
