package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.deleteproducts.listadapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductItem;

import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 21.07.16 creation date
 */
public class DeleteProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private List<ProductItem> productsList;
    private AppCompatActivity activity;

    public DeleteProductsAdapter(List<ProductItem> productsList, AppCompatActivity activity)
    {
        this.productsList = productsList;
        this.activity = activity;
    }

    public void setProductsList(List<ProductItem> productsList)
    {
        this.productsList = productsList;
    }

    public List<ProductItem> getProductsList()
    {
        return productsList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.delete_product_list_item, parent, false);
        return new DeleteProductsItemViewHolder(view, activity);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position)
    {
        DeleteProductsItemViewHolder holder = (DeleteProductsItemViewHolder) viewHolder;
        ProductItem item = productsList.get(position);
        holder.processItem(item);
    }

    @Override
    public int getItemCount()
    {
        return productsList == null ? 0 : productsList.size();
    }
}
