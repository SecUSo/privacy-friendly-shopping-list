package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.impl.converter.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.DB;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.StringUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.impl.converter.ProductConverterService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence.entity.ProductItemEntity;

import java.io.ByteArrayOutputStream;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 17.07.16 creation date
 */
public class ProductConverterServiceImpl implements ProductConverterService
{
    private static final int DEFAULT_QUANTITY = 1;
    private static final double DEFAULT_PRICE = 0.0;
    private static final int BITMAP_QUALITY = 100;
    private String priceFormat0;
    private String priceFormat1;
    private String priceFormat2;
    private Context context;

    @Override
    public void setContext(Context context, DB db)
    {
        this.context = context;
        this.priceFormat0 = context.getResources().getString(R.string.number_format_0_decimals);
        this.priceFormat1 = context.getResources().getString(R.string.number_format_1_decimal);
        this.priceFormat2 = context.getResources().getString(R.string.number_format_2_decimals);
    }

    @Override
    public void convertDtoToEntity(ProductDto dto, ProductItemEntity entity)
    {
        entity.setProductName(dto.getProductName());

        if ( !StringUtils.isEmpty(dto.getId()) )
        {
            entity.setId(Long.valueOf(dto.getId()));
        }

        if ( !StringUtils.isEmpty(dto.getQuantity()) )
        {
            entity.setQuantity(Integer.valueOf(dto.getQuantity()));
        }
        else
        {
            entity.setQuantity(DEFAULT_QUANTITY);
        }

        entity.setNotes(dto.getProductNotes());
        entity.setStore(dto.getProductStore());

        String priceString = dto.getProductPrice();
        if ( !StringUtils.isEmpty(priceString) )
        {
            Double price = getStringAsDouble(priceString);
            entity.setPrice(price);
        }
        else
        {
            entity.setPrice(DEFAULT_PRICE);
        }

        entity.setCategory(dto.getProductCategory());
        entity.setSelected(dto.isChecked());

        if ( dto.getThumbnailBitmap() != null && !dto.isDefaultImage() )
        {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            dto.getThumbnailBitmap().compress(Bitmap.CompressFormat.PNG, BITMAP_QUALITY, stream);
            entity.setImageBytes(stream.toByteArray());
        }
        else
        {
            entity.setImageBytes(null);
        }
    }

    @Override
    public void convertEntitiesToDto(ProductItemEntity entity, ProductDto dto)
    {
        dto.setListId(entity.getShoppingList().getId().toString());
        dto.setProductName(entity.getProductName());

        if ( entity.getId() != null )
        {
            dto.setId(String.valueOf(entity.getId()));
        }

        if ( entity.getQuantity() != null )
        {
            dto.setQuantity(String.valueOf(entity.getQuantity()));
        }

        dto.setProductNotes(entity.getNotes());
        dto.setProductStore(entity.getStore());

        if ( entity.getPrice() != null )
        {
            dto.setProductPrice(getDoubleAsString(entity.getPrice()));
        }

        if ( entity.getQuantity() != null && entity.getPrice() != null )
        {
            dto.setTotalProductPrice(getDoubleAsString(entity.getPrice() * entity.getQuantity()));
        }

        dto.setProductCategory(entity.getCategory());
        dto.setChecked(entity.getSelected());

        byte[] imageBytes = entity.getImageBytes();
        if ( imageBytes != null )
        {
            Bitmap imageBitMap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            dto.setThumbnailBitmap(imageBitMap);
            dto.setDefaultImage(false);
        }
        else
        {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_menu_camera);
            dto.setThumbnailBitmap(bitmap);
            dto.setDefaultImage(true);
        }
    }

    @Override
    public String getDoubleAsString(Double price)
    {
        return StringUtils.getDoubleAsString(price, priceFormat2);
    }

    @Override
    public Double getStringAsDouble(String priceString)
    {
        return StringUtils.getStringAsDouble(priceString, priceFormat2, priceFormat1, priceFormat0);
    }
}
