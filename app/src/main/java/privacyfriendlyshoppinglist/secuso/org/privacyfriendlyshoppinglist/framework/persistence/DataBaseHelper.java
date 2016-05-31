package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.logger.Logger;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.services.products.persistence.entity.ProductEntity;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.05.16 15:35 creation date
 */
public class DataBaseHelper extends OrmLiteSqliteOpenHelper
{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ShoppingList_test.db";
    private static List<? extends AbstractEntity> entities;

    public DataBaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    private void setupClasses()
    {
        entities = Arrays.asList(
                // SETUP_PERSISTENCE: all entities must be added here
                new ProductEntity()
        );
    }

    @Override
    public void onCreate(SQLiteDatabase database, final ConnectionSource connectionSource)
    {
        try
        {
            setupClasses();
            Logger.info(getClass().getName(), "onCreate", "start");
            for (AbstractEntity entity : entities)
            {
                TableUtils.createTable(connectionSource, entity.getClass());
            }
        }
        catch ( Exception e )
        {
            Logger.error(getClass().getName(), "onCreate", entities, e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion)
    {
        try
        {
            setupClasses();
            for (AbstractEntity entity : entities)
            {
                TableUtils.dropTable(connectionSource, entity.getClass(), true);
            }
            onCreate(database, connectionSource);
        }
        catch ( SQLException e )
        {
            Logger.error(getClass().getName(), "onUpgrade", entities, e);
        }
    }

    @Override
    public void close()
    {
        super.close();
    }

    private static class DataBaseConfig extends OrmLiteConfigUtil
    {
        // SETUP_PERSISTENCE: run every time the DB schema changes
        // Run Configuration: make sure to set "privacy-friendly-shopping-list/app/src/main" as Working directory
        public static void main(String[] args) throws SQLException, IOException
        {
            writeConfigFile("ormlite_config.txt");
        }
    }
}
