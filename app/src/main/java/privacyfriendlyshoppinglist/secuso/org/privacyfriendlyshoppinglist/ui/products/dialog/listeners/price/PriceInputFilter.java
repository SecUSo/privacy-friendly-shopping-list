package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.dialog.listeners.price;

import android.text.InputFilter;
import android.text.Spanned;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.dialog.ProductDialogCache;

import java.util.Locale;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 30.07.16 creation date
 */
public class PriceInputFilter implements InputFilter
{
    public static final String PERIOD = ".";
    public static final String COMMA = ",";
    private String decimalSeparator;
    private static final String INVALID_CHAR_REGEX = "[^0-9\\.\\,]+]";

    public PriceInputFilter(ProductDialogCache dialogCache)
    {
        Locale locale = dialogCache.getPrice().getContext().getResources().getConfiguration().locale;
        if ("US".equals(locale.getCountry()))
        {
            decimalSeparator = PERIOD;
        } else {
            decimalSeparator = dialogCache.getPrice().getResources().getString(R.string.number_decimal_separator);
        }
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend)
    {
        String cleanedString = source.toString();
        cleanedString = cleanedString.replaceAll(INVALID_CHAR_REGEX, "");
        cleanedString = cleanedString.replace(PERIOD, decimalSeparator);
        cleanedString = cleanedString.replace(COMMA, decimalSeparator);
        return cleanedString;
    }
}