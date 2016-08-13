package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.dialog;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.ProductService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductActivityCache;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductsActivity;

/**
 * Created by Chris on 11.08.2016.
 */
public class EditDeleteProductDialog extends DialogFragment
{
    private ProductActivityCache cache;
    private ProductDto dto;
    private ProductService productService;

    public static EditDeleteProductDialog newEditDeleteInstance(ProductDto dto, ProductActivityCache cache)
    {
        EditDeleteProductDialog dialogFragment = getEditDeleteFragment(dto, cache);
        return dialogFragment;
    }

    private static EditDeleteProductDialog getEditDeleteFragment(ProductDto dto, ProductActivityCache cache)
    {
        EditDeleteProductDialog dialogFragment = new EditDeleteProductDialog();
        dialogFragment.setCache(cache);
        dialogFragment.setDto(dto);
        AbstractInstanceFactory instanceFactory = new InstanceFactory(cache.getActivity().getApplicationContext());
        ProductService productService = (ProductService) instanceFactory.createInstance(ProductService.class);
        dialogFragment.setProductService(productService);
        return dialogFragment;
    }

    public void setCache(ProductActivityCache cache)
    {
        this.cache = cache;
    }

    public void setDto(ProductDto dto)
    {
        this.dto = dto;
    }

    public void setProductService(ProductService productService)
    {
        this.productService = productService;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.edit_dialog_message)
                .setPositiveButton(R.string.edit, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {

                        DialogFragment productFragement = ProductDialogFragment.newEditDialogInstance(dto, cache);
                        productFragement.show(cache.getActivity().getSupportFragmentManager(), "Product");

                    }
                })
                .setNegativeButton(R.string.delete, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {

                        Snackbar.make(cache.getNewListFab(), R.string.delele_products_confirmation, Snackbar.LENGTH_LONG)
                                .setAction(R.string.okay, new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        productService.deleteById(dto.getId());
                                        ProductsActivity activity = (ProductsActivity) cache.getActivity();
                                        activity.updateListView();
                                    }
                                }).show();

                    }
                });

        return builder.create();
    }

}
