package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.listadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductActivityCache;

import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 20.07.16 creation date
 */
public class ProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private List<ProductDto> productsList;
    private ProductActivityCache cache;

    public ProductsAdapter(List<ProductDto> productsList, ProductActivityCache cache)
    {
        this.productsList = productsList;
        this.cache = cache;
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
        View view = LayoutInflater.from(context).inflate(R.layout.product_list_item, parent, false);
        return new ProductsItemViewHolder(view, cache);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position)
    {
        ProductsItemViewHolder holder = (ProductsItemViewHolder) viewHolder;
        ProductDto dto = productsList.get(position);
        holder.processDto(dto);
    }

    @Override
    public int getItemCount()
    {
        return productsList == null ? 0 : productsList.size();
    }
}
