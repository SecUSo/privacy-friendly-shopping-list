package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.domain;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 23.07.16 creation date
 */
public class StatisticEntryItem
{
    private String recordDate;

    private String productName;

    private String quantity;

    private String unitPrice;

    private String productCategory;

    private String productStore;

    public String getRecordDate()
    {
        return recordDate;
    }

    public void setRecordDate(String recordDate)
    {
        this.recordDate = recordDate;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getQuantity()
    {
        return quantity;
    }

    public void setQuantity(String quantity)
    {
        this.quantity = quantity;
    }

    public String getUnitPrice()
    {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice)
    {
        this.unitPrice = unitPrice;
    }

    public String getProductCategory()
    {
        return productCategory;
    }

    public void setProductCategory(String productCategory)
    {
        this.productCategory = productCategory;
    }

    public String getProductStore()
    {
        return productStore;
    }

    public void setProductStore(String productStore)
    {
        this.productStore = productStore;
    }

    @Override
    public String toString()
    {
        return "StatisticEntryItem{" +
                "recordDate='" + recordDate + '\'' +
                ", productName='" + productName + '\'' +
                ", quantity='" + quantity + '\'' +
                ", unitPrice='" + unitPrice + '\'' +
                ", productCategory='" + productCategory + '\'' +
                ", productStore='" + productStore + '\'' +
                '}';
    }
}
