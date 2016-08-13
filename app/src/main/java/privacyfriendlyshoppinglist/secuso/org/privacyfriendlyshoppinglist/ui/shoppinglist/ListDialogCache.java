package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist;

import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.*;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 30.07.16 creation date
 */
public class ListDialogCache
{

    private EditText listNameText;
    private EditText listNotes;
    private EditText reminderText;
    private CheckBox checkBox;
    private LinearLayout deadlineLayout;
    private LinearLayout dateLayout;
    private LinearLayout timeLayout;
    private LinearLayout reminderLayout;
    private TextView dateTextView;
    private TextView timeTextView;
    private ImageView deadlineExpansionButton;
    private Spinner prioritySpinner;
    private Spinner reminderSpinner;
    private TextView titleTextView;
    private SwitchCompat statisticsSwitch;

    public ListDialogCache(View rootview)
    {
        prioritySpinner = (Spinner) rootview.findViewById(R.id.priority_spinner);
        reminderSpinner = (Spinner) rootview.findViewById(R.id.reminder_spinner);
        listNameText = (EditText) rootview.findViewById(R.id.list_name);
        reminderText = (EditText) rootview.findViewById(R.id.edittext_reminder);
        listNotes = (EditText) rootview.findViewById(R.id.list_notes);
        checkBox = (CheckBox) rootview.findViewById(R.id.list_dialog_checkbox);
        deadlineExpansionButton = (ImageView) rootview.findViewById(R.id.expand_button_list);
        deadlineLayout = (LinearLayout) rootview.findViewById(R.id.set_deadline_layout);
        dateLayout = (LinearLayout) rootview.findViewById(R.id.set_deadline_date);
        timeLayout = (LinearLayout) rootview.findViewById(R.id.set_deadline_time);
        reminderLayout = (LinearLayout) rootview.findViewById(R.id.set_deadline_reminder);
        dateTextView = (TextView) rootview.findViewById(R.id.set_date_view);
        timeTextView = (TextView) rootview.findViewById(R.id.set_time_view);
        titleTextView = (TextView) rootview.findViewById(R.id.dialog_title);
        statisticsSwitch = (SwitchCompat) rootview.findViewById(R.id.switch_statistics);
    }

    public EditText getListNameText()
    {
        return listNameText;
    }

    public EditText getListNotes()
    {
        return listNotes;
    }

    public CheckBox getCheckBox()
    {
        return checkBox;
    }

    public LinearLayout getDeadlineLayout()
    {
        return deadlineLayout;
    }

    public LinearLayout getDateLayout()
    {
        return dateLayout;
    }

    public LinearLayout getTimeLayout()
    {
        return timeLayout;
    }

    public LinearLayout getReminderLayout()
    {
        return reminderLayout;
    }

    public TextView getDateTextView()
    {
        return dateTextView;
    }

    public TextView getTimeTextView()
    {
        return timeTextView;
    }

    public ImageView getDeadlineExpansionButton()
    {
        return deadlineExpansionButton;
    }

    public Spinner getPrioritySpinner()
    {
        return prioritySpinner;
    }

    public Spinner getReminderSpinner()
    {
        return reminderSpinner;
    }

    public TextView getTitleTextView()
    {
        return titleTextView;
    }

    public EditText getReminderText()
    {
        return reminderText;
    }

    public SwitchCompat getStatisticsSwitch()
    {
        return statisticsSwitch;
    }
}
