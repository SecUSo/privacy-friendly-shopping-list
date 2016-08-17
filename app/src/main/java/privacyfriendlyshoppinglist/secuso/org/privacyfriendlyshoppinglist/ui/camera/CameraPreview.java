package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.camera;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.08.16 creation date
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback
{
    private SurfaceHolder mHolder;
    private Camera mCamera;

    public CameraPreview(Context context, Camera camera)
    {
        super(context);
        mCamera = camera;
        mHolder = getHolder();
        mHolder.addCallback(this);

    }

    public CameraPreview(Context context)
    {
        super(context);
    }

    public CameraPreview(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public CameraPreview(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        try
        {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        }
        catch ( IOException e )
        {
            // ignore
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        if ( mHolder.getSurface() == null )
        {
            return;
        }

        try
        {
            mCamera.stopPreview();
        }
        catch ( Exception e )
        {

        }

        try
        {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        }
        catch ( Exception e )
        {

        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {

    }
}
