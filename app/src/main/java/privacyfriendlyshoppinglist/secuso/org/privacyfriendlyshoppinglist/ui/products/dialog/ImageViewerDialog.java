package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.CameraUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.MessageUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.ProductService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductsActivity;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Chris on 11.08.2016.
 */
public class ImageViewerDialog extends DialogFragment
{
    private static final long WAIT_INTERVAL_MILLISECONDS = 200L;
    private static boolean opened;
    private ProductDialogCache dialogCache;
    private ProductDto dto;
    private Bitmap fullSizeBitmap;
    private ProductService productService;

    public static ImageViewerDialog newInstance(ProductDto dto, ProductDialogCache dialogCache)
    {
        ImageViewerDialog dialogFragment = new ImageViewerDialog();
        dialogFragment.setDialogCache(dialogCache);
        dialogFragment.setDto(dto);
        return dialogFragment;
    }

    public static ImageViewerDialog newViewOnlyInstance(ProductDto dto)
    {
        ImageViewerDialog dialogFragment = new ImageViewerDialog();
        dialogFragment.setDto(dto);
        return dialogFragment;
    }

    public void setDialogCache(ProductDialogCache dialogCache)
    {
        this.dialogCache = dialogCache;
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
    }

    public static boolean isOpened()
    {
        return opened;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppTheme);
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.product_image_viewer, null);

        Button closeButton = (Button) rootView.findViewById(R.id.close);
        ImageButton deleteButton = (ImageButton) rootView.findViewById(R.id.delete);
        SubsamplingScaleImageView productImage = (SubsamplingScaleImageView) rootView.findViewById(R.id.product_image_in_viewer);
        ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);

        AbstractInstanceFactory instanceFactory = new InstanceFactory(getContext());
        productService = (ProductService) instanceFactory.createInstance(ProductService.class);

        setupDeleteButton(deleteButton);

        TextView titleTextView = (TextView) rootView.findViewById(R.id.title);

        String listDialogTitle = getContext().getResources().getString(R.string.product_as_title, dto.getProductName());
        titleTextView.setText(listDialogTitle);

        loadImageFromStorage(dto)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> fullSizeBitmap = result,
                        Throwable::printStackTrace,
                        () ->
                        {
                            progressBar.setVisibility(View.GONE);
                            productImage.setImage(ImageSource.bitmap(fullSizeBitmap));
                        }
                );

        closeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });

        builder.setView(rootView);
        return builder.create();
    }

    private void setupDeleteButton(ImageButton deleteButton)
    {
        if ( dialogCache == null )
        {
            deleteButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    MessageUtils.showAlertDialog(
                            getContext(),
                            R.string.delete_confirmation_title,
                            R.string.delete_foto_confirmation,
                            dto.getProductName(),
                            productService.deleteOnlyImage(dto.getId())
                                    .doOnCompleted(() ->
                                    {
                                        dismiss();
                                        ProductsActivity activity = (ProductsActivity) getActivity();
                                        activity.updateListView();
                                    })
                    );
                }
            });
        }
        else
        {
            deleteButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_menu_camera);
                    dialogCache.getProductImage().setImageBitmap(bitmap);
                    dialogCache.setImageScheduledForDeletion(true);
                    dismiss();
                }
            });
        }
    }

    private Observable<Bitmap> loadImageFromStorage(ProductDto dto)
    {
        Observable<Bitmap> observable = Observable
                .create(subscriber ->
                {
                    try
                    {
                        subscriber.onNext(loadImageFromStorageSync(dto));
                    }
                    catch ( InterruptedException e )
                    {
                        e.printStackTrace();
                    }
                    subscriber.onCompleted();
                });
        return observable;
    }

    private Bitmap loadImageFromStorageSync(ProductDto dto) throws InterruptedException
    {
        String productImagePath = productService.getProductImagePath(dto.getId());

        while ( CameraUtils.isSavingImage(productImagePath) )
        {
            Thread.sleep(WAIT_INTERVAL_MILLISECONDS);
        }

        try
        {
            File imageFile = new File(productImagePath);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(imageFile));
            return bitmap;
        }
        catch ( FileNotFoundException e )
        {
            return null;
        }
    }

}
