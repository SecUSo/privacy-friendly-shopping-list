package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 21.07.16 creation date
 */
public class TotalDto
{
    String totalAmount;
    String checkedAmount;
    boolean equalsZero;

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
}
