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
        values.put(ProductDbContract.Entry.COLUMN_NAME_ENTRY_NAME, entity.getProductName());

        long id;
        id = db.insert(
                ProductDbContract.Entry.TABLE_NAME,
                ProductDbContract.Entry.COLUMN_NAME_ENTRY_NAME,
                values
        );

        return id;
    }

    @Override
    public ProductEntity getById(Context context, Long id)
    {
        return null;
    }
}
