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

    public boolean isSelected()
    {
        return selected;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    @Override
    public boolean equals(Object o)
    {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        if ( !super.equals(o) ) return false;

        ProductDto dto = (ProductDto) o;

        if ( isSelected() != dto.isSelected() ) return false;
        if ( getProductId() != null ? !getProductId().equals(dto.getProductId()) : dto.getProductId() != null )
            return false;
        if ( getQuantity() != null ? !getQuantity().equals(dto.getQuantity()) : dto.getQuantity() != null )
            return false;
        if ( getQuantityPurchased() != null ? !getQuantityPurchased().equals(dto.getQuantityPurchased()) : dto.getQuantityPurchased() != null )
            return false;
        if ( getProductNotes() != null ? !getProductNotes().equals(dto.getProductNotes()) : dto.getProductNotes() != null )
            return false;
        if ( getProductStore() != null ? !getProductStore().equals(dto.getProductStore()) : dto.getProductStore() != null )
            return false;
        if ( getProductPrice() != null ? !getProductPrice().equals(dto.getProductPrice()) : dto.getProductPrice() != null )
            return false;
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
        int result = super.hashCode();
        result = 31 * result + (getProductId() != null ? getProductId().hashCode() : 0);
        result = 31 * result + (getQuantity() != null ? getQuantity().hashCode() : 0);
        result = 31 * result + (getQuantityPurchased() != null ? getQuantityPurchased().hashCode() : 0);
        result = 31 * result + (getProductNotes() != null ? getProductNotes().hashCode() : 0);
        result = 31 * result + (getProductStore() != null ? getProductStore().hashCode() : 0);
        result = 31 * result + (getProductPrice() != null ? getProductPrice().hashCode() : 0);
        result = 31 * result + (isSelected() ? 1 : 0);
        return result;
    }
}
