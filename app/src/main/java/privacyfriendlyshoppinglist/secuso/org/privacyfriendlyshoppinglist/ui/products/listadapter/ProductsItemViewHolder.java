package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.listadapter;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.ProductService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductActivityCache;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductsActivity;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 20.07.16 creation date
 */
public class ProductsItemViewHolder extends RecyclerView.ViewHolder
{
    private ProductItemCache productItemCache;
    private ProductActivityCache productActivityCache;
    private ProductService productService;

    public ProductsItemViewHolder(final View parent, ProductActivityCache cache)
    {
        super(parent);
        this.productItemCache = new ProductItemCache(parent);
        this.productActivityCache = cache;
        AbstractInstanceFactory instanceFactory = new InstanceFactory(productActivityCache.getActivity());
        this.productService = (ProductService) instanceFactory.createInstance(ProductService.class);

    }

    public void processDto(ProductDto dto)
    {
        final CheckBox checkbox = productItemCache.getCheckbox();
        checkbox.setChecked(dto.isChecked());
        productItemCache.getProductNameTextView().setText(dto.getProductName());
        productItemCache.getQuantityTextView().setText(dto.getQuantity());

        updateVisibilityFormat(dto);

        checkbox.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dto.setChecked(checkbox.isChecked());
                productService.saveOrUpdate(dto, productActivityCache.getListId());

                ProductsActivity host = (ProductsActivity) productActivityCache.getActivity();
                host.updateTotals();
                host.changeItemPosition(dto);

                updateVisibilityFormat(dto);
            }
        });


        productItemCache.getProductCard().setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // todo: show seek bar to modify bought quantity
            }
        });

        productItemCache.getProductCard().setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                // todo: show dialog
                return true;
            }
        });

    }

    private void updateVisibilityFormat(ProductDto dto)
    {
        Resources resources = productActivityCache.getActivity().getResources();
        TextView productNameTextView = productItemCache.getProductNameTextView();
        TextView quantityTextView = productItemCache.getQuantityTextView();
        CardView productCard = productItemCache.getProductCard();
        AppCompatCheckBox checkbox = (AppCompatCheckBox) productItemCache.getCheckbox();

        if ( dto.isChecked() )
        {
            int grey = resources.getColor(R.color.middlegrey);
            productCard.setCardBackgroundColor(resources.getColor(R.color.transparent));
            checkbox.setSupportButtonTintList(ColorStateList.valueOf(resources.getColor(R.color.middleblue)));
            productNameTextView.setTextColor(grey);
            quantityTextView.setTextColor(grey);
        }
        else
        {
            int black = resources.getColor(R.color.black);
            productCard.setCardBackgroundColor(resources.getColor(R.color.white));
            checkbox.setSupportButtonTintList(ColorStateList.valueOf(resources.getColor(R.color.colorAccent)));
            productNameTextView.setTextColor(black);
            quantityTextView.setTextColor(black);
        }

    }
}
