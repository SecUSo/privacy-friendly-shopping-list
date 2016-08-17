package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.camera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.ProductService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductsActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.08.16 creation date
 */
public class CameraActivity extends Activity
{
    public static final String THUMBNAIL_KEY = "thumbnail";
    public static final String IMAGE_PATH_KEY = "imagePath";

    private Camera mCamera;
    private CameraPreview mPreview;
    private String productId;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_preview);

        mCamera = getCameraInstance();

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

    private Camera.PictureCallback mPicture = new Camera.PictureCallback()
    {
        @Override
        public void onPictureTaken(byte[] data, Camera camera)
        {
            AbstractInstanceFactory instanceFactory = new InstanceFactory(getApplicationContext());
            ProductService productService = (ProductService) instanceFactory.createInstance(ProductService.class);

            File path = new File(productService.getProductImagePath(productId));

            Matrix matrix = new Matrix();
            matrix.postRotate(270);
            Bitmap imageBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            Bitmap rotatedBitmap = Bitmap.createBitmap(imageBitmap, 0, 0, imageBitmap.getWidth(), imageBitmap.getHeight(), matrix, true);
            Bitmap thumbnailBitmap = Bitmap.createScaledBitmap(rotatedBitmap, 200, 200, true);

            FileOutputStream fos = null;
            {
                try
                {
                    fos = new FileOutputStream(path);
                    // Use the compress method on the BitMap object to write image to the OutputStream
                    rotatedBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                }
                catch ( Exception e )
                {
                    e.printStackTrace();
                }
                finally
                {
                    try
                    {
                        fos.close();
                    }
                    catch ( IOException e )
                    {
                        e.printStackTrace();
                    }
                }
            }

            Intent resultIntent = new Intent();
            resultIntent.putExtra(THUMBNAIL_KEY, thumbnailBitmap);
            resultIntent.putExtra(IMAGE_PATH_KEY, path.getAbsolutePath());
            setResult(RESULT_OK, resultIntent);
            finish();
        }
    };


    public Camera getCameraInstance()
    {
        Camera c = null;
        try
        {
            c = Camera.open();
        }
        catch ( Exception e )
        {
            // no camera available
        }
        return c;
    }
}
