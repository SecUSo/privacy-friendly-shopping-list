package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.listeners;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.StringUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductActivityCache;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductsActivity;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 31.08.16 creation date
 */
public class CancelSearchOnClick implements View.OnClickListener
{
    private static ProductActivityCache cache;

    public CancelSearchOnClick(ProductActivityCache cache)
    {
        this.cache = cache;
    }

    @Override
    public void onClick(View v)
    {
        performClick();
    }

    public static void performClick()
    {
        cache.getSearchTextInputLayout().setVisibility(View.GONE);
        cache.getCancelSarchButton().setVisibility(View.GONE);
        cache.getSearchAutoCompleteTextView().setText(StringUtils.EMPTY);
        ProductsActivity host = (ProductsActivity) cache.getActivity();
        host.updateListView();
        hideKeyboard();
    }

    private static void hideKeyboard()
    {
        AppCompatActivity activity = cache.getActivity();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(cache.getSearchAutoCompleteTextView().getWindowToken(), 0);
    }
}
