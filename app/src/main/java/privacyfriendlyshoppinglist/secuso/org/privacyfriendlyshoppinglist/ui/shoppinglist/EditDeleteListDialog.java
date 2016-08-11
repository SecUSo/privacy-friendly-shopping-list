package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.ShoppingListService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.ShoppingListActivityCache;

import java.util.Calendar;

/**
 * Created by Chris on 11.08.2016.
 */
public class EditDeleteListDialog extends DialogFragment
{

    private ShoppingListActivityCache cache;
    private ListDto dto;
    private ShoppingListService shoppingListService;
    private Calendar currentDate;
    private int year, month, day, hour, minute;
    private ListDialogCache dialogCache;


    public static EditDeleteListDialog newEditDeleteInstance(ListDto dto, ShoppingListActivityCache cache)
    {

        EditDeleteListDialog dialogFragment = getEditDeleteFragment(dto, cache);
        return dialogFragment;
    }


    private static EditDeleteListDialog getEditDeleteFragment(ListDto dto, ShoppingListActivityCache cache)
    {
        EditDeleteListDialog dialogFragment = new EditDeleteListDialog();
        dialogFragment.setCache(cache);
        dialogFragment.setDto(dto);
        AbstractInstanceFactory instanceFactory = new InstanceFactory(cache.getActivity().getApplicationContext());
        ShoppingListService shoppingListService = (ShoppingListService) instanceFactory.createInstance(ShoppingListService.class);
        dialogFragment.setShoppingListService(shoppingListService);
        return dialogFragment;
    }

    public void setCache(ShoppingListActivityCache cache)
    {
        this.cache = cache;
    }

    public void setDto(ListDto dto)
    {
        this.dto = dto;
    }

    public void setShoppingListService(ShoppingListService shoppingListService)
    {
        this.shoppingListService = shoppingListService;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.edit_dialog_message)
                .setPositiveButton(R.string.edit, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {

                        DialogFragment productFragement = ListDialogFragment.newEditInstance(dto, cache);
                        productFragement.show(cache.getActivity().getSupportFragmentManager(), "Liste");

                    }
                })
                .setNegativeButton(R.string.delete, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {


                    }
                });

        return builder.create();
    }

}