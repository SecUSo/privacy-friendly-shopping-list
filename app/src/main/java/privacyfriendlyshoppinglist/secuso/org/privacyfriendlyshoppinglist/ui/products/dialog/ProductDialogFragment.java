package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.StringUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.ProductService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.AutoCompleteLists;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductActivityCache;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductsActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.dialog.listeners.onFocusListener.ProductDialogFocusListener;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.dialog.listeners.price.PriceInputFilter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import java.io.File;

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

    private static boolean editDialog;

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
        View v = inflater.inflate(R.layout.product_dialog, null);

        dialogCache = new ProductDialogCache(v);

        dialogCache.getExpandableLayout().setVisibility(View.GONE);

        dialogCache.getProductName().setText(dto.getProductName());
        dialogCache.getProductNotes().setText(dto.getProductNotes());
        dialogCache.getQuantity().setText(dto.getQuantity());
        dialogCache.getPrice().setText(dto.getProductPrice());
        dialogCache.getCategory().setText(dto.getProductCategory());
        dialogCache.getCustomStore().setText(dto.getProductStore());

        if ( editDialog )
        {
            dialogCache.getTitleTextView().setText(getActivity().getResources().getString(R.string.product_name_edit));
        }
        else
        {
            dialogCache.getTitleTextView().setText(getActivity().getResources().getString(R.string.product_name_new));
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



        dialogCache.getCameraIcon().setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                String imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/picture.jpg";
                File imageFile = new File(imageFilePath);
                Uri imageFileUri = Uri.fromFile(imageFile); // convert path to Uri

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                takePictureIntent.setClipData(ClipData.newRawUri(null, imageFileUri));

                if ( takePictureIntent.resolveActivity(cache.getActivity().getPackageManager()) != null )
                {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }

            }

        });

        dialogCache.getPrice().setFilters(new InputFilter[]{new PriceInputFilter(dialogCache)});


        dialogCache.getProductNotes().setOnFocusChangeListener(new ProductDialogFocusListener(dialogCache));
        dialogCache.getProductName().setOnFocusChangeListener(new ProductDialogFocusListener(dialogCache));
        dialogCache.getQuantity().setOnFocusChangeListener(new ProductDialogFocusListener(dialogCache));
        dialogCache.getPrice().setOnFocusChangeListener(new ProductDialogFocusListener(dialogCache));

        Observable<AutoCompleteLists> rxAutoCompleteLists = productService.getAutoCompleteListsObservable();

        AutoCompleteLists autoCompleteLists = new AutoCompleteLists();
        rxAutoCompleteLists
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        result -> result.copyTo(autoCompleteLists),
                        Throwable::printStackTrace,
                        () -> setupAutoCompleteLists(autoCompleteLists));


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
                if ( StringUtils.isEmpty(String.valueOf(dialogCache.getProductName().getText())) )
                {
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), R.string.alert_missing_product_name, Toast.LENGTH_LONG);
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

                    productService.saveOrUpdate(dto, cache.getListId());
                    ProductsActivity productsActivity = (ProductsActivity) cache.getActivity();
                    productsActivity.updateListView();

                    dialog.dismiss();
                }
            }
        });
        return dialog;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        String imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/picture.jpg";

        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK )
        {
//            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//            thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//            File destination = new File(Environment.getExternalStorageDirectory(),
//                    System.currentTimeMillis() + ".jpg");
//            FileOutputStream fo;
//            try {
//                destination.createNewFile();
//                fo = new FileOutputStream(destination);
//                fo.write(bytes.toByteArray());
//                fo.close();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            dialogCache.getCameraIcon().setImageBitmap(thumbnail);

            BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
            bmpFactoryOptions.inJustDecodeBounds = false;

            //imageFilePath image path which you pass with intent
            Bitmap bmp = BitmapFactory.decodeFile(imageFilePath, bmpFactoryOptions);
            dialogCache.getCameraIcon().setImageBitmap(bmp);


//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            dialogCache.getProductImage().setImageBitmap(imageBitmap);
        }
    }
}
