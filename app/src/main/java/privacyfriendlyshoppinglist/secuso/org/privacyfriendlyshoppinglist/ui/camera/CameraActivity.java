package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.camera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.CameraUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.ProductService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductsActivity;
import rx.schedulers.Schedulers;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.08.16 creation date
 */
public class CameraActivity extends Activity
{
    public static final String THUMBNAIL_KEY = "thumbnail";

    private static final int THUMBNAIL_SIZE = 200;
    private Camera mCamera;
    private CameraPreview mPreview;
    private String productId;
    private int cameraOrientation;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_preview);

        mCamera = getCameraAndSetupOrientation();
        setContinuousAutoFocus();

        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        Bundle extras = getIntent().getExtras();
        productId = (String) extras.get(ProductsActivity.PRODUCT_ID_KEY);

        Button captureButton = (Button) findViewById(R.id.button_capture);
        captureButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mCamera.takePicture(null, null, mPicture);
            }
        });
    }

    private void setContinuousAutoFocus()
    {
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        mCamera.setParameters(parameters);
    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback()
    {
        @Override
        public void onPictureTaken(byte[] data, Camera camera)
        {
            Bitmap imageBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            Bitmap thumbnailBitmap = Bitmap.createScaledBitmap(imageBitmap, THUMBNAIL_SIZE, THUMBNAIL_SIZE, true);
            Bitmap rotatedThumbnailBitmap = CameraUtils.getRotatedBitmap(thumbnailBitmap, cameraOrientation);

            String productImagePath = getImagePath();
            CameraUtils.saveBitmap(imageBitmap, productImagePath, cameraOrientation)
                    .subscribeOn(Schedulers.newThread())
                    .subscribe();

            Intent resultIntent = new Intent();
            resultIntent.putExtra(THUMBNAIL_KEY, rotatedThumbnailBitmap);
            setResult(RESULT_OK, resultIntent);
            finish();
        }
    };

    private String getImagePath()
    {
        AbstractInstanceFactory instanceFactory = new InstanceFactory(getApplicationContext());
        ProductService productService = (ProductService) instanceFactory.createInstance(ProductService.class);
        return productService.getProductImagePath(productId);
    }


    public Camera getCameraAndSetupOrientation()
    {
        Camera c = null;
        try
        {
            c = Camera.open();
            cameraOrientation = getWindowManager().getDefaultDisplay().getRotation();
            int orientation = CameraUtils.getRotationAdjustment(cameraOrientation);
            c.setDisplayOrientation(orientation);
        }
        catch ( Exception e )
        {
            // no camera available
        }
        return c;
    }
}
