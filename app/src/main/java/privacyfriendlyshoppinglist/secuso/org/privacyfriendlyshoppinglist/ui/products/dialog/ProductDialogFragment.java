package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.dialog;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.MessageUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.StringUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.ProductService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.AutoCompleteLists;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.camera.CameraActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductActivityCache;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductsActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.dialog.listeners.onFocusListener.ProductDialogFocusListener;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.dialog.listeners.price.PriceInputFilter;
import rx.Observable;

import java.io.File;
import java.util.Set;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Chris on 18.07.2016.
 */
public class ProductDialogFragment extends DialogFragment
{
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int MY_PERMISSIONS_REQUEST_USE_CAMERA = 2;

    private static boolean opened;
    private static boolean editDialog;
    private static boolean resetState;
    private static boolean saveConfirmed;

    private ProductDto dto;
    private ProductDialogCache dialogCache;
    private ProductActivityCache cache;

    private ProductService productService;

    public static ProductDialogFragment newEditDialogInstance(ProductDto dto, ProductActivityCache cache)
    {
        editDialog = true;
        ProductDialogFragment dialogFragment = getProductDialogFragment(dto, cache);
        return dialogFragment;
    }

    public static ProductDialogFragment newAddDialogInstance(ProductDto dto, ProductActivityCache cache)
    {
        editDialog = false;
        ProductDialogFragment dialogFragment = getProductDialogFragment(dto, cache);
        return dialogFragment;
    }

    private static ProductDialogFragment getProductDialogFragment(ProductDto dto, ProductActivityCache cache)
    {
        ProductDialogFragment dialogFragment = new ProductDialogFragment();
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
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        opened = true; // flag to avoid opening this dialog twice
    }

    @Override
    public void onDismiss(DialogInterface dialog)
    {
        super.onDismiss(dialog);
        opened = false; // flag to avoid opening this dialog twice

        if ( resetState && !saveConfirmed )
        {
            // if dto was implicitly saved because of taking a picture for the product, then delete the product
            productService.deleteById(dto.getId());
            ProductsActivity productsActivity = (ProductsActivity) cache.getActivity();
            productsActivity.updateListView();
            resetState = false;
        }
    }

