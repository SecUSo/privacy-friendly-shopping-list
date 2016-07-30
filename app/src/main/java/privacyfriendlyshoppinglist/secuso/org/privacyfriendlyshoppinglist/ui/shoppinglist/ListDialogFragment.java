package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist;

import android.app.*;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.DateUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.StringUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.ShoppingListService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.MainActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.ShoppingListActivityCache;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Chris on 09.07.2016.
 */
public class ListDialogFragment extends DialogFragment
{

    private ShoppingListActivityCache cache;
    private ListDto dto;
    private ShoppingListService shoppingListService;

    private EditText listEditText;
    private EditText listNotes;
    private CheckBox checkBox;
    private LinearLayout deadlineLayout;
    private LinearLayout dateLayout;
    private LinearLayout timeLayout;
    private LinearLayout reminderLayout;
    private Calendar currentDate;
    private int year, month, day, hour, minute;
    private String deadlineDateTime;
    private TextView setDateTextView;
    private TextView setTimeTextView;
    private ImageButton deadlineExpensionButton;
    private static boolean edited;


    public static ListDialogFragment newEditInstance(ListDto dto, ShoppingListActivityCache cache)
    {
        edited = true;
        ListDialogFragment dialogFragment = getListDialogFragment(dto, cache);
        return dialogFragment;
    }

    public static ListDialogFragment newAddInstance(ListDto dto, ShoppingListActivityCache cache)
    {
        edited = false;
        ListDialogFragment dialogFragment = getListDialogFragment(dto, cache);
        return dialogFragment;
    }

    private static ListDialogFragment getListDialogFragment(ListDto dto, ShoppingListActivityCache cache)
    {
        ListDialogFragment dialogFragment = new ListDialogFragment();
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
        // Read DTO contents and activity from cache

        Activity activity = cache.getActivity();
        String priority = dto.getPriority();


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.shopping_list_dialog, null);

        Spinner prioritySpinner = (Spinner) v.findViewById(R.id.priority_spinner);
        Spinner reminderSpinner = (Spinner) v.findViewById(R.id.reminder_spinner);
        listEditText = (EditText) v.findViewById(R.id.list_name);
        listNotes = (EditText) v.findViewById(R.id.list_notes);
        checkBox = (CheckBox) v.findViewById(R.id.list_dialog_checkbox);
        deadlineExpensionButton = (ImageButton) v.findViewById(R.id.expand_button_list);
        deadlineLayout = (LinearLayout) v.findViewById(R.id.set_deadline_layout);
        dateLayout = (LinearLayout) v.findViewById(R.id.set_deadline_date);
        timeLayout = (LinearLayout) v.findViewById(R.id.set_deadline_time);
        reminderLayout = (LinearLayout) v.findViewById(R.id.set_deadline_reminder);

        setDateTextView = (TextView) v.findViewById(R.id.set_date_view);
        setTimeTextView = (TextView) v.findViewById(R.id.set_time_view);

        if ( edited )
        {
            listEditText.setHint(R.string.list_name_edit);
        }

        if ( dto.isSelected() )
        {
            deadlineExpensionButton.setVisibility(View.VISIBLE);
            deadlineExpensionButton.setImageResource(R.drawable.expander_ic_maximized);
        }

        if ( StringUtils.isEmpty(dto.getDeadlineDate()) )
        {
            deadlineLayout.setVisibility(View.GONE);
        }

        else
        {
            deadlineLayout.setVisibility(View.VISIBLE);
            checkBox.setChecked(true);

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

            setDateTextView.setText(DateUtils.getFormattedDateString(dto.getDeadlineDate(), datePatternInput, datePattern, language));
            setTimeTextView.setText(DateUtils.getFormattedDateString(dto.getDeadlineTime(), timePatternInput, timePattern, language));
        }


        deadlineExpensionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if ( deadlineLayout.getVisibility() == View.VISIBLE )
                {
                    deadlineLayout.setVisibility(View.GONE);
                    deadlineExpensionButton.setImageResource(R.drawable.expander_ic_minimized);
                }
                else
                {
                    deadlineLayout.setVisibility(View.VISIBLE);
                    deadlineExpensionButton.setImageResource(R.drawable.expander_ic_maximized);
                }
            }
        });

        checkBox.setOnClickListener(new View.OnClickListener()
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

                if ( checkBox.isChecked() )
                {
                    deadlineExpensionButton.setVisibility(View.VISIBLE);
                    deadlineExpensionButton.setImageResource(R.drawable.expander_ic_maximized);
                    deadlineLayout.setVisibility(View.VISIBLE);
                    setDateTextView.setText(DateUtils.getDateAsString(currentDate.getTimeInMillis(), datePattern, language));
                    setTimeTextView.setText(DateUtils.getDateAsString(currentDate.getTimeInMillis(), timePattern, language));
                }
                else
                {
                    deadlineExpensionButton.setVisibility(View.GONE);
                    setDateTextView.setText("");
                    setTimeTextView.setText("");
                    deadlineLayout.setVisibility(View.GONE);
                }
            }
        });

        currentDate = new GregorianCalendar();
        year = currentDate.get(Calendar.YEAR);
        month = currentDate.get(Calendar.MONTH);
        day = currentDate.get(Calendar.DAY_OF_MONTH);
        hour = currentDate.get(Calendar.HOUR_OF_DAY);
        minute = currentDate.get(Calendar.MINUTE);

        dateLayout.setOnClickListener(new View.OnClickListener()
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

                        setDateTextView.setText(DateUtils.getDateAsString(currentDate.getTimeInMillis(), cache.getActivity().getResources().getString(R.string.date_short_pattern), cache.getActivity().getResources().getString(R.string.language)));

                    }
                }, year, month, day);
                datePickerDialog.setTitle("Set Date:");
                datePickerDialog.show();
            }
        });


        timeLayout.setOnClickListener(new View.OnClickListener()
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

                        setTimeTextView.setText(DateUtils.getDateAsString(currentDate.getTimeInMillis(), cache.getActivity().getResources().getString(R.string.time_pattern), cache.getActivity().getResources().getString(R.string.language)));

                    }
                }, hour, minute, true);
                timePickerDialog.setTitle("Set Time: ");
                timePickerDialog.show();
            }
        });

        reminderLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        });

        listEditText.setText(dto.getListName());
        listNotes.setText(dto.getNotes());
        String[] priorityList = cache.getActivity().getResources().getStringArray(R.array.shopping_list_priority_spinner);
        ArrayAdapter<String> prioritySpinnerAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner, priorityList)
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

        prioritySpinner.setAdapter(prioritySpinnerAdapter);
        prioritySpinner.setSelection(Integer.valueOf(dto.getPriority()));

        String[] reminderItemList = cache.getActivity().getResources().getStringArray(R.array.shopping_list_reminder_spinner);
        ArrayAdapter<String> reminderSpinnerAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner, reminderItemList)
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

        reminderSpinner.setAdapter(reminderSpinnerAdapter);
        //reminderSpinner.setSelection(Integer.valueOf(dto.getPriority()));

        builder.setView(v);

        builder.setPositiveButton(cache.getActivity().getResources().getString(R.string.okay), new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                //saveListName("storedListName", listEditText.getText().toString() );
                dto.setListName(listEditText.getText().toString());
                dto.setNotes(listNotes.getText().toString());

                //TODO Set priority to be Integer
                dto.setPriority(String.valueOf(prioritySpinner.getSelectedItemPosition()));

                dto.setDeadlineDate((String) setDateTextView.getText());
                dto.setDeadlineTime((String) setTimeTextView.getText());

                shoppingListService.saveOrUpdate(dto);
                MainActivity mainActivity = (MainActivity) cache.getActivity();
                mainActivity.updateListView();
            }
        });

        builder.setNegativeButton(cache.getActivity().getResources().getString(R.string.cancel), new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
            }
        });

        return builder.create();
    }
}
