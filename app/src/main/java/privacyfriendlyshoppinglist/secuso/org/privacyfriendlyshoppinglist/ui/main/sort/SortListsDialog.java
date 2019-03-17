package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.sort;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.comparators.PFAComparators;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.ShoppingListService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.MainActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.settings.SettingsKeys;

import java.util.ArrayList;
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogColourful);
        View rootView = i.inflate(R.layout.sort_lists_dialog, null);

        SortListsDialogCache cache = new SortListsDialogCache(rootView);
        setupPreviosOptions(cache);

        builder.setView(rootView);
        builder.setTitle(getActivity().getString(R.string.sort_options));
        builder.setNegativeButton(getActivity().getString(R.string.cancel), null);
        builder.setPositiveButton(getActivity().getString(R.string.okay), new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                MainActivity host = (MainActivity) activity;
                AbstractInstanceFactory instanceFactory = new InstanceFactory(host.getApplicationContext());
                ShoppingListService shoppingListService = (ShoppingListService) instanceFactory.createInstance(ShoppingListService.class);

                String criteria = PFAComparators.SORT_BY_NAME;
                if ( cache.getPriority().isChecked() )
                {
                    criteria = PFAComparators.SORT_BY_PRIORITY;
                }
                final String finalCriteria = criteria;
                boolean ascending = cache.getAscending().isChecked();

                List<ListItem> listItems = new ArrayList<>();

                shoppingListService.getAllListItems()
                        .doOnNext(item -> listItems.add(item))
                        .doOnCompleted(() ->
                        {
                            shoppingListService.sortList(listItems, finalCriteria, ascending);
                            host.reorderListView(listItems);
                        })
                        .doOnError(Throwable::printStackTrace)
                        .subscribe();

                // save sort options
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(SettingsKeys.LIST_SORT_BY, criteria);
                editor.putBoolean(SettingsKeys.LIST_SORT_ASCENDING, ascending);
                editor.commit();
            }
        });

        return builder.create();
    }

    private void setupPreviosOptions(SortListsDialogCache cache)
    {
        boolean prevAscending = PreferenceManager.getDefaultSharedPreferences(activity).getBoolean(SettingsKeys.LIST_SORT_ASCENDING, true);
        cache.getAscending().setChecked(prevAscending);
        cache.getDescending().setChecked(!prevAscending);

        String prevCriteria = PreferenceManager.getDefaultSharedPreferences(activity).getString(SettingsKeys.LIST_SORT_BY, PFAComparators.SORT_BY_NAME);
        switch ( prevCriteria )
        {
            case PFAComparators.SORT_BY_PRIORITY:
                cache.getPriority().setChecked(true);
                break;
            default:
                cache.getName().setChecked(true);
        }
    }
}
