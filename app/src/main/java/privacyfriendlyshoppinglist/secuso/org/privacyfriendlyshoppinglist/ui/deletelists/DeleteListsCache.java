package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.deletelists;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.business.PFACache;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.deletelists.listadapter.DeleteListsAdapter;

import java.util.ArrayList;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.07.16 creation date
 */
public class DeleteListsCache extends PFACache
{
    private AppCompatActivity activity;
    private FloatingActionButton deleteFab;
    private DeleteListsAdapter deleteListsAdapter;

    public DeleteListsCache(DeleteListsActivity activity)
    {
        this.activity = activity;

        deleteListsAdapter = new DeleteListsAdapter(new ArrayList<>(), this);

        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(deleteListsAdapter);

        deleteFab = (FloatingActionButton) activity.findViewById(R.id.fab_delete_lists);
    }

    public AppCompatActivity getActivity()
    {
        return activity;
    }

    public FloatingActionButton getDeleteFab()
    {
        return deleteFab;
    }

    public DeleteListsAdapter getDeleteListsAdapter()
    {
        return deleteListsAdapter;
    }
}
