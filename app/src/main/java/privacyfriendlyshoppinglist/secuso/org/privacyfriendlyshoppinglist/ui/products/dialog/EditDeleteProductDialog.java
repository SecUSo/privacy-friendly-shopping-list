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
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductActivityCache;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductsActivity;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.edit_delete_dialog, null);
        Button editButton = (Button) rootView.findViewById(R.id.edit);
        Button deleteButton = (Button) rootView.findViewById(R.id.delete);
        TextView titleTextView = (TextView) rootView.findViewById(R.id.title);

        String listDialogTitle = getContext().getResources().getString(R.string.product_as_title, dto.getProductName());
        titleTextView.setText(listDialogTitle);

        editButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
                DialogFragment productFragement = ProductDialogFragment.newEditDialogInstance(dto, cache);
                productFragement.show(cache.getActivity().getSupportFragmentManager(), "Product");
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
                MessageUtils.showAlertDialog(
                        getContext(),
                        R.string.delete_confirmation_title,
                        R.string.delete_product_confirmation,
                        dto.getProductName(),
                        deleteProduct()
                                .doOnCompleted(() ->
                                {
                                    ProductsActivity activity = (ProductsActivity) cache.getActivity();
                                    activity.updateListView();
                                }));
            }
        });

        builder.setView(rootView);
        return builder.create();
    }

    private Observable<Void> deleteProduct()
    {
        Observable observable = Observable
                .create(subscriber ->
                {
                    subscriber.onNext(deleteProductSync());
                    subscriber.onCompleted();
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private Void deleteProductSync()
    {
        productService.deleteById(dto.getId());
        return null;
    }

}
