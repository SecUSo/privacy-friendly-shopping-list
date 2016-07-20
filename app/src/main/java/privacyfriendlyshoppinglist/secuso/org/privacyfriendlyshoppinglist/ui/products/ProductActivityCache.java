package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.listadapter.ProductsAdapter;

import java.util.ArrayList;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 20.07.16 creation date
 */
public class ProductActivityCache
{
    private AppCompatActivity activity;
    private FloatingActionButton newListFab;
    private ProductsAdapter productsAdapter;
    private ImageView sortImageView;
    private ImageView deleteImageView;
    private String listId;

    public ProductActivityCache(AppCompatActivity activity, String listId)
    {
        this.activity = activity;
        this.listId = listId;

        productsAdapter = new ProductsAdapter(new ArrayList<>(), this);

        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(productsAdapter);

        newListFab = (FloatingActionButton) activity.findViewById(R.id.fab_new_product);
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

    public ProductsAdapter getProductsAdapter()
    {
        return productsAdapter;
    }

    public ImageView getSortImageView()
    {
        return sortImageView;
    }

    public ImageView getDeleteImageView()
    {
        return deleteImageView;
    }

    public String getListId()
    {
        return listId;
    }
}
