package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.MessageUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.NotificationUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.ProductService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.ShoppingListService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.MainActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.ShoppingListActivityCache;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist.reminder.ReminderReceiver;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist.reminder.ReminderSchedulingService;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 11.08.2016.
 */
public class EditDeleteListDialog extends DialogFragment
{

    private ShoppingListActivityCache cache;
    private ListDto dto;
    private ShoppingListService shoppingListService;
    private ProductService productService;


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

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AbstractInstanceFactory instanceFactory = new InstanceFactory(cache.getActivity().getApplicationContext());
        shoppingListService = (ShoppingListService) instanceFactory.createInstance(ShoppingListService.class);
        productService = (ProductService) instanceFactory.createInstance(ProductService.class);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.shopping_list_actions, null);
        Button editButton = (Button) rootView.findViewById(R.id.edit);
        Button duplicateButton = (Button) rootView.findViewById(R.id.duplicate);
        Button resetButton = (Button) rootView.findViewById(R.id.reset);
        Button shareButton = (Button) rootView.findViewById(R.id.share);
        Button deleteButton = (Button) rootView.findViewById(R.id.delete);
        TextView titleTextView = (TextView) rootView.findViewById(R.id.title);

        String listDialogTitle = getContext().getResources().getString(R.string.list_as_title, dto.getListName());
        titleTextView.setText(listDialogTitle);

        editButton.setOnClickListener(getEditOnClickListener());
        duplicateButton.setOnClickListener(getDuplicateOnClickListener());
        deleteButton.setOnClickListener(getDeleteOnClickListener());
        resetButton.setOnClickListener(getResetCheckedItemsOnClickListener());
        shareButton.setOnClickListener(getShareOnClickListener());

        builder.setView(rootView);
        return builder.create();
    }

    private View.OnClickListener getDeleteOnClickListener()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dismiss();
                MessageUtils.showAlertDialog(
                        getContext(),
                        R.string.delete_confirmation_title,
                        R.string.delete_list_confirmation,
                        dto.getListName(),
                        deleteList());
            }
        };
    }

    private View.OnClickListener getEditOnClickListener()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dismiss();
                if ( !ListDialogFragment.isOpened() )
                {
                    DialogFragment productFragment = ListDialogFragment.newEditInstance(dto, cache);
                    productFragment.show(cache.getActivity().getSupportFragmentManager(), "List");
                }
            }
        };
    }

    private View.OnClickListener getShareOnClickListener()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dismiss();
                List<ProductDto> productDtos = new ArrayList<>();
                Context context = getContext();
                productService.getAllProducts(dto.getId())
                        .doOnNext(productDto -> productDtos.add(productDto))
                        .doOnCompleted(() ->
                        {
                            String shareableText = shoppingListService.getShareableText(dto, productDtos);
                            MessageUtils.shareText(context, shareableText, dto.getListName());
                        })
                        .subscribe();

            }
        };
    }

    private View.OnClickListener getDuplicateOnClickListener()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dismiss();
                productService.duplicateProducts(dto.getId())
                        .doOnCompleted(() ->
                        {
                            MainActivity activity = (MainActivity) cache.getActivity();
                            activity.updateListView();
                        })
                        .subscribe();

            }
        };
    }

    private View.OnClickListener getResetCheckedItemsOnClickListener()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dismiss();
                productService.resetCheckedProducts(dto.getId())
                        .doOnCompleted(() ->
                        {
                            MainActivity activity = (MainActivity) cache.getActivity();
                            activity.updateListView();
                        })
                        .subscribe();
            }
        };
    }

    private Observable<Void> deleteList()
    {
        Observable observable = Observable
                .defer(() -> Observable.just(deleteListSync()))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private Void deleteListSync()
    {
        String id = dto.getId();
        shoppingListService.deleteById(id)
                .doOnCompleted(() ->
                {
                    MainActivity activity = (MainActivity) cache.getActivity();
                    activity.updateListView();
                })
                .subscribe();
        productService.deleteAllFromList(id).subscribe();

        // delete notification if any
        NotificationUtils.removeNotification(cache.getActivity(), id);
        ReminderReceiver alarm = new ReminderReceiver();
        Intent intent = new Intent(cache.getActivity(), ReminderSchedulingService.class);
        alarm.cancelAlarm(cache.getActivity(), intent, id);
        return null;
    }

}