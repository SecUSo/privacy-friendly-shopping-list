package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 21.07.16 creation date
 */
public class TotalDto
{
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
}
