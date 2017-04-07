package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.deleteproducts;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.business.PFACache;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.deleteproducts.listadapter.DeleteProductsAdapter;

import java.util.ArrayList;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 21.07.16 creation date
 */
public class DeleteProductsCache extends PFACache
{
    private AppCompatActivity activity;
    private FloatingActionButton deleteFab;
    private DeleteProductsAdapter deleteProductsAdapter;
    private String listId;
    private String listName;

    public DeleteProductsCache(DeleteProductsActivity activity, String listId, String listName)
    {
        this.activity = activity;
        this.listId = listId;
        this.listName = listName;

        deleteProductsAdapter = new DeleteProductsAdapter(new ArrayList<>(), this);

        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(deleteProductsAdapter);

        deleteFab = (FloatingActionButton) activity.findViewById(R.id.fab_delete_products);
    }

    public AppCompatActivity getActivity()
    {
        return activity;
    }

    public FloatingActionButton getDeleteFab()
    {
        return deleteFab;
    }

    public DeleteProductsAdapter getDeleteProductsAdapter()
    {
        return deleteProductsAdapter;
    }

    public String getListId()
    {
        return listId;
    }

    public String getListName()
    {
        return listName;
    }
}
