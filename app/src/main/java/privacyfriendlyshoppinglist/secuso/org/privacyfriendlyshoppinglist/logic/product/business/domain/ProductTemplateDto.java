package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain;

import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.business.AbstractDto;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 17.07.16 creation date
 */
public class ProductTemplateDto extends AbstractDto
{
    // template fields
    private String productName;

    private String productCategory;

    private String historyCount;

    private String defaultNotes;

    private String defaultStore;

    private String lastTimePurchased;

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getProductCategory()
    {
        return productCategory;
    }

    public void setProductCategory(String productCategory)
    {
        this.productCategory = productCategory;
    }

    public String getHistoryCount()
    {
        return historyCount;
    }

    public void setHistoryCount(String historyCount)
    {
        this.historyCount = historyCount;
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

    public String getLastTimePurchased()
    {
        return lastTimePurchased;
    }

    public void setLastTimePurchased(String lastTimePurchased)
    {
        this.lastTimePurchased = lastTimePurchased;
    }

    @Override
    public boolean equals(Object o)
    {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        ProductTemplateDto dto = (ProductTemplateDto) o;

        if ( getProductName() != null ? !getProductName().equals(dto.getProductName()) : dto.getProductName() != null )
            return false;
        if ( getProductCategory() != null ? !getProductCategory().equals(dto.getProductCategory()) : dto.getProductCategory() != null )
            return false;
        if ( getHistoryCount() != null ? !getHistoryCount().equals(dto.getHistoryCount()) : dto.getHistoryCount() != null )
            return false;
        if ( getDefaultNotes() != null ? !getDefaultNotes().equals(dto.getDefaultNotes()) : dto.getDefaultNotes() != null )
            return false;
        if ( getDefaultStore() != null ? !getDefaultStore().equals(dto.getDefaultStore()) : dto.getDefaultStore() != null )
            return false;
        return getLastTimePurchased() != null ? getLastTimePurchased().equals(dto.getLastTimePurchased()) : dto.getLastTimePurchased() == null;

    }

    @Override
    public int hashCode()
    {
        int result = getProductName() != null ? getProductName().hashCode() : 0;
        result = 31 * result + (getProductCategory() != null ? getProductCategory().hashCode() : 0);
        result = 31 * result + (getHistoryCount() != null ? getHistoryCount().hashCode() : 0);
        result = 31 * result + (getDefaultNotes() != null ? getDefaultNotes().hashCode() : 0);
        result = 31 * result + (getDefaultStore() != null ? getDefaultStore().hashCode() : 0);
        result = 31 * result + (getLastTimePurchased() != null ? getLastTimePurchased().hashCode() : 0);
        return result;
    }
}
