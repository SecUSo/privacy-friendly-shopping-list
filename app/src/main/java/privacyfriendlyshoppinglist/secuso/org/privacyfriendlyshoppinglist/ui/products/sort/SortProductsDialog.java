package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.sort;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 22.07.16 creation date
 */
public class SortProductsDialog extends DialogFragment
{
    private AppCompatActivity activity;

    public static SortProductsDialog newInstance(AppCompatActivity activity)
    {
        SortProductsDialog sortProductsDialog = new SortProductsDialog();
        sortProductsDialog.setActivity(activity);
        return sortProductsDialog;
    }

    public void setActivity(AppCompatActivity activity)
    {
        this.activity = activity;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        LayoutInflater i = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View rootView = i.inflate(R.layout.sort_products_dialog, null);

        SortProductsDialogCache cache = new SortProductsDialogCache(rootView);
        cache.getAscending().setChecked(true);
        cache.getName().setChecked(true);

        builder.setView(rootView);
        builder.setTitle(getActivity().getString(R.string.sort_lists));
        builder.setNegativeButton(getActivity().getString(R.string.cancel), null);
        builder.setPositiveButton(getActivity().getString(R.string.okay), null);

        return builder.create();
    }
}
