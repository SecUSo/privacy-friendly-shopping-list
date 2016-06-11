package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain;

import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.business.AbstractDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductDto;

import java.util.Date;
import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 11.06.16 creation date
 */
public class ListDto extends AbstractDto
{

    public enum VariableName
    {
        ID, LIST_NAME, PRIORITY, ICON, DEADLINE, NOTES
    }

    private String listName;
    private String priority;
    private Integer icon;
    private Date deadline;
    private String notes;
    private List<ProductDto> products;

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

    public List<ProductDto> getProducts()
    {
        return products;
    }

    public void setProducts(List<ProductDto> products)
    {
        this.products = products;
    }

    @Override
    public String toString()
    {
        return "ListDto{" +
                "listName='" + listName + '\'' +
                ", priority='" + priority + '\'' +
                ", icon=" + icon +
                ", deadline=" + deadline +
                ", notes='" + notes + '\'' +
                ", products=" + products +
                '}';
    }
}
