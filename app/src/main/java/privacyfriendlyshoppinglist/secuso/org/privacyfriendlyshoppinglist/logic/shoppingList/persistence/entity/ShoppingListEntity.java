package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.entity;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.AbstractEntity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.products.persistence.entity.ProductEntity;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 05.06.16 creation date
 */
public class ShoppingListEntity extends AbstractEntity
{
    @DatabaseField(canBeNull = false)
    private String listName;

    @ForeignCollectionField(eager = false)
    ForeignCollection<ProductEntity> products;

    public String getListName()
    {
        return listName;
    }

    public ShoppingListEntity setListName(String listName)
    {
        this.listName = listName;
        return this;
    }

    public ForeignCollection<ProductEntity> getProducts()
    {
        return products;
    }

    public ShoppingListEntity setProducts(ForeignCollection<ProductEntity> products)
    {
        this.products = products;
        return this;
    }

    @Override
    public String toString()
    {
        return "ShoppingListEntity{" +
                "listName='" + listName + '\'' +
                ", products=" + products +
                '}';
    }
}
