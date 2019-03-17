package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.CameraUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.MessageUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.ProductService;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 03.09.16 creation date
 */
public class PhotoPreviewActivity extends AppCompatActivity
{
    private static final long WAIT_INTERVAL_MILLISECONDS = 200L;

    private String productId;
    private String productName;
    private Bitmap fullSizeBitmap;
    private ProductService productService;
    private boolean calledFromDialog;

    @Override
    protected final void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_photo_preview_activity);

        Bundle extras = getIntent().getExtras();
        productId = (String) extras.get(ProductsActivity.PRODUCT_ID_KEY);
        productName = (String) extras.get(ProductsActivity.PRODUCT_NAME);
        calledFromDialog = (boolean) extras.get(ProductsActivity.FROM_DIALOG);
        setTitle(productName);

        SubsamplingScaleImageView productImage = (SubsamplingScaleImageView) findViewById(R.id.product_image_in_viewer);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        AbstractInstanceFactory instanceFactory = new InstanceFactory(this);
        productService = (ProductService) instanceFactory.createInstance(ProductService.class);

        String listDialogTitle = getResources().getString(R.string.product_as_title, productName);
        setTitle(listDialogTitle);

        loadImageFromStorage(productId)
                .doOnNext(result -> fullSizeBitmap = result)
                .doOnCompleted(() ->
                {
                    progressBar.setVisibility(View.GONE);
                    productImage.setImage(ImageSource.bitmap(fullSizeBitmap));
                })
                .doOnError(Throwable::printStackTrace)
                .subscribe();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.photopreview_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        MenuItem deleteItem = menu.findItem(R.id.imageview_delete);
        setupDeleteButton(deleteItem);
        return super.onPrepareOptionsMenu(menu);
    }

    private void setupDeleteButton(MenuItem deleteButton)
    {
        if ( !calledFromDialog )
        {
            deleteButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
            {
                @Override
                public boolean onMenuItemClick(MenuItem item)
                {
                    showConfirmationDialog();
                    return true;
                }
            });
        }
        else
        {
            deleteButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
            {
                @Override
                public boolean onMenuItemClick(MenuItem item)
                {
                    scheduleForDeletion();
                    return true;
                }
            });
        }
    }

    private void scheduleForDeletion()
    {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_menu_camera);
        Intent result = new Intent();
        result.putExtra(ProductsActivity.PHOTO_BITMAP, bitmap);
        result.putExtra(ProductsActivity.SCHEDULED_FOR_DELETION, true);
        setResult(RESULT_OK, result);
        finish();
    }

    private void showConfirmationDialog()
    {
        MessageUtils.showAlertDialog(
                this,
                R.string.delete_confirmation_title,
                R.string.delete_foto_confirmation,
                productName,
                productService.deleteOnlyImage(productId)
                        .doOnCompleted(() ->
                        {
                            setResult(RESULT_OK);
                            finish();
                        })
        );
    }

    private Observable<Bitmap> loadImageFromStorage(String productId)
    {
        Observable observable = Observable
                .defer(() ->
                {
                    try
                    {
                        return Observable.just(loadImageFromStorageSync(productId));
                    }
                    catch ( InterruptedException e )
                    {
                        return Observable.error(e);
                    }
                })
                .doOnError(Throwable::printStackTrace)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private Bitmap loadImageFromStorageSync(String productId) throws InterruptedException
    {
        String productImagePath = productService.getProductImagePath(productId);

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
