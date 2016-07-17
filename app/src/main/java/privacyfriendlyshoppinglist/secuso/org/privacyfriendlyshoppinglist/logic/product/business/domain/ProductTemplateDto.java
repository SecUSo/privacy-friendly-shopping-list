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
}
