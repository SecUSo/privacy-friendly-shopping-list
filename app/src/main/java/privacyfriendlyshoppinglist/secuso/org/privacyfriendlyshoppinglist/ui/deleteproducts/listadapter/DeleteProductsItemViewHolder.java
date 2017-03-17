package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.deleteproducts.listadapter;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductItem;

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

    public void processItem(ProductItem item)
    {
        cache.getProductNameTextView().setText(item.getProductName());
        cache.getProductQuantityTextView().setText(item.getQuantity());
        cache.getCheckBox().setChecked(item.isChecked());
        updateVisibilityFormat(item);

        cache.getProductCard().setAlpha(0.0f);
        cache.getProductCard().setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                item.setSelectedForDeletion(!item.isSelectedForDeletion());
                updateVisibilityFormat(item);
            }
        });

    }

    private void updateVisibilityFormat(ProductItem item)
    {
        Resources resources = cache.getProductCard().getContext().getResources();
        TextView productNameTextView = cache.getProductNameTextView();
        TextView productQuantityTextView = cache.getProductQuantityTextView();
        AppCompatCheckBox checkBox = (AppCompatCheckBox) cache.getCheckBox();
        if ( item.isSelectedForDeletion() )
        {
            int transparent = resources.getColor(R.color.transparent);
            int grey = resources.getColor(R.color.middlegrey);

            cache.getProductCard().setCardBackgroundColor(transparent);
            productNameTextView.setTextColor(grey);
            productQuantityTextView.setTextColor(grey);
            checkBox.setSupportButtonTintList(ColorStateList.valueOf(grey));
            productNameTextView.setPaintFlags(productNameTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            productQuantityTextView.setPaintFlags(productQuantityTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else
        {
            int white = resources.getColor(R.color.white);
            int black = resources.getColor(R.color.black);

            cache.getProductCard().setCardBackgroundColor(white);
            productNameTextView.setTextColor(black);
            productQuantityTextView.setTextColor(black);
            checkBox.setSupportButtonTintList(ColorStateList.valueOf(black));
            productNameTextView.setPaintFlags(productNameTextView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            productQuantityTextView.setPaintFlags(productQuantityTextView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }
}
