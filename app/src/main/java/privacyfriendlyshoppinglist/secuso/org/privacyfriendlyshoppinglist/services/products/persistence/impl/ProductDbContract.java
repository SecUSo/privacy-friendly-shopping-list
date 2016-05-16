package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.services.products.persistence.impl;

import android.provider.BaseColumns;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.05.16 15:14 creation date
 */
public final class ProductDbContract
{
    public ProductDbContract()
    {
    }

    public static abstract class Entry implements BaseColumns
    {
        public static final String TABLE_NAME = "productEntry";
        public static final String COLUMN_NAME_ENTRY_ID = "productId";
        public static final String COLUMN_NAME_ENTRY_NAME = "productName";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Entry.TABLE_NAME + " (" +
                    Entry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    Entry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    Entry.COLUMN_NAME_ENTRY_NAME + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Entry.TABLE_NAME;

    public static String getSqlCreateEntries()
    {
        return SQL_CREATE_ENTRIES;
    }

    public static String getSqlDeleteEntries()
    {
        return SQL_DELETE_ENTRIES;
    }
}