    public static boolean isOpened()
    {
        return opened;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AbstractInstanceFactory instanceFactory = new InstanceFactory(cache.getActivity().getApplicationContext());
        productService = (ProductService) instanceFactory.createInstance(ProductService.class);

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

        PackageManager pm = getContext().getPackageManager();
        if ( !pm.hasSystemFeature(PackageManager.FEATURE_CAMERA) )
        {
            dialogCache.getCameraIcon().setVisibility(View.GONE);
            dialogCache.getProductImage().setVisibility(View.GONE);
        }

        if ( editDialog )
        {
            dialogCache.getTitleTextView().setText(getActivity().getResources().getString(R.string.product_name_edit));
            dialogCache.getProductImage().setImageBitmap(dto.getThumbnailBitmap());
        }
        else
        {
            dialogCache.getTitleTextView().setText(getActivity().getResources().getString(R.string.product_name_new));
            Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_menu_camera);
            dialogCache.getProductImage().setImageBitmap(bitmap);
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
                    dialogCache.getExpandableImageView().setImageResource(R.drawable.ic_keyboard_arrow_up_white_48dp);
                    dialogCache.getExpandableLayout().setVisibility(View.VISIBLE);
                }
                else
                {
                    dialogCache.getExpandableImageView().setImageResource(R.drawable.ic_keyboard_arrow_down_white_48dp);
                    dialogCache.getExpandableLayout().setVisibility(View.GONE);
                }
            }
        });


        dialogCache.getCameraIcon().setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                int permissionCheck = ContextCompat.checkSelfPermission(cache.getActivity(), Manifest.permission.CAMERA);

                if ( permissionCheck == PackageManager.PERMISSION_GRANTED )
                {
                    startImageCaptureAction();
                }
                else
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_USE_CAMERA);
                }

            }

        });

        dialogCache.getPrice().setFilters(new InputFilter[]{new PriceInputFilter(dialogCache)});


        dialogCache.getProductNotes().setOnFocusChangeListener(new ProductDialogFocusListener(dialogCache));
        dialogCache.getProductName().setOnFocusChangeListener(new ProductDialogFocusListener(dialogCache));

        Observable<AutoCompleteLists> rxAutoCompleteLists = productService.getAutoCompleteListsObservable();
        AutoCompleteLists autoCompleteLists = new AutoCompleteLists();
        rxAutoCompleteLists
                .doOnNext(result -> result.copyTo(autoCompleteLists))
                .doOnCompleted(() -> setupAutoCompleteLists(autoCompleteLists))
                .subscribe();

        dialogCache.getQuantity().setOnFocusChangeListener(new ProductDialogFocusListener(dialogCache));
        dialogCache.getPrice().setOnFocusChangeListener(new ProductDialogFocusListener(dialogCache));

        dialogCache.getProductImage().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if ( !dto.isDefaultImage() && !dialogCache.isImageScheduledForDeletion() )
                {
                    if ( !ImageViewerDialog.isOpened() )
                    {
                        DialogFragment imageViewerDialog = ImageViewerDialog.newInstance(dto, dialogCache);
                        imageViewerDialog.show(cache.getActivity().getSupportFragmentManager(), "ProductViewer");
                    }
                }
            }
        });


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

        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String productName = String.valueOf(dialogCache.getProductName().getText());
                if ( StringUtils.isEmpty(productName) )
                {
                    MessageUtils.showToast(getActivity().getApplicationContext(), R.string.alert_missing_product_name, Toast.LENGTH_LONG);
                }
                else
                {
                    saveConfirmed = true;
                    saveUserInput(productName);
                    ProductsActivity productsActivity = (ProductsActivity) cache.getActivity();
                    productsActivity.updateListView();

                    dialog.dismiss();
                }
            }
        });
        if ( !editDialog )
        {
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

        dialogCache.getProductNameInputLayout().setError(null);
        dialogCache.getProductName().addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                Set<String> products = autoCompleteLists.getProducts();
                if ( products.contains(s.toString()) )
                {
                    dialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(false);
                    dialogCache.getProductNameInputLayout().setError(getContext().getString(R.string.product_already_exists));
                }
                else
                {
                    dialogCache.getProductNameInputLayout().setError(null);
                    dialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(true);
                }
            }
        });

        return dialog;
    }

    private void saveUserInput(String productName)
    {
        dto.setProductName(productName);
        dto.setProductNotes(String.valueOf(dialogCache.getProductNotes().getText()));
        dto.setQuantity(String.valueOf(dialogCache.getQuantity().getText()));
        dto.setProductPrice(String.valueOf(dialogCache.getPrice().getText()));
        dto.setProductCategory(String.valueOf(dialogCache.getCategory().getText()));
        dto.setProductStore(String.valueOf(dialogCache.getCustomStore().getText()));
        dto.setChecked(false);

        if ( dialogCache.isImageScheduledForDeletion() )
        {
            Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_menu_camera);
            File imageFile = new File(productService.getProductImagePath(dto.getId()));
            imageFile.delete();
            dto.setThumbnailBitmap(bitmap);
            dto.setDefaultImage(true);
        }

        productService.saveOrUpdate(dto, cache.getListId());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if ( requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK )
        {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get(CameraActivity.THUMBNAIL_KEY);

            dialogCache.getProductImage().setImageBitmap(imageBitmap);
            dto.setThumbnailBitmap(imageBitmap);
            dto.setDefaultImage(false);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch ( requestCode )
        {
            case MY_PERMISSIONS_REQUEST_USE_CAMERA:
            {
                if ( grantResults.length > 0
                        && grantResults[ 0 ] == PackageManager.PERMISSION_GRANTED )
                {
                    startImageCaptureAction();
                }
                return;
            }
        }
    }

    private void startImageCaptureAction()
    {
        // dto must be saved first in order to have an id.
        // id is needed to generate a unique file name for the image
        String productName = String.valueOf(dialogCache.getProductName().getText());
        if ( StringUtils.isEmpty(productName) ){
            String newProductName = getResources().getString(R.string.new_product);
            productName = newProductName;
        }
        boolean newProductAdded = dto.getId() == null;
        resetState = true && newProductAdded;
        saveUserInput(productName);
        saveConfirmed = false;

        dialogCache.setImageScheduledForDeletion(false);
        Intent takePictureIntent = new Intent(cache.getActivity(), CameraActivity.class);
        takePictureIntent.putExtra(ProductsActivity.PRODUCT_ID_KEY, dto.getId());
        takePictureIntent.putExtra(ProductsActivity.PRODUCT_NAME, dto.getProductName());
        this.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }

    private void setupAutoCompleteLists(AutoCompleteLists autoCompleteLists)
    {
        String[] productsArray = autoCompleteLists.getProductsArray();
        ArrayAdapter<String> productNamesAdapter = new ArrayAdapter<>(getActivity(), R.layout.pfa_lists, productsArray);
        dialogCache.getProductName().setAdapter(productNamesAdapter);

        String[] storesArray = autoCompleteLists.getStoresArray();
        ArrayAdapter<String> storesAdapter = new ArrayAdapter<>(getActivity(), R.layout.pfa_lists, storesArray);
        dialogCache.getCustomStore().setAdapter(storesAdapter);

        String[] categoryArray = autoCompleteLists.getCategoryArray();
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getActivity(), R.layout.pfa_lists, categoryArray);
        dialogCache.getCategory().setAdapter(categoryAdapter);
    }
}
