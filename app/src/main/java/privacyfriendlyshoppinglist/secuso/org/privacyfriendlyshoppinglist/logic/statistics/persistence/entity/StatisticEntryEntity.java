package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.persistence.entity;

import com.j256.ormlite.field.DatabaseField;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.AbstractEntity;

import java.util.Date;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 23.07.16 creation date
 */
public class StatisticEntryEntity extends AbstractEntity
{
    @DatabaseField
    private Date recordDate;

    @DatabaseField
    private String productName;

    @DatabaseField
    private Integer quantity;

    @DatabaseField
    private Double unitPrice;

    @DatabaseField
    private String productCategory;

    @DatabaseField
    private String productStore;

    public Date getRecordDate()
    {
        return recordDate;
    }

    public void setRecordDate(Date recordDate)
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

    public Integer getQuantity()
    {
        return quantity;
    }

    public void setQuantity(Integer quantity)
    {
        this.quantity = quantity;
    }

    public Double getUnitPrice()
    {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice)
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
        return "StatisticEntryEntity{" +
                "recordDate=" + recordDate +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", productCategory='" + productCategory + '\'' +
                ", productStore='" + productStore + '\'' +
                '}';
    }
}
