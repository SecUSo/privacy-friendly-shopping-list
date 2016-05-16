package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.services.products.persistence.impl;

import android.provider.BaseColumns;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.05.16 15:14 creation date
 */
public final class ProductContract
{
    public ProductContract()
    {
    }

    public static abstract class ProductEntry implements BaseColumns
    {
        // todo: warning Camel Case
        public static final String TABLE_NAME = "productentry";
        public static final String COLUMN_NAME_ENTRY_ID = "productid";
        public static final String COLUMN_NAME_ENTRY_NAME = "productname";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ProductEntry.TABLE_NAME + " (" +
                    ProductEntry._ID + " INTEGER PRIMARY KEY," +
                    ProductEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    ProductEntry.COLUMN_NAME_ENTRY_NAME + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ProductEntry.TABLE_NAME;

    public static String getSqlCreateEntries()
    {
        return SQL_CREATE_ENTRIES;
    }

    public static String getSqlDeleteEntries()
    {
        return SQL_DELETE_ENTRIES;
    }
}
