package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.entity;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.AbstractEntity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence.entity.ProductItemEntity;

import java.util.Date;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 09.06.16 creation date
 */
public class ShoppingListEntity extends AbstractEntity
{
    @DatabaseField(canBeNull = false)
    private String listName;

    @DatabaseField
    private String priority;

    @DatabaseField
    private Integer icon;

    @DatabaseField
    private Date deadline;

    @DatabaseField
    private String notes;

    @ForeignCollectionField
    private ForeignCollection<ProductItemEntity> products;

    public String getListName()
    {
        return listName;
    }

    public void setListName(String listName)
    {
        this.listName = listName;
    }

    public String getPriority()
    {
        return priority;
    }

    public void setPriority(String priority)
    {
        this.priority = priority;
    }

    public Integer getIcon()
    {
        return icon;
    }

    public void setIcon(Integer icon)
    {
        this.icon = icon;
    }

    public Date getDeadline()
    {
        return deadline;
    }

    public void setDeadline(Date deadline)
    {
        this.deadline = deadline;
    }

    public String getNotes()
    {
        return notes;
    }

    public void setNotes(String notes)
    {
        this.notes = notes;
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
        return "ShoppingListEntity{" +
                "listName='" + listName + '\'' +
                ", priority='" + priority + '\'' +
                ", icon=" + icon +
                ", deadline=" + deadline +
                ", notes='" + notes + '\'' +
                ", products=" + products +
                '}';
    }
}
