package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.listadapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
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
}
