package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.sort;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.comparators.PFAComparators;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.ShoppingListService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.MainActivity;

import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.07.16 creation date
 */
public class SortListsDialog extends DialogFragment
{

    private AppCompatActivity activity;

    public static SortListsDialog newInstance(AppCompatActivity activity)
    {
        SortListsDialog sortListsDialog = new SortListsDialog();
        sortListsDialog.setActivity(activity);
        return sortListsDialog;
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
        View rootView = i.inflate(R.layout.sort_lists_dialog, null);

        SortListsDialogCache cache = new SortListsDialogCache(rootView);
        cache.getAscending().setChecked(true);
        cache.getName().setChecked(true);

        builder.setView(rootView);
        builder.setTitle(getActivity().getString(R.string.sort_lists));
        builder.setNegativeButton(getActivity().getString(R.string.cancel), null);
        builder.setPositiveButton(getActivity().getString(R.string.okay), new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                MainActivity host = (MainActivity) activity;
                AbstractInstanceFactory instanceFactory = new InstanceFactory(host.getApplicationContext());
                ShoppingListService shoppingListService = (ShoppingListService) instanceFactory.createInstance(ShoppingListService.class);

                List<ListDto> listDtos = shoppingListService.getAllListDtos();
                String criteria = PFAComparators.SORT_BY_NAME;
                if ( cache.getPriority().isChecked() )
                {
                    criteria = PFAComparators.SORT_BY_PRIORITY;
                }
                shoppingListService.sortList(listDtos, criteria, cache.getAscending().isChecked());
                host.reorderListView(listDtos);
            }
        });

        return builder.create();
    }
}
