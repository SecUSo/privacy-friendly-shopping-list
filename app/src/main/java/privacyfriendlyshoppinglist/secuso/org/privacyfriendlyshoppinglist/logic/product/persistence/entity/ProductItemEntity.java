package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence.entity;

import com.j256.ormlite.field.DatabaseField;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.AbstractEntity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.entity.ShoppingListEntity;

import java.util.Date;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 09.06.16 creation date
 */
public class ProductItemEntity extends AbstractEntity
{
    @DatabaseField(foreign = true, canBeNull = false)
    private ProductTemplateEntity productTemplate;

    @DatabaseField
    private Integer quantity;

    @DatabaseField
    private Integer quantityPurchased;

    @DatabaseField
    private String notes;

    @DatabaseField
    private String store;

    @DatabaseField
    private Double price;

    @DatabaseField
    private Date purchasedDate;

    @DatabaseField
    private Integer icon;

    @DatabaseField
    private Boolean selected;

    @DatabaseField(foreign = true, canBeNull = false)
    private ShoppingListEntity shoppingList;

    public ProductItemEntity()
    {
    }

    public ProductTemplateEntity getProductTemplate()
    {
        return productTemplate;
    }

    public void setProductTemplate(ProductTemplateEntity productTemplate)
    {
        this.productTemplate = productTemplate;
    }

    public Integer getQuantity()
    {
        return quantity;
    }

    public void setQuantity(Integer quantity)
    {
        this.quantity = quantity;
    }

    public Integer getQuantityPurchased()
    {
        return quantityPurchased;
    }

    public void setQuantityPurchased(Integer quantityPurchased)
    {
        this.quantityPurchased = quantityPurchased;
    }

    public String getNotes()
    {
        return notes;
    }

    public void setNotes(String notes)
    {
        this.notes = notes;
    }

    public String getStore()
    {
        return store;
    }

    public void setStore(String store)
    {
        this.store = store;
    }

    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public Date getPurchasedDate()
    {
        return purchasedDate;
    }

    public void setPurchasedDate(Date purchasedDate)
    {
        this.purchasedDate = purchasedDate;
    }

    public Integer getIcon()
    {
        return icon;
    }

    public void setIcon(Integer icon)
    {
        this.icon = icon;
    }

    public Boolean getSelected()
    {
        return selected;
    }

    public void setSelected(Boolean selected)
    {
        this.selected = selected;
    }

    public ShoppingListEntity getShoppingList()
    {
        return shoppingList;
    }

    public void setShoppingList(ShoppingListEntity shoppingList)
    {
        this.shoppingList = shoppingList;
    }

    @Override
    public String toString()
    {
        return "ProductItemEntity{" +
                "productTemplate=" + productTemplate +
                ", quantity=" + quantity +
                ", quantityPurchased=" + quantityPurchased +
                ", notes='" + notes + '\'' +
                ", store='" + store + '\'' +
                ", price=" + price +
                ", purchasedDate=" + purchasedDate +
                ", icon=" + icon +
                ", selected=" + selected +
                ", shoppingList=" + shoppingList +
                '}';
    }
}
