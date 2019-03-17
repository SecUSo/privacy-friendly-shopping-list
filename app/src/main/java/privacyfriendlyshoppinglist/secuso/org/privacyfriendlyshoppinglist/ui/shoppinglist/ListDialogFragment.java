package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist;

import android.app.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.*;
import org.joda.time.DateTime;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.DateUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.MessageUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.StringUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.ShoppingListService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.MainActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.ShoppingListActivityCache;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductsActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.settings.SettingsKeys;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist.listeners.ListsDialogFocusListener;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist.reminder.ReminderReceiver;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist.reminder.ReminderSchedulingService;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Chris on 09.07.2016.
 */
public class ListDialogFragment extends DialogFragment
{

    private ShoppingListActivityCache cache;
    private ListItem item;
    private ShoppingListService shoppingListService;
    private Calendar currentDate;
    private int year, month, day, hour, minute;
    private static boolean editDialog;
    private static boolean opened;
    private ListDialogCache dialogCache;

    public static ListDialogFragment newEditInstance(ListItem item, ShoppingListActivityCache cache)
    {
        editDialog = true;
        ListDialogFragment dialogFragment = getListDialogFragment(item, cache);
        return dialogFragment;
    }

    public static ListDialogFragment newAddInstance(ListItem item, ShoppingListActivityCache cache)
    {
        editDialog = false;
        ListDialogFragment dialogFragment = getListDialogFragment(item, cache);
        return dialogFragment;
    }

    private static ListDialogFragment getListDialogFragment(ListItem item, ShoppingListActivityCache cache)
    {
        ListDialogFragment dialogFragment = new ListDialogFragment();
        dialogFragment.setCache(cache);
        dialogFragment.setItem(item);
        return dialogFragment;
    }

    public void setCache(ShoppingListActivityCache cache)
    {
        this.cache = cache;
    }

