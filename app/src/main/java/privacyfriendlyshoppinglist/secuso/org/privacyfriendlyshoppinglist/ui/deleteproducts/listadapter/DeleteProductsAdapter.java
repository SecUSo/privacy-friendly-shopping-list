package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.deleteproducts.listadapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductDto;

import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 21.07.16 creation date
 */
public class DeleteProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private List<ProductDto> productsList;
    private AppCompatActivity activity;

    public DeleteProductsAdapter(List<ProductDto> productsList, AppCompatActivity activity)
    {
        this.productsList = productsList;
        this.activity = activity;
    }

    public void setProductsList(List<ProductDto> productsList)
    {
        this.productsList = productsList;
    }

    public List<ProductDto> getProductsList()
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
        ProductDto dto = productsList.get(position);
        holder.processDto(dto);
    }

    @Override
    public int getItemCount()
    {
        return productsList == null ? 0 : productsList.size();
    }
}
