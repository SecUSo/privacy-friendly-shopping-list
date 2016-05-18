package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.services.products.persistence.impl;

import android.provider.BaseColumns;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.AbstractContract;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.05.16 15:14 creation date
 */
public final class ProductContract extends AbstractContract
{
    public ProductContract()
    {
    }

    public static abstract class Entry implements BaseColumns
    {
        public static final String TABLE_NAME = "productEntry";
        public static final String COLUMN_NAME_ENTRY_NAME = "productName";
    }


    public static final String SQL_CREATE_ENTRIES =
            CREATE_TABLE + Entry.TABLE_NAME + " (" +
                    Entry._ID + INTEGER_PRIMARY_KEY + AUTOINCREMENT_NOT_NULL + COMMA_SEP +
                    Entry.COLUMN_NAME_ENTRY_NAME + TEXT_TYPE +
                    " )";

    public static final String SQL_DELETE_ENTRIES =
            DROP_TABLE_IF_EXISTS + Entry.TABLE_NAME;


    public static String[] getColumnNames()
    {
        return new String[] {Entry._ID, Entry.COLUMN_NAME_ENTRY_NAME };
    }


}