    public void setItem(ListItem item)
    {
        this.item = item;
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
        AbstractInstanceFactory instanceFactory = new InstanceFactory(cache.getActivity().getApplicationContext());
        shoppingListService = (ShoppingListService) instanceFactory.createInstance(ShoppingListService.class);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogColourful);
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.shopping_list_dialog, null);
        dialogCache = new ListDialogCache(v);

        dialogCache.getStatisticsSwitch().setChecked(item.isStatisticEnabled());

        dialogCache.getStatisticsSwitch().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                if ( dialogCache.getStatisticsSwitch().isChecked() )
                {
                    MessageUtils.showToast(getActivity(), R.string.pref_statistics_toast_on, Toast.LENGTH_LONG);
                }
                else
                {
                    MessageUtils.showToast(getActivity(), R.string.pref_statistics_toast_off, Toast.LENGTH_SHORT);
                }
            }
        });

        if ( editDialog )
        {
            dialogCache.getTitleTextView().setText(getActivity().getResources().getString(R.string.list_name_edit));
            dialogCache.getStatisticsSwitch().setChecked(item.isStatisticEnabled());
        }
        else
        {
            dialogCache.getTitleTextView().setText(getActivity().getResources().getString(R.string.list_name_new));
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cache.getActivity());
            boolean statisticsEnabled = sharedPreferences.getBoolean(SettingsKeys.STATISTICS_ENABLED, false);
            dialogCache.getStatisticsSwitch().setChecked(statisticsEnabled);
        }

        if ( !StringUtils.isEmpty(item.getDeadlineDate()) )
        {
            dialogCache.getDeadlineExpansionButton().setVisibility(View.VISIBLE);
            dialogCache.getDeadlineExpansionButton().setImageResource(R.drawable.ic_keyboard_arrow_down_white_48sp);

            dialogCache.getCheckBox().setChecked(true);
            String language;
            String datePatternInput;
            String datePattern;
            String timePatternInput;
            String timePattern;
            language = cache.getActivity().getResources().getString(R.string.language);
            datePatternInput = cache.getActivity().getResources().getString(R.string.date_short_pattern);
            datePattern = cache.getActivity().getResources().getString(R.string.date_short_pattern);
            timePatternInput = cache.getActivity().getResources().getString(R.string.time_pattern);
            timePattern = cache.getActivity().getResources().getString(R.string.time_pattern);

            dialogCache.getDateTextView().setText(DateUtils.getFormattedDateString(item.getDeadlineDate(), datePatternInput, datePattern, language));
            dialogCache.getTimeTextView().setText(DateUtils.getFormattedDateString(item.getDeadlineTime(), timePatternInput, timePattern, language));
        }


        dialogCache.getDeadlineExpansionButton().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if ( dialogCache.getDeadlineLayout().getVisibility() == View.VISIBLE )
                {
                    dialogCache.getDeadlineLayout().setVisibility(View.GONE);
                    dialogCache.getDeadlineExpansionButton().setImageResource(R.drawable.ic_keyboard_arrow_down_white_48sp);
                }
                else
                {
                    dialogCache.getDeadlineLayout().setVisibility(View.VISIBLE);
                    dialogCache.getDeadlineExpansionButton().setImageResource(R.drawable.ic_keyboard_arrow_up_white_48sp);
                }
            }
        });

        dialogCache.getCheckBox().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                currentDate = new GregorianCalendar();
                String language;
                String datePattern;
                String timePattern;
                language = cache.getActivity().getResources().getString(R.string.language);
                datePattern = cache.getActivity().getResources().getString(R.string.date_short_pattern);
                timePattern = cache.getActivity().getResources().getString(R.string.time_pattern);

                if ( dialogCache.getCheckBox().isChecked() )
                {
                    dialogCache.getDeadlineExpansionButton().setVisibility(View.VISIBLE);
                    dialogCache.getDeadlineExpansionButton().setImageResource(R.drawable.ic_keyboard_arrow_up_white_48sp);
                    dialogCache.getDeadlineLayout().setVisibility(View.VISIBLE);
                    dialogCache.getDateTextView().setText(DateUtils.getDateAsString(currentDate.getTimeInMillis(), datePattern, language));
                    dialogCache.getTimeTextView().setText(DateUtils.getDateAsString(currentDate.getTimeInMillis(), timePattern, language));
                }
                else
                {
                    dialogCache.getReminderSwitch().setChecked(false);
                    dialogCache.getDeadlineExpansionButton().setVisibility(View.INVISIBLE);
                    dialogCache.getDateTextView().setText("");
                    dialogCache.getTimeTextView().setText("");
                    dialogCache.getDeadlineLayout().setVisibility(View.GONE);
                }
            }
        });

        currentDate = new GregorianCalendar();
        year = currentDate.get(Calendar.YEAR);
        month = currentDate.get(Calendar.MONTH);
        day = currentDate.get(Calendar.DAY_OF_MONTH);
        hour = currentDate.get(Calendar.HOUR_OF_DAY);
        minute = currentDate.get(Calendar.MINUTE);

        dialogCache.getDateLayout().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DatePickerDialog datePickerDialog;
                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(final DatePicker dp, final int currentYear,
                                          final int currentMonth, final int currentDay)
                    {
                        currentDate.set(currentYear, currentMonth, currentDay,
                                currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE));

                        dialogCache.getDateTextView().setText(DateUtils.getDateAsString(currentDate.getTimeInMillis(), cache.getActivity().getResources().getString(R.string.date_short_pattern), cache.getActivity().getResources().getString(R.string.language)));

                    }
                }, year, month, day);
                datePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getResources().getString(R.string.okay), datePickerDialog);
                datePickerDialog.show();
            }
        });


        dialogCache.getTimeLayout().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(final TimePicker tp, final int currentHour, final int currentMinute)
                    {
                        currentDate.set(currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH),
                                currentDate.get(Calendar.DAY_OF_MONTH), currentHour, currentMinute);

                        dialogCache.getTimeTextView().setText(DateUtils.getDateAsString(currentDate.getTimeInMillis(), cache.getActivity().getResources().getString(R.string.time_pattern), cache.getActivity().getResources().getString(R.string.language)));

                    }
                }, hour, minute, true);
                timePickerDialog.setButton(TimePickerDialog.BUTTON_POSITIVE, getResources().getString(R.string.okay), timePickerDialog);
                timePickerDialog.show();
            }
        });

        dialogCache.getListNameText().setText(item.getListName());
        dialogCache.getListNotes().setText(item.getNotes());
        String[] priorityList = cache.getActivity().getResources().getStringArray(R.array.shopping_list_priority_spinner);
        ArrayAdapter<String> prioritySpinnerAdapter = new ArrayAdapter<String>(getActivity(), R.layout.pfa_lists, priorityList)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {
                View v = super.getView(position, convertView, parent);
                if ( position == getCount() )
                {
                    ((TextView) v.findViewById(android.R.id.text1)).setText("");
                    ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount()));
                }
                return v;
            }

            @Override
            public int getCount()
            {
                return super.getCount() - 1; // you dont display last item. It is used as hint.
            }
        };

        dialogCache.getPrioritySpinner().setAdapter(prioritySpinnerAdapter);
        dialogCache.getPrioritySpinner().setSelection(Integer.valueOf(item.getPriority()));

        String[] reminderItemList = cache.getActivity().getResources().getStringArray(R.array.shopping_list_reminder_spinner);
        ArrayAdapter<String> reminderSpinnerAdapter = new ArrayAdapter<String>(getActivity(), R.layout.pfa_lists, reminderItemList)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {
                View v = super.getView(position, convertView, parent);
                if ( position == getCount() )
                {
                    ((TextView) v.findViewById(android.R.id.text1)).setText("");
                    ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount()));
                }
                return v;
            }

            @Override
            public int getCount()
            {
                return super.getCount() - 1; // you dont display last item. It is used as hint.
            }
        };

        dialogCache.getReminderSpinner().setAdapter(reminderSpinnerAdapter);

        String reminderCount = item.getReminderCount();
        String reminderUnit = item.getReminderUnit();
        boolean reminderEnabled = reminderCount != null;
        if ( reminderEnabled )
        {
            dialogCache.getReminderText().setText(reminderCount);
            dialogCache.getReminderSpinner().setSelection(Integer.valueOf(reminderUnit));
            dialogCache.getReminderLayout().setVisibility(View.VISIBLE);
        }
        else
        {
            dialogCache.getReminderLayout().setVisibility(View.GONE);
        }
        final SwitchCompat reminderSwitch = dialogCache.getReminderSwitch();
        reminderSwitch.setChecked(reminderEnabled);

        reminderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                if ( reminderSwitch.isChecked() )
                {
                    if ( !PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean(SettingsKeys.NOTIFICATIONS_ENABLED, true) )
                    {
                        MessageUtils.showToast(getContext(), R.string.notification_setting_status, Toast.LENGTH_LONG);
                    }
                    dialogCache.getReminderLayout().setVisibility(View.VISIBLE);
                }
                else
                {
                    dialogCache.getReminderLayout().setVisibility(View.GONE);
                }
            }
        });


        builder.setView(v);

        dialogCache.getListNameText().setOnFocusChangeListener(new ListsDialogFocusListener(dialogCache));
        dialogCache.getListNotes().setOnFocusChangeListener(new ListsDialogFocusListener(dialogCache));

        builder.setPositiveButton(cache.getActivity().getResources().getString(R.string.okay), new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                item.setListName(dialogCache.getListNameText().getText().toString());
                item.setNotes(dialogCache.getListNotes().getText().toString());

                item.setPriority(String.valueOf(dialogCache.getPrioritySpinner().getSelectedItemPosition()));

                item.setDeadlineDate((String) dialogCache.getDateTextView().getText());
                item.setDeadlineTime((String) dialogCache.getTimeTextView().getText());

                item.setReminderUnit(String.valueOf(dialogCache.getReminderSpinner().getSelectedItemPosition()));
                item.setReminderCount(dialogCache.getReminderText().getText().toString());
                item.setReminderEnabled(reminderSwitch.isChecked());
                item.setStatisticEnabled(dialogCache.getStatisticsSwitch().isChecked());

                String message = getResources().getString(R.string.notification_message, item.getListName(), item.getDeadlineDate() + " " + item.getDeadlineTime());

                shoppingListService.saveOrUpdate(item)
                        .doOnCompleted(() ->
                        {
                            // the reminder feature must happen after save, because the list id is necessary for the notification
                            if ( reminderSwitch.isChecked() )
                            {
                                DateTime reminderTime = shoppingListService.getReminderDate(item);
                                ReminderReceiver alarm = new ReminderReceiver();

                                Intent intent = new Intent(cache.getActivity(), ReminderSchedulingService.class);
                                intent.putExtra(ReminderSchedulingService.MESSAGE_TEXT, message);
                                intent.putExtra(MainActivity.LIST_ID_KEY, item.getId());
                                alarm.setAlarm(cache.getActivity(), intent, reminderTime.getMillis(), item.getId());
                            }
                            else
                            {
                                // delete notification if exists
                                ReminderReceiver alarm = new ReminderReceiver();
                                Intent intent = new Intent(cache.getActivity(), ReminderSchedulingService.class);
                                alarm.cancelAlarm(cache.getActivity(), intent, item.getId());
                            }

                            if ( !editDialog )
                            {
                                // go to new list
                                Intent intent = new Intent(cache.getActivity(), ProductsActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra(MainActivity.LIST_ID_KEY, item.getId());
                                cache.getActivity().startActivity(intent);
                            }
                            else
                            {
                                // update lists
                                MainActivity mainActivity = (MainActivity) cache.getActivity();
                                mainActivity.updateListView();
                            }
                        })
                        .doOnError(Throwable::printStackTrace)
                        .subscribe();
            }
        });

        builder.setNegativeButton(cache.getActivity().getResources().getString(R.string.cancel), new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
            }
        });

        AlertDialog dialog = builder.create();
        if ( !editDialog )
        {
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        return dialog;
    }

}
