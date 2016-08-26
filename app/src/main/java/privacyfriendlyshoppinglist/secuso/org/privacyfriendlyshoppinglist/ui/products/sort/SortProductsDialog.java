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
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.ShoppingListService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 22.07.16 creation date
 */
public class SortProductsDialog extends DialogFragment
{
    private Activity activity;
    private String listId;

    public static SortProductsDialog newInstance(Activity activity, String listId)
    {
        SortProductsDialog sortProductsDialog = new SortProductsDialog();
        sortProductsDialog.setActivity(activity);
        sortProductsDialog.setListId(listId);
        return sortProductsDialog;
    }

    public void setActivity(Activity activity)
    {
        this.activity = activity;
    }

    public void setListId(String listId)
    {
        this.listId = listId;
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
                ProductsActivity host = (ProductsActivity) activity;
                AbstractInstanceFactory instanceFactory = new InstanceFactory(host.getApplicationContext());
                ProductService productService = (ProductService) instanceFactory.createInstance(ProductService.class);
                ShoppingListService shoppingListService = (ShoppingListService) instanceFactory.createInstance(ShoppingListService.class);

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
                boolean ascending = cache.getAscending().isChecked();
                final String finalCriteria = criteria;

                List<ProductDto> productDtos = new ArrayList<>();

                productService.getAllProducts(listId)
                        .doOnNext(dto -> productDtos.add(dto))
                        .doOnCompleted(() ->
                        {
                            productService.sortProducts(productDtos, finalCriteria, ascending);
                            host.reorderProductView(productDtos);
                            host.reorderProductViewBySelection();
                        })
                        .subscribe();

                // save sort options
                shoppingListService.getById(listId)
                        .doOnNext(dto ->
                        {
                            dto.setSortAscending(ascending);
                            dto.setSortCriteria(finalCriteria);
                            shoppingListService.saveOrUpdate(dto).subscribe();
                        })
                        .subscribe();

            }
        });

        return builder.create();
    }
}
