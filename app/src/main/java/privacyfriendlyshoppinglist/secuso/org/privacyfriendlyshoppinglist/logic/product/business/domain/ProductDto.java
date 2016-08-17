package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.StringUtils;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 11.06.16 creation date
 */
public class ProductDto
{

    public static final String NEW_LINE = "\n";
    private static final String SPACE = " ";
    private static final String COMMA = ",";

    // product fields
    private String productName;

    private String id;

    private String productCategory;

    private String quantity;

    private String productNotes;

    private String productStore;

    private String productPrice;

    private String totalProductPrice;

    private boolean checked;

    private boolean selectedForDeletion;

    private Bitmap thumbnailBitmap;

    private boolean isDefaultImage;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getQuantity()
    {
        return quantity;
    }

    public void setQuantity(String quantity)
    {
        this.quantity = quantity;
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

    public boolean isChecked()
    {
        return checked;
    }

    public void setChecked(boolean checked)
    {
        this.checked = checked;
    }

    public boolean isSelectedForDeletion()
    {
        return selectedForDeletion;
    }

    public void setSelectedForDeletion(boolean selectedForDeletion)
    {
        this.selectedForDeletion = selectedForDeletion;
    }

    public void setTotalProductPrice(String totalProductPrice)
    {
        this.totalProductPrice = totalProductPrice;
    }

    public String getProductCategory()
    {
        return productCategory;
    }

    public void setProductCategory(String productCategory)
    {
        this.productCategory = productCategory;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public Bitmap getThumbnailBitmap()
    {
        return thumbnailBitmap;
    }

    public void setThumbnailBitmap(Bitmap thumbnailBitmap)
    {
        this.thumbnailBitmap = thumbnailBitmap;
    }

    public boolean isDefaultImage()
    {
        return isDefaultImage;
    }

    public void setDefaultImage(boolean defaultImage)
    {
        isDefaultImage = defaultImage;
    }

    public String getSummary(Context context)
    {
        Resources resources = context.getResources();
        String storeLabel = resources.getString(R.string.store_label);
        String categoryLabel = resources.getString(R.string.category_label);

        StringBuilder sb = new StringBuilder();
        boolean withStore = false;
        if ( !StringUtils.isEmpty(getProductStore()) )
        {
            sb.append(storeLabel).append(SPACE).append(getProductStore());
            withStore = true;
        }
        if ( !StringUtils.isEmpty(getProductCategory()) )
        {
            if ( withStore )
            {
                sb.append(COMMA).append(SPACE);
            }
            sb.append(categoryLabel).append(SPACE).append(getProductCategory());
        }
        return sb.toString();
    }

    public String getDetailInfo(Context context)
    {
        Resources resources = context.getResources();
        String quantityDetail = resources.getString(R.string.quantity_detail, quantity);
        String priceDetail = resources.getString(R.string.price_detail, productPrice);
        String totalPriceDetail = resources.getString(R.string.total_price_detail, totalProductPrice);
        String notesDetail = resources.getString(R.string.notes_detail, productNotes);
        String categoryDetail = resources.getString(R.string.category_detail, productCategory);
        String storeDetail = resources.getString(R.string.store_detail, productStore);

        StringBuilder sb = new StringBuilder();
        if ( !StringUtils.isEmpty(quantity) )
        {
            sb.append(quantityDetail);
            sb.append(NEW_LINE);
        }
        if ( !StringUtils.isEmpty(productPrice) )
        {
            sb.append(priceDetail);
            sb.append(NEW_LINE);
        }
        if ( !StringUtils.isEmpty(totalProductPrice) )
        {
            sb.append(totalPriceDetail);
        }

        boolean categoryExists = !StringUtils.isEmpty(productCategory);
        boolean storeExists = !StringUtils.isEmpty(productStore);
        boolean notesExists = !StringUtils.isEmpty(productNotes);
        if ( categoryExists || storeExists || notesExists )
        {
            sb.append(NEW_LINE);
        }

        if ( categoryExists )
        {
            sb.append(NEW_LINE);
            sb.append(categoryDetail);
        }
        if ( storeExists )
        {
            sb.append(NEW_LINE);
            sb.append(storeDetail);
        }
        if ( notesExists )
        {
            sb.append(NEW_LINE);
            sb.append(notesDetail);
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o)
    {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        ProductDto dto = (ProductDto) o;

        if ( isChecked() != dto.isChecked() ) return false;
        if ( isSelectedForDeletion() != dto.isSelectedForDeletion() ) return false;
        if ( getProductName() != null ? !getProductName().equals(dto.getProductName()) : dto.getProductName() != null )
            return false;
        if ( getId() != null ? !getId().equals(dto.getId()) : dto.getId() != null ) return false;
        if ( getProductCategory() != null ? !getProductCategory().equals(dto.getProductCategory()) : dto.getProductCategory() != null )
            return false;
        if ( getQuantity() != null ? !getQuantity().equals(dto.getQuantity()) : dto.getQuantity() != null )
            return false;
        if ( getProductNotes() != null ? !getProductNotes().equals(dto.getProductNotes()) : dto.getProductNotes() != null )
            return false;
        if ( getProductStore() != null ? !getProductStore().equals(dto.getProductStore()) : dto.getProductStore() != null )
            return false;
        return getProductPrice() != null ? getProductPrice().equals(dto.getProductPrice()) : dto.getProductPrice() == null;

    }

    @Override
    public int hashCode()
    {
        int result = getProductName() != null ? getProductName().hashCode() : 0;
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        result = 31 * result + (getProductCategory() != null ? getProductCategory().hashCode() : 0);
        result = 31 * result + (getQuantity() != null ? getQuantity().hashCode() : 0);
        result = 31 * result + (getProductNotes() != null ? getProductNotes().hashCode() : 0);
        result = 31 * result + (getProductStore() != null ? getProductStore().hashCode() : 0);
        result = 31 * result + (getProductPrice() != null ? getProductPrice().hashCode() : 0);
        result = 31 * result + (isChecked() ? 1 : 0);
        result = 31 * result + (isSelectedForDeletion() ? 1 : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return "ProductDto{" +
                "productName='" + productName + '\'' +
                ", id='" + id + '\'' +
                ", productCategory='" + productCategory + '\'' +
                ", quantity='" + quantity + '\'' +
                ", productNotes='" + productNotes + '\'' +
                ", productStore='" + productStore + '\'' +
                ", productPrice='" + productPrice + '\'' +
                ", totalProductPrice='" + totalProductPrice + '\'' +
                ", checked=" + checked +
                ", selectedForDeletion=" + selectedForDeletion +
                ", thumbnailBitmap=" + thumbnailBitmap +
                ", isDefaultImage=" + isDefaultImage +
                '}';
    }
}
