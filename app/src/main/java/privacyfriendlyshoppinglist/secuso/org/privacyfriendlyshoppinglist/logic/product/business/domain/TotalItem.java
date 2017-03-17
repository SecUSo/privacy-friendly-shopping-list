package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain;

import android.content.Context;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 21.07.16 creation date
 */
public class TotalItem
{
    private static final String DETAIL_SEPARATOR = ": ";
    private static final String NEW_LINE = "\n";
    private static final String SPACE = " ";

    private String totalAmount;
    private String checkedAmount;
    private boolean equalsZero;
    private int nrProducts;

    public String getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    public String getCheckedAmount()
    {
        return checkedAmount;
    }

    public void setCheckedAmount(String checkedAmount)
    {
        this.checkedAmount = checkedAmount;
    }

    public boolean isEqualsZero()
    {
        return equalsZero;
    }

    public void setEqualsZero(boolean equalsZero)
    {
        this.equalsZero = equalsZero;
    }

    public void setNrProducts(int nrProducts)
    {
        this.nrProducts = nrProducts;
    }

    public int getNrProducts()
    {
        return nrProducts;
    }

    public String getInfo(String currency, Context context)
    {
        String nrItemsLabel = context.getResources().getString(R.string.nr_items);
        String totalAmountLabel = context.getResources().getString(R.string.total_list_amount);

        StringBuilder sb = new StringBuilder();
        sb.append(nrItemsLabel);
        sb.append(DETAIL_SEPARATOR);
        sb.append(this.getNrProducts());
        sb.append(NEW_LINE);
        sb.append(totalAmountLabel);
        sb.append(DETAIL_SEPARATOR);
        sb.append(this.getTotalAmount());
        sb.append(SPACE);
        sb.append(currency);
        sb.append(NEW_LINE);
        sb.append(NEW_LINE);

        return sb.toString();
    }
}
