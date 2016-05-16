package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.05.16 creation date
 */
public abstract class AbstractContract
{
    // SQL statements
    protected static final String CREATE_TABLE = "CREATE TABLE ";
    protected static final String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS ";
    protected static final String INTEGER_PRIMARY_KEY = " INTEGER PRIMARY KEY";
    protected static final String AUTOINCREMENT_NOT_NULL = " AUTOINCREMENT NOT NULL";
    protected static final String COMMA_SEP = ",";

    // NULL
    protected static final String NULL = " NULL";

    // SQL types
    protected static final String INTEGER_TYPE = " INTEGER";
    protected static final String REAL_TYPE = " REAL";
    protected static final String TEXT_TYPE = " TEXT";
    protected static final String BLOB_TYPE = " BLOB";
    protected static final String NUMERIC_TYPE = " NUMERIC";
}
