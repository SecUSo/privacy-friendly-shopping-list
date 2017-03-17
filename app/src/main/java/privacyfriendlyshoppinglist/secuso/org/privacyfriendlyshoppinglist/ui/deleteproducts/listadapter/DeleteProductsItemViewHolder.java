package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.deleteproducts.listadapter;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.TextView;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.ui.AbstractViewHolder;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.deleteproducts.DeleteProductsCache;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 21.07.16 creation date
 */
public class DeleteProductsItemViewHolder extends AbstractViewHolder<ProductItem, DeleteProductsCache>
{
    private DeleteProductItemCache deleteProductItemCache;

    public DeleteProductsItemViewHolder(final View parent, DeleteProductsCache cache)
    {
        super(parent,cache);
        this.deleteProductItemCache = new DeleteProductItemCache(parent);
    }

    public void processItem(ProductItem item)
    {
        deleteProductItemCache.getProductNameTextView().setText(item.getProductName());
        deleteProductItemCache.getProductQuantityTextView().setText(item.getQuantity());
        deleteProductItemCache.getCheckBox().setChecked(item.isChecked());
        updateVisibilityFormat(item);

        deleteProductItemCache.getProductCard().setAlpha(0.0f);
        deleteProductItemCache.getProductCard().setOnClickListener(new View.OnClickListener()
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
        Resources resources = deleteProductItemCache.getProductCard().getContext().getResources();
        TextView productNameTextView = deleteProductItemCache.getProductNameTextView();
        TextView productQuantityTextView = deleteProductItemCache.getProductQuantityTextView();
        AppCompatCheckBox checkBox = (AppCompatCheckBox) deleteProductItemCache.getCheckBox();
        if ( item.isSelectedForDeletion() )
        {
            int transparent = resources.getColor(R.color.transparent);
            int grey = resources.getColor(R.color.middlegrey);

            deleteProductItemCache.getProductCard().setCardBackgroundColor(transparent);
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

            deleteProductItemCache.getProductCard().setCardBackgroundColor(white);
            productNameTextView.setTextColor(black);
            productQuantityTextView.setTextColor(black);
            checkBox.setSupportButtonTintList(ColorStateList.valueOf(black));
            productNameTextView.setPaintFlags(productNameTextView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            productQuantityTextView.setPaintFlags(productQuantityTextView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }
}
