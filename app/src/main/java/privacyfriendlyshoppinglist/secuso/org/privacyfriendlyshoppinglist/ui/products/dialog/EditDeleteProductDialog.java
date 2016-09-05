package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.MessageUtils;
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


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AbstractInstanceFactory instanceFactory = new InstanceFactory(cache.getActivity().getApplicationContext());
        productService = (ProductService) instanceFactory.createInstance(ProductService.class);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogColourful);

        builder.setMessage(R.string.edit_dialog_product)
                .setTitle(getContext().getResources().getString(R.string.product_as_title, dto.getProductName()))
                .setIcon(R.drawable.ic_edit_custom_color)
                .setPositiveButton(R.string.edit, getEditOnClickListener())
                .setNegativeButton(R.string.delete, getDeleteOnClickListener())
                .setNeutralButton(R.string.share, getShareOnClickListener());

        return builder.create();
    }

    private DialogInterface.OnClickListener getDeleteOnClickListener()
    {
        return new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                dismiss();
                MessageUtils.showAlertDialog(
                        getContext(),
                        R.string.delete_confirmation_title,
                        R.string.delete_product_confirmation,
                        dto.getProductName(),
                        productService.deleteById(dto.getId())
                                .doOnCompleted(() ->
                                {
                                    ProductsActivity activity = (ProductsActivity) cache.getActivity();
                                    activity.updateListView();
                                }));
            }
        };
    }

    private DialogInterface.OnClickListener getEditOnClickListener()
    {
        return new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                dismiss();
                if ( !ProductDialogFragment.isOpened() )
                {
                    DialogFragment productFragement = ProductDialogFragment.newEditDialogInstance(dto, cache);
                    productFragement.show(cache.getActivity().getSupportFragmentManager(), "Product");
                }
            }
        };
    }

    private DialogInterface.OnClickListener getShareOnClickListener()
    {
        return new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                dismiss();
                String shareableText = productService.getSharableText(dto);
                MessageUtils.shareText(getContext(), shareableText, dto.getProductName());
            }
        };
    }

}
