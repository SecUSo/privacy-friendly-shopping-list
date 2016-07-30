package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.StringUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.ProductService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductActivityCache;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductsActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.dialog.listeners.onFocusListener.FocusListener;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.dialog.listeners.price.PriceInputFilter;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Chris on 18.07.2016.
 */
public class ProductDialogFragment extends DialogFragment
{
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ProductDialogCache dialogCache;
    private ProductActivityCache cache;
    private ProductDto dto;
    private ProductService productService;

    private static boolean edited;

    public static ProductDialogFragment newEditDialogInstance(ProductDto dto, ProductActivityCache cache)
    {
        edited = true;
        ProductDialogFragment dialogFragment = getProductDialogFragment(dto, cache);
        return dialogFragment;
    }

    public static ProductDialogFragment newAddDialogInstance(ProductDto dto, ProductActivityCache cache)
    {
        edited = false;
        ProductDialogFragment dialogFragment = getProductDialogFragment(dto, cache);
        return dialogFragment;
    }

    private static ProductDialogFragment getProductDialogFragment(ProductDto dto, ProductActivityCache cache)
    {
        ProductDialogFragment dialogFragment = new ProductDialogFragment();
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
        // Read DTO contents and activity from cache
        Activity activity = cache.getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.product_dialog, null);

        dialogCache = new ProductDialogCache(v);

        dialogCache.getExpandableLayout().setVisibility(View.GONE);

        dialogCache.getProductName().setText(dto.getProductName());
        dialogCache.getProductNotes().setText(dto.getProductNotes());
        dialogCache.getQuantity().setText(dto.getQuantity());
        dialogCache.getPrice().setText(dto.getProductPrice());
        dialogCache.getCategory().setText(dto.getProductCategory());
        dialogCache.getCustomStore().setText(dto.getProductStore());

        dialogCache.getProductCheckBox().setChecked(dto.isChecked());

        if ( edited == true )
        {
            dialogCache.getProductName().setHint(R.string.product_name_edit);
        }

        dialogCache.getButtonPlus().setOnClickListener(new View.OnClickListener()
        {
            int value;
            String newQuantity;

            @Override
            public void onClick(View view)
            {

                if ( !StringUtils.isEmpty(String.valueOf(dialogCache.getQuantity().getText())) )
                {
                    value = Integer.parseInt(String.valueOf(dialogCache.getQuantity().getText()));
                    value++;
                    newQuantity = String.valueOf(value);
                    dialogCache.getQuantity().setText(newQuantity);
                }
                else
                {
                    dialogCache.getQuantity().setText("1");
                }
            }
        });

        dialogCache.getButtonMinus().setOnClickListener(new View.OnClickListener()
        {
            int value;
            String newQuantity;

            @Override
            public void onClick(View view)
            {
                value = Integer.parseInt(String.valueOf(dialogCache.getQuantity().getText()));
                if ( value > 0 )
                {
                    value--;
                    newQuantity = String.valueOf(value);
                    dialogCache.getQuantity().setText(newQuantity);
                }
            }
        });


        dialogCache.getExpandableImageView().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if ( dialogCache.getExpandableLayout().getVisibility() == View.GONE )
                {
                    dialogCache.getExpandableImageView().setImageResource(R.drawable.expander_ic_maximized);
                    dialogCache.getExpandableLayout().setVisibility(View.VISIBLE);
                }
                else
                {
                    dialogCache.getExpandableImageView().setImageResource(R.drawable.expander_ic_minimized);
                    dialogCache.getExpandableLayout().setVisibility(View.GONE);
                }
            }
        });


        dialogCache.getCameraButton().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Uri fileUri;

//                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if ( takePictureIntent.resolveActivity(cache.getActivity().getPackageManager()) != null )
                {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
//                Bitmap bp = (Bitmap) intent.getExtras().get("data");
//                dialogCache.getCameraImage().setImageBitmap(bp);

                //final Fragment homeFragment = activity.getFragmentManager().findFragmentById(R.id.cont);

                //homeFragment.startActivityForResult(intent, 0);

                //ProductDialogFragment.super.onActivityResult( int requestCode, intent);

            }

        });

        dialogCache.getPrice().setFilters(new InputFilter[]{new PriceInputFilter(dialogCache)});


        dialogCache.getProductNotes().setOnFocusChangeListener(new FocusListener(dialogCache));
        dialogCache.getProductName().setOnFocusChangeListener(new FocusListener(dialogCache));
        dialogCache.getQuantity().setOnFocusChangeListener(new FocusListener(dialogCache));
        dialogCache.getPrice().setOnFocusChangeListener(new FocusListener(dialogCache));



        builder.setPositiveButton(cache.getActivity().getResources().getString(R.string.okay), new DialogInterface.OnClickListener()

        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {

            }
        });

        builder.setNegativeButton(cache.getActivity().getResources().getString(R.string.cancel), new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
            }
        });

        builder.setView(v);

        AlertDialog dialogCreate = builder.create();

        dialogCreate.show();
        dialogCreate.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if ( StringUtils.isEmpty(String.valueOf(dialogCache.getProductName().getText())) )
                {
                    Toast toast = Toast.makeText(activity.getApplicationContext(), R.string.alert_missing_product_name, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else
                {
                    dto.setProductName(String.valueOf(dialogCache.getProductName().getText()));

                    dto.setProductNotes(String.valueOf(dialogCache.getProductNotes().getText()));
                    dto.setQuantity(String.valueOf(dialogCache.getQuantity().getText()));

                    dto.setProductPrice(String.valueOf(dialogCache.getPrice().getText()));

                    dto.setProductCategory(String.valueOf(dialogCache.getCategory().getText()));
                    dto.setProductStore(String.valueOf(dialogCache.getCustomStore().getText()));

                    dto.setChecked(dialogCache.getProductCheckBox().isChecked());

                    productService.saveOrUpdate(dto, cache.getListId());
                    ProductsActivity productsActivity = (ProductsActivity) cache.getActivity();
                    productsActivity.updateListView();

                    dialogCreate.dismiss();
                }
            }
        });
        return dialogCreate;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if ( requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK )
        {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            dialogCache.getCameraImage().setImageBitmap(imageBitmap);
        }
    }
}
