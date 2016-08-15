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
import android.widget.ImageView;
import android.widget.TextView;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductDto;

/**
 * Created by Chris on 11.08.2016.
 */
public class ImageViewerDialog extends DialogFragment
{
    private static boolean opened;
    private ProductDialogCache dialogCache;
    private ProductDto dto;

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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.product_image_viewer, null);
        Button closeButton = (Button) rootView.findViewById(R.id.close);
        ImageButton deleteButton = (ImageButton) rootView.findViewById(R.id.delete);
        ImageView productImage = (ImageView) rootView.findViewById(R.id.product_image_in_viewer);

        if ( dialogCache == null )
        {
            deleteButton.setVisibility(View.GONE);
        }

        TextView titleTextView = (TextView) rootView.findViewById(R.id.title);

        String listDialogTitle = getContext().getResources().getString(R.string.product_as_title, dto.getProductName());
        titleTextView.setText(listDialogTitle);

        productImage.setImageBitmap(dto.getBitmapImage());

        closeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });

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


        builder.setView(rootView);
        return builder.create();
    }

}
