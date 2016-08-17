package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.view.Surface;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.logger.PFALogger;
import rx.Observable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 17.08.16 creation date
 */
public abstract class CameraUtils
{
    private static Set<String> imagesBeingSaved = new HashSet<>();

    public static Bitmap getRotatedBitmap(Bitmap imageBitmap, int screenRotation)
    {
        int rotationAdjustment = getRotationAdjustment(screenRotation);
        Matrix matrix = new Matrix();
        matrix.postRotate(rotationAdjustment);
        return Bitmap.createBitmap(imageBitmap, 0, 0, imageBitmap.getWidth(), imageBitmap.getHeight(), matrix, true);
    }

    public static int getRotationAdjustment(int screenRotation)
    {
        int degrees = 0;
        switch ( screenRotation )
        {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int cameraOrientation = getCameraOrientation();
        return (cameraOrientation - degrees + 360) % 360;
    }

    public static Observable<Void> saveBitmap(Bitmap bitmap, String imagePath, int rotation)
    {
        Observable<Void> observable = Observable
                .create(subscriber ->
                {
                    subscriber.onNext(saveBitmapSync(bitmap, imagePath, rotation));
                    subscriber.onCompleted();
                });
        return observable;
    }

    public static int getCameraOrientation()
    {
        Camera.CameraInfo cameraInfo = getCameraInfo();
        return cameraInfo.orientation;
    }

    private static Camera.CameraInfo getCameraInfo()
    {
        Camera.CameraInfo info = new Camera.CameraInfo();
        for ( int i = 0; i < Camera.getNumberOfCameras(); i++ )
        {
            Camera.getCameraInfo(i, info);
            if ( info.facing == Camera.CameraInfo.CAMERA_FACING_BACK )
            {
                break;
            }
        }
        return info;
    }

    private static Void saveBitmapSync(Bitmap bitmap, String imagePath, int rotation)
    {
        addImageToSet(imagePath);
        File imageFile = new File(imagePath);
        FileOutputStream fos = null;
        {
            try
            {
                Bitmap rotatedBitmap = getRotatedBitmap(bitmap, rotation);
                fos = new FileOutputStream(imageFile);
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            }
            catch ( Exception e )
            {

            }
            finally
            {
                try
                {
                    removeImageFromSet(imagePath);
                    fos.close();
                }
                catch ( IOException e1 )
                {
                    PFALogger.error("CameraUtils", "saveBitmap", e1);
                }
            }
        }
        return null;
    }

    private synchronized static void removeImageFromSet(String imagePath)
    {
        imagesBeingSaved.remove(imagePath);
    }

    private synchronized static void addImageToSet(String imagePath)
    {
        imagesBeingSaved.add(imagePath);
    }

    public synchronized static boolean isSavingImage(String imagePath)
    {
        return imagesBeingSaved.contains(imagePath);
    }
}
