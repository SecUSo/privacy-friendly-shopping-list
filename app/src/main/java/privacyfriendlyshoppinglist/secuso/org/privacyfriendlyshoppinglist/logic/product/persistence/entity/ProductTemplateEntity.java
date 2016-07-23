package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence.entity;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.AbstractEntity;

import java.util.Date;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 09.06.16 creation date
 */
public class ProductTemplateEntity extends AbstractEntity
{
    @DatabaseField(canBeNull = false)
    private String productName;

    @DatabaseField
    private String category;

    @DatabaseField
    private Integer historyCount;

    @DatabaseField
    private Date lastTimePurchased;

    @DatabaseField
    private String defaultNotes;

    @DatabaseField
    private String defaultStore;

    @DatabaseField
    private Integer defaultIcon;

    @ForeignCollectionField
    private ForeignCollection<ProductItemEntity> products;

    public ProductTemplateEntity()
    {// SETUP_PERSISTENCE: needed for ORMLite
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public Integer getHistoryCount()
    {
        return historyCount;
    }

    public void setHistoryCount(Integer historyCount)
    {
        this.historyCount = historyCount;
    }

    public Date getLastTimePurchased()
    {
        return lastTimePurchased;
    }

    public void setLastTimePurchased(Date lastTimePurchased)
    {
        this.lastTimePurchased = lastTimePurchased;
    }

    public String getDefaultNotes()
    {
        return defaultNotes;
    }

    public void setDefaultNotes(String defaultNotes)
    {
        this.defaultNotes = defaultNotes;
    }

    public String getDefaultStore()
    {
        return defaultStore;
    }

    public void setDefaultStore(String defaultStore)
    {
        this.defaultStore = defaultStore;
    }

    public Integer getDefaultIcon()
    {
        return defaultIcon;
    }

    public void setDefaultIcon(Integer defaultIcon)
    {
        this.defaultIcon = defaultIcon;
    }

    public ForeignCollection<ProductItemEntity> getProducts()
    {
        return products;
    }

    public void setProducts(ForeignCollection<ProductItemEntity> products)
    {
        this.products = products;
    }

    @Override
    public String toString()
    {
        return "ProductTemplateEntity{" +
                "productName='" + productName + '\'' +
                ", category='" + category + '\'' +
                ", historyCount=" + historyCount +
                ", lastTimePurchased=" + lastTimePurchased +
                ", defaultNotes='" + defaultNotes + '\'' +
                ", defaultStore='" + defaultStore + '\'' +
                ", defaultIcon=" + defaultIcon +
                ", products=" + products +
                '}';
    }
}
