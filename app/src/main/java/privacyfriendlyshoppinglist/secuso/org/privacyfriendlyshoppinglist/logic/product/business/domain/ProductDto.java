package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 11.06.16 creation date
 */
public class ProductDto extends ProductTemplateDto
{
    public enum ErrorFieldName
    {
        PRODUCT_NAME_EMPTY,
        PRODUCT_NAME_TOO_LONG
    }

    // product fields
    private String productId;

    private String quantity;

    private String quantityPurchased;

    private String productNotes;

    private String productStore;

    private String productPrice;

    private String purchasedDate;

    private boolean selected;

    public String getProductId()
    {
        return productId;
    }

    public void setProductId(String productId)
    {
        this.productId = productId;
    }

    public String getQuantity()
    {
        return quantity;
    }

    public void setQuantity(String quantity)
    {
        this.quantity = quantity;
    }

    public String getQuantityPurchased()
    {
        return quantityPurchased;
    }

    public void setQuantityPurchased(String quantityPurchased)
    {
        this.quantityPurchased = quantityPurchased;
    }

    public String getProductNotes()
    {
        return productNotes;
    }

    public void setProductNotes(String productNotes)
    {
        this.productNotes = productNotes;
    }

    public String getProductStore()
    {
        return productStore;
    }

    public void setProductStore(String productStore)
    {
        this.productStore = productStore;
    }

    public String getProductPrice()
    {
        return productPrice;
    }

    public void setProductPrice(String productPrice)
    {
        this.productPrice = productPrice;
    }

    public String getPurchasedDate()
    {
        return purchasedDate;
    }

    public void setPurchasedDate(String purchasedDate)
    {
        this.purchasedDate = purchasedDate;
    }

    public boolean isSelected()
    {
        return selected;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }
}
