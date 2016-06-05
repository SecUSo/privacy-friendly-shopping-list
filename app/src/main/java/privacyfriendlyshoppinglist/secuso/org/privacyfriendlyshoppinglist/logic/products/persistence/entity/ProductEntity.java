package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.products.persistence.entity;

import com.j256.ormlite.field.DatabaseField;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.AbstractEntity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.entity.ShoppingListEntity;

import java.util.Date;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.05.16 14:58 creation date
 */
public class ProductEntity extends AbstractEntity
{
    @DatabaseField(canBeNull = false)
    private String productName;

    @DatabaseField
    private Double price;

    @DatabaseField
    private String description;

    @DatabaseField
    private String category;

    @DatabaseField
    private String store;

    @DatabaseField
    private Date lastDate;

    @DatabaseField(canBeNull = false, foreign = true)
    private ShoppingListEntity shoppingList;

    // SETUP_PERSISTENCE: needed for ORMLite
    public ProductEntity()
    {
    }

    public String getProductName()
    {
        return productName;
    }

    public ProductEntity setProductName(String productName)
    {
        this.productName = productName;
        return this;
    }

    public Double getPrice()
    {
        return price;
    }

    public ProductEntity setPrice(Double price)
    {
        this.price = price;
        return this;
    }

    public String getDescription()
    {
        return description;
    }

    public ProductEntity setDescription(String description)
    {
        this.description = description;
        return this;
    }

    public String getCategory()
    {
        return category;
    }

    public ProductEntity setCategory(String category)
    {
        this.category = category;
        return this;
    }

    public String getStore()
    {
        return store;
    }

    public ProductEntity setStore(String store)
    {
        this.store = store;
        return this;
    }

    public Date getLastDate()
    {
        return lastDate;
    }

    public ProductEntity setLastDate(Date lastDate)
    {
        this.lastDate = lastDate;
        return this;
    }

    public ProductEntity setShoppingList(ShoppingListEntity shoppingList)
    {
        this.shoppingList = shoppingList;
        return this;
    }

    public ShoppingListEntity getShoppingList()
    {
        return shoppingList;
    }

    @Override
    public String toString()
    {
        return "ProductEntity{" +
                "productName='" + productName + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", store='" + store + '\'' +
                ", lastDate=" + lastDate +
                ", shoppingList=" + shoppingList +
                '}';
    }
}
