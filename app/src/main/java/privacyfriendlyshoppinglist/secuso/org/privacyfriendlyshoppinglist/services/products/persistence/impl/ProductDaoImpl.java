package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.services.products.persistence.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.DataBaseHelper;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.services.products.persistence.ProductDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.services.products.persistence.entity.ProductEntity;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.05.16 15:08 creation date
 */
public class ProductDaoImpl implements ProductDao
{
    @Override
    public Long save(Context context, ProductEntity entity)
    {
        DataBaseHelper dbHelper = new DataBaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ProductContract.ProductEntry.COLUMN_NAME_ENTRY_ID, entity.getId());
        values.put(ProductContract.ProductEntry.COLUMN_NAME_ENTRY_NAME, entity.getProductName());

        long newRowId;
        newRowId = db.insert(
                ProductContract.ProductEntry.TABLE_NAME,
                ProductContract.ProductEntry.COLUMN_NAME_ENTRY_NAME,
                values
        );

        return newRowId;
    }

    @Override
    public ProductEntity getById(Context context, Long id)
    {
        return null;
    }
}
