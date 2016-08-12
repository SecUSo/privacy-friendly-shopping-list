package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.sort;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.comparators.PFAComparators;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.ProductService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductActivityCache;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductsActivity;

import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 22.07.16 creation date
 */
public class SortProductsDialog extends DialogFragment
{
    private ProductActivityCache productActivityCache;

    public static SortProductsDialog newInstance(ProductActivityCache productActivityCache)
    {
        SortProductsDialog sortProductsDialog = new SortProductsDialog();
        sortProductsDialog.setProductActivityCache(productActivityCache);
        return sortProductsDialog;
    }

    public void setProductActivityCache(ProductActivityCache productActivityCache)
    {
        this.productActivityCache = productActivityCache;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        LayoutInflater i = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View rootView = i.inflate(R.layout.sort_products_dialog, null);

        SortProductsDialogCache cache = new SortProductsDialogCache(rootView);
        cache.getAscending().setChecked(true);
        cache.getName().setChecked(true);

        builder.setView(rootView);
        builder.setTitle(getActivity().getString(R.string.sort_options));
        builder.setNegativeButton(getActivity().getString(R.string.cancel), null);
        builder.setPositiveButton(getActivity().getString(R.string.okay), new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                ProductsActivity host = (ProductsActivity) productActivityCache.getActivity();
                AbstractInstanceFactory instanceFactory = new InstanceFactory(host.getApplicationContext());
                ProductService productService = (ProductService) instanceFactory.createInstance(ProductService.class);

                List<ProductDto> productDtos = productService.getAllProducts(productActivityCache.getListId());
                String criteria = PFAComparators.SORT_BY_NAME;
                if ( cache.getQuantity().isChecked() )
                {
                    criteria = PFAComparators.SORT_BY_QUANTITY;
                }
                else if ( cache.getStore().isChecked() )
                {
                    criteria = PFAComparators.SORT_BY_STORE;
                }
                else if ( cache.getPrice().isChecked() )
                {
                    criteria = PFAComparators.SORT_BY_PRICE;
                }
                else if ( cache.getCategory().isChecked() )
                {
                    criteria = PFAComparators.SORT_BY_CATEGORY;
                }
                productService.sortProducts(productDtos, criteria, cache.getAscending().isChecked());
                host.reorderProductView(productDtos);
                host.reorderProductViewBySelection();
            }
        });

        return builder.create();
    }
}
