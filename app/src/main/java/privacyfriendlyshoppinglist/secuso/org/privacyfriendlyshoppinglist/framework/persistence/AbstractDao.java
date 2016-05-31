package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence;

import android.content.Context;
import com.j256.ormlite.dao.Dao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.ContextSetter;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.logger.PFALogger;

import java.sql.SQLException;
import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.05.16 creation date
 */
public abstract class AbstractDao<T extends AbstractEntity> implements ContextSetter
{
    private DataBaseHelper database;

    @Override
    public void setContext(Context context, DB db)
    {
        database = new DataBaseHelper(context, db);
    }

    protected Long saveOrUpdate(T entity)
    {
        PFALogger.info(getClass().getName(), "saveOrUpdate", entity);
        try
        {
            @SuppressWarnings("unchecked")
            Dao<T, Long> dao = database.getDao((Class) entity.getClass());
            dao.createOrUpdate(entity);
            PFALogger.info(getClass().getName(), "saveOrUpdate", "successful");
            return entity.getId();
        }
        catch ( SQLException e )
        {
            PFALogger.error(getClass().getName(), "saveOrUpdate", entity, e);
            return null;
        }
    }

    protected T getById(Long id, Class<T> type)
    {
        PFALogger.info(getClass().getName(), "getById", id);
        try
        {
            Dao<T, Long> dao = database.getDao(type);
            T entity = dao.queryForId(id);
            PFALogger.info(getClass().getName(), "getById", "successful");
            return entity;
        }
        catch ( SQLException e )
        {
            PFALogger.error(getClass().getName(), "getById", id, e);
            return null;
        }
    }

    protected List<T> getAllEntities(Class<T> type)
    {
        List<T> entities;
        PFALogger.info(getClass().getName(), "getAllEntities", "start");
        try
        {
            Dao<T, Long> dao = database.getDao(type);
            entities = dao.queryForAll();
            PFALogger.info(getClass().getName(), "getAllEntities", "successful");
            return entities;
        }
        catch ( SQLException e )
        {
            PFALogger.error(getClass().getName(), "getAllEntities", type, e);
            return null;
        }
    }

    protected boolean deleteById(Long id, Class<T> type)
    {
        PFALogger.info(getClass().getName(), "deleteById", id);
        try
        {
            Dao<T, Long> dao = database.getDao(type);
            dao.deleteById(id);
            PFALogger.info(getClass().getName(), "deleteById", "successful");
            return true;
        }
        catch ( SQLException e )
        {
            PFALogger.error(getClass().getName(), "deleteById", id, e);
            return false;
        }
    }

    protected Dao<T, Long> getDao(Class<T> type)
    {
        PFALogger.info(getClass().getName(), "getDao", "start");
        try
        {
            Dao<T, Long> dao = database.getDao(type);
            PFALogger.info(getClass().getName(), "getDao", "successful");
            return dao;
        }
        catch ( SQLException e )
        {
            PFALogger.error(getClass().getName(), "getDao", type, e);
            return null;
        }
    }


}
