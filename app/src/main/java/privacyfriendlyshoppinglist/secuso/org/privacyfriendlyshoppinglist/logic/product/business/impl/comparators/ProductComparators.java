package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.impl.comparators;

import android.content.Context;
import android.content.res.Resources;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.comparators.PFAComparators;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.StringUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductItem;

import java.util.Comparator;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 22.07.16 creation date
 */
public class ProductComparators extends PFAComparators
{
    public static Comparator<ProductItem> getNameComparator(boolean ascending)
    {
        int ascendingFactor = getAscendingFactor(ascending);
        return (lhs, rhs) -> (lhs.getProductName().compareTo(rhs.getProductName()) * ascendingFactor);
    }

    public static Comparator<ProductItem> getStoreComparator(boolean ascending)
    {
        int ascendingFactor = getAscendingFactor(ascending);
        return (lhs, rhs) -> (lhs.getProductStore().compareTo(rhs.getProductStore()) * ascendingFactor);
    }

    public static Comparator<ProductItem> getCategoryComparator(boolean ascending)
    {
        int ascendingFactor = getAscendingFactor(ascending);
        return (lhs, rhs) -> (lhs.getProductCategory().compareTo(rhs.getProductCategory()) * ascendingFactor);
    }

    public static Comparator<ProductItem> getQuantityCompartor(boolean ascending)
    {
        int ascendingFactor = getAscendingFactor(ascending);
        return (lhs, rhs) ->
        {
            Integer quantity1 = Integer.valueOf(lhs.getQuantity());
            Integer quantity2 = Integer.valueOf(rhs.getQuantity());
            return (quantity1.compareTo(quantity2) * ascendingFactor);
        };
    }


    public static Comparator<ProductItem> getPriceComparator(boolean ascending, Context context)
    {
        Resources resources = context.getResources();
        String format = resources.getString(R.string.number_format_2_decimals);

        int ascendingFactor = getAscendingFactor(ascending);
        return (lhs, rhs) ->
        {
            Double quantity1 = StringUtils.getStringAsDouble(lhs.getProductPrice(), format);
            Double quantity2 = StringUtils.getStringAsDouble(rhs.getProductPrice(), format);
            return (quantity1.compareTo(quantity2) * ascendingFactor);
        };
    }


}
