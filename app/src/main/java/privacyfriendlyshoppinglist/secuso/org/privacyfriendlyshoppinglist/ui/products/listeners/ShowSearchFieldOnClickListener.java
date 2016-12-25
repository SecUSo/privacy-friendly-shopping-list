package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.listeners;

import android.app.Activity;
import android.support.design.widget.TextInputLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.StringUtils;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 31.08.16 creation date
 */
public class ShowSearchFieldOnClickListener implements MenuItem.OnMenuItemClickListener
{
    private Activity activity;

    public ShowSearchFieldOnClickListener(Activity activity)
    {
        this.activity = activity;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        TextInputLayout searchLayout = (TextInputLayout) activity.findViewById(R.id.search_input_layout);
        AutoCompleteTextView searchText = (AutoCompleteTextView) activity.findViewById(R.id.search_input_text);
        ImageButton cancel = (ImageButton) activity.findViewById(R.id.cancel_search);
        searchLayout.setVisibility(View.VISIBLE);
        cancel.setVisibility(View.VISIBLE);
        if ( searchText.requestFocus() )
        {
            showKeyboard();
        }
        AutoCompleteTextView searchAutoCompleteTextView = (AutoCompleteTextView) activity.findViewById(R.id.search_input_text);
        searchAutoCompleteTextView.setText(StringUtils.EMPTY);
        return true;
    }

    private void showKeyboard()
    {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
}
