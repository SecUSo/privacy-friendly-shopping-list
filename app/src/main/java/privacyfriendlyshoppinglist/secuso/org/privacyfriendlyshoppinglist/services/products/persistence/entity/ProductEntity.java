package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.services.products.persistence.entity;

import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.AbstractEntity;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.05.16 14:58 creation date
 */
public class ProductEntity extends AbstractEntity
{
    private String productName;

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }
}
