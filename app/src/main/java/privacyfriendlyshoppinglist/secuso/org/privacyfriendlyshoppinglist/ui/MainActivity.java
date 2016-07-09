package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppingList.ShoppingListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 17.05.16 creation date
 */
public class MainActivity extends BaseActivity
{
    @Override
    protected final void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        setupRecyclerView(recyclerView);

        WelcomeDialog welcomeDialog = new WelcomeDialog();
        welcomeDialog.show(getFragmentManager(), "WelcomeDialog");

        overridePendingTransition(0, 0);

    }

    private void setupRecyclerView(RecyclerView recyclerView)
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ShoppingListAdapter shoppingListAdapter = new ShoppingListAdapter(getDummyItemList());
        recyclerView.setAdapter(shoppingListAdapter);
    }

    private List<String> getDummyItemList()
    {
        List<String> itemList = new ArrayList<>();
        for ( int i = 0; i < 20; i++ )
        {
            itemList.add("Item " + i);
        }
        return itemList;
    }

    @Override
    protected int getNavigationDrawerID()
    {
        return R.id.nav_example;
    }

    public static class WelcomeDialog extends DialogFragment
    {

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
            builder.setView(i.inflate(R.layout.welcome_dialog, null));
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setTitle(getActivity().getString(R.string.welcome));
            builder.setPositiveButton(getActivity().getString(R.string.okay), null);
            builder.setNegativeButton(getActivity().getString(R.string.viewhelp), new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    ((MainActivity) getActivity()).goToNavigationItem(R.id.nav_help);
                }
            });

            return builder.create();
        }
    }
}
