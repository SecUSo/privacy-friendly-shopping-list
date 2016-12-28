package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.dialog.listeners.price;


import android.text.InputFilter;
import android.text.Spanned;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.dialog.ProductDialogCache;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 30.07.16 creation date
 */
public class PriceInputFilter implements InputFilter
{
    private static final String PERIOD = ".";
    private static final String COMMA = ",";
    private String decimalSeparator;
    private static final String INVALID_CHAR_REGEX = "[^0-9\\.\\,]+]";

    public PriceInputFilter(ProductDialogCache dialogCache)
    {
        NumberFormat nf = NumberFormat.getInstance();
        if ( nf instanceof DecimalFormat )
        {
            DecimalFormatSymbols symbols = ((DecimalFormat) nf).getDecimalFormatSymbols();
            decimalSeparator = String.valueOf(symbols.getDecimalSeparator());
        }
        else
        {
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