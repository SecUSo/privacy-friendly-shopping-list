package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 31.05.16 creation date
 */
public enum DB
{
    TEST("ShoppingList_test.db", 1), // for test purposes
    APP("ShippingList.db", 7); // for the app

    private String dbName;
    private int dbVersion;

    DB(String dbName, int dbVersion)
    {
        this.dbName = dbName;
        this.dbVersion = dbVersion;
    }

    public String getDbName()
    {
        return dbName;
    }

    public int getDbVersion()
    {
        return dbVersion;
    }
}
