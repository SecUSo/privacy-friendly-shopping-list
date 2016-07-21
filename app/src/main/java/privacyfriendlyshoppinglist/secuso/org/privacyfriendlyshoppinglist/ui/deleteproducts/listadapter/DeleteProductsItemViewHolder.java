package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.deleteproducts.listadapter;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductDto;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 21.07.16 creation date
 */
public class DeleteProductsItemViewHolder extends RecyclerView.ViewHolder
{
    private DeleteProductItemCache cache;
    private AppCompatActivity activity;

    public DeleteProductsItemViewHolder(final View parent, AppCompatActivity activity)
    {
        super(parent);
        this.cache = new DeleteProductItemCache(parent);
        this.activity = activity;
    }

    public void processDto(ProductDto dto)
    {
        cache.getProductNameTextView().setText(dto.getProductName());
        cache.getProductQuantityTextView().setText("0");

        cache.getProductCard().setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // todo: show seek bar to modify bought quantity
            }
        });

        cache.getProductCard().setOnLongClickListener(new View.OnLongClickListener()
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
