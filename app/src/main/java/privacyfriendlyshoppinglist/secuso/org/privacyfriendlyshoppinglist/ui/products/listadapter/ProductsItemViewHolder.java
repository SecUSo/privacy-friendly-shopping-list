package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.listadapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductActivityCache;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 20.07.16 creation date
 */
public class ProductsItemViewHolder extends RecyclerView.ViewHolder
{
    private ProductItemCache productItemCache;
    private ProductActivityCache productActivityCache;

    public ProductsItemViewHolder(final View parent, ProductActivityCache cache)
    {
        super(parent);
        this.productItemCache = new ProductItemCache(parent);
        this.productActivityCache = cache;

    }

    public void processDto(ProductDto dto)
    {
        productItemCache.getProductName().setText(dto.getProductName());
        productItemCache.getNrProducts().setText("0");

        productItemCache.getListCard().setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // todo: show seek bar to modify bought quantity
            }
        });

        productItemCache.getListCard().setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                // todo: show dialog
                return true;
            }
        });

    }
}
