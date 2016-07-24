package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.sort;

import android.view.View;
import android.widget.RadioButton;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.07.16 creation date
 */
public class SortListsDialogCache
{
    private RadioButton ascending;
    private RadioButton descending;
    private RadioButton name;
    private RadioButton priority;

    public SortListsDialogCache(View rootview)
    {
        ascending = (RadioButton) rootview.findViewById(R.id.radiobutton_ascending);
        descending = (RadioButton) rootview.findViewById(R.id.radiobutton_descending);
        name = (RadioButton) rootview.findViewById(R.id.radiobutton_name);
        priority = (RadioButton) rootview.findViewById(R.id.radiobutton_priority);
    }

    public RadioButton getAscending()
    {
        return ascending;
    }

    public RadioButton getDescending()
    {
        return descending;
    }

    public RadioButton getName()
    {
        return name;
    }

    public RadioButton getPriority()
    {
        return priority;
    }
}
