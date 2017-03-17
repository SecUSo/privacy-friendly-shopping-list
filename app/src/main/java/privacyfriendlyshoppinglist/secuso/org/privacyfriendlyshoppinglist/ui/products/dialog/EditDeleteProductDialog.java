package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.MessageUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.ProductService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductActivityCache;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductsActivity;

/**
 * Created by Chris on 11.08.2016.
 */
public class EditDeleteProductDialog extends DialogFragment
{
    private ProductActivityCache cache;
    private ProductItem item;
    private ProductService productService;

    public static EditDeleteProductDialog newEditDeleteInstance(ProductItem item, ProductActivityCache cache)
    {
        EditDeleteProductDialog dialogFragment = getEditDeleteFragment(item, cache);
        return dialogFragment;
    }

    private static EditDeleteProductDialog getEditDeleteFragment(ProductItem item, ProductActivityCache cache)
    {
        EditDeleteProductDialog dialogFragment = new EditDeleteProductDialog();
        dialogFragment.setCache(cache);
        dialogFragment.setItem(item);
        return dialogFragment;
    }

    public void setCache(ProductActivityCache cache)
    {
        this.cache = cache;
    }

    public void setItem(ProductItem item)
    {
        this.item = item;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AbstractInstanceFactory instanceFactory = new InstanceFactory(cache.getActivity().getApplicationContext());
        productService = (ProductService) instanceFactory.createInstance(ProductService.class);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.product_actions, null);
        Button editButton = (Button) rootView.findViewById(R.id.edit);
        Button shareButton = (Button) rootView.findViewById(R.id.share);
        Button deleteButton = (Button) rootView.findViewById(R.id.delete);
        TextView titleTextView = (TextView) rootView.findViewById(R.id.title);

        String listDialogTitle = getContext().getResources().getString(R.string.product_as_title, item.getProductName());
        titleTextView.setText(listDialogTitle);

        editButton.setOnClickListener(getEditOnClickListener());
        deleteButton.setOnClickListener(getDeleteOnClickListener());
        shareButton.setOnClickListener(getShareOnClickListener());

        builder.setView(rootView);
        return builder.create();
    }

    private View.OnClickListener getDeleteOnClickListener()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
                MessageUtils.showAlertDialog(
                        getContext(),
                        R.string.delete_confirmation_title,
                        R.string.delete_product_confirmation,
                        item.getProductName(),
                        productService.deleteById(item.getId())
                                .doOnCompleted(() ->
                                {
                                    ProductsActivity activity = (ProductsActivity) cache.getActivity();
                                    activity.updateListView();
                                }));
            }
        };
    }

    private View.OnClickListener getEditOnClickListener()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
                if ( !ProductDialogFragment.isOpened() )
                {
                    DialogFragment productFragement = ProductDialogFragment.newEditDialogInstance(item, cache);
                    productFragement.show(cache.getActivity().getSupportFragmentManager(), "Product");
                }
            }
        };
    }

    private View.OnClickListener getShareOnClickListener()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
                String shareableText = productService.getSharableText(item);
                MessageUtils.shareText(getContext(), shareableText, item.getProductName());
            }
        };
    }

}
