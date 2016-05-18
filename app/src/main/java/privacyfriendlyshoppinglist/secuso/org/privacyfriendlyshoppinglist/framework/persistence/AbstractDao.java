package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.ContextSetter;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.05.16 creation date
 */
public abstract class AbstractDao implements ContextSetter
{
    protected static final String ASC = " ASC";
    protected static final String DESC = " DESC";
    protected static final String EQUALS = "=?";
    protected static final String LIKE = " LIKE ?";
    private static final String _ID = "_id";

    protected Context context;

    @Override
    public void setContext(Context context)
    {
        this.context = context;
    }

    protected SQLiteDatabase getWritableSqLiteDatabase(Context context)
    {
        DataBaseHelper dbHelper = new DataBaseHelper(context);
        return dbHelper.getWritableDatabase();
    }

    protected SQLiteDatabase getReadableSqLiteDatabase(Context context)
    {
        DataBaseHelper dbHelper = new DataBaseHelper(context);
        return dbHelper.getReadableDatabase();
    }

    protected Long saveOrUpdate(Context context, AbstractEntity entity, String tableName, ContentValues values)
    {
        Long id;
        SQLiteDatabase db = getWritableSqLiteDatabase(context);

        if ( hasId(entity) )
        {
            id = update(context, entity, tableName, values);
        }
        else
        {
            id = save(tableName, values, db);
        }

        db.close();
        return id;
    }

    private boolean hasId(AbstractEntity entity)
    {
        return entity.getId() != null;
    }

    private Long save(String tableName, ContentValues values, SQLiteDatabase db)
    {
        Long id;
        id = db.insert(
                tableName,
                null,
                values
        );
        return id;
    }

    private Long update(Context context, AbstractEntity entity, String tableName, ContentValues values)
    {
        Long id;
        SQLiteDatabase db = getWritableSqLiteDatabase(context);

        String selection = _ID + LIKE;
        String[] selectionArgs = {String.valueOf(entity.getId())};

        int count = db.update(
                tableName,
                values,
                selection,
                selectionArgs
        );
        db.close();

        boolean found = count > 0;
        id = getId(entity, found);
        return id;
    }

    private Long getId(AbstractEntity entity, boolean found)
    {
        Long id;
        if ( found )
        {
            id = entity.getId();
        }
        else
        {
            id = null;
        }
        return id;
    }

    protected Cursor getCursor(Context context, String tableName, Long id, String[] columNames)
    {
        SQLiteDatabase db = getReadableSqLiteDatabase(context);

        String sortOrder = _ID + ASC;

        Cursor cursor = db.query(
                tableName,
                columNames,
                _ID + EQUALS,
                new String[]{id.toString()},
                null,
                null,
                sortOrder
        );

        cursor.moveToFirst();
        db.close();
        return cursor;
    }

}
