package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.services.products.persistence.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.AbstractDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.services.products.persistence.ProductDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.services.products.persistence.entity.ProductEntity;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.05.16 15:08 creation date
 */
public class ProductDaoImpl extends AbstractDao implements ProductDao
{

    public ProductDaoImpl( Context context)
    {
        super(context);
    }

    @Override
    public Long save(ProductEntity entity)
    {
        ContentValues values = new ContentValues();
        values.put(ProductContract.Entry.COLUMN_NAME_ENTRY_NAME, entity.getProductName());
        String tableName = ProductContract.Entry.TABLE_NAME;
        Long id = saveOrUpdate(context, entity, tableName, values);
        return id;
    }

    @Override
    public ProductEntity getById(Long id)
    {
        String[] columnNames = ProductContract.getColumnNames();
        Cursor cursor = getCursor(context, ProductContract.Entry.TABLE_NAME, id, columnNames);

        long entityId = cursor.getLong(cursor.getColumnIndexOrThrow(ProductContract.Entry._ID));
        String productName = cursor.getString(cursor.getColumnIndexOrThrow(ProductContract.Entry.COLUMN_NAME_ENTRY_NAME));

        ProductEntity entity = new ProductEntity();
        entity.setId(entityId);
        entity.setProductName(productName);
        return entity;
    }
}
