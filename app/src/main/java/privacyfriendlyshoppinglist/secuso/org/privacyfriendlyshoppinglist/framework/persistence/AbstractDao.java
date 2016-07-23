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
    private static final String SUCCESSFUL = "successful";
    private static final String GET_ALL_ENTITIES = "getAllEntities";
    private static final String DELETE_BY_ID = "deleteById";
    private static final String GET_DAO = "getDao";
    private static final String SAVE_OR_UPDATE = "saveOrUpdate";
    private static final String GET_BY_ID = "getById";
    private static final String ID = ", ID=";
    private static final String ENTITY = ", ENTITY=";
    private DataBaseHelper database;

    @Override
    public void setContext(Context context, DB db)
    {
        database = new DataBaseHelper(context, db);
    }

    protected Long saveOrUpdate(T entity)
    {
        try
        {
            @SuppressWarnings("unchecked")
            Dao<T, Long> dao = database.getDao((Class) entity.getClass());
            dao.createOrUpdate(entity);
            PFALogger.debug(getClass().getName(), SAVE_OR_UPDATE, SUCCESSFUL);
            return entity.getId();
        }
        catch ( SQLException e )
        {
            PFALogger.error(getClass().getName(), SAVE_OR_UPDATE + ENTITY + entity.toString(), e);
            return null;
        }
    }

    protected T getById(Long id, Class<T> type)
    {
        try
        {
            Dao<T, Long> dao = database.getDao(type);
            T entity = dao.queryForId(id);
            PFALogger.debug(getClass().getName(), GET_BY_ID, SUCCESSFUL);
            return entity;
        }
        catch ( SQLException e )
        {
            PFALogger.error(getClass().getName(), GET_BY_ID + ID + id, e);
            return null;
        }
    }

    protected List<T> getAllEntities(Class<T> type)
    {
        List<T> entities;
        try
        {
            Dao<T, Long> dao = database.getDao(type);
            entities = dao.queryForAll();
            PFALogger.debug(getClass().getName(), GET_ALL_ENTITIES, SUCCESSFUL);
            return entities;
        }
        catch ( SQLException e )
        {
            PFALogger.error(getClass().getName(), GET_ALL_ENTITIES, e);
            return null;
        }
    }

    protected boolean deleteById(Long id, Class<T> type)
    {
        try
        {
            Dao<T, Long> dao = database.getDao(type);
            dao.deleteById(id);
            PFALogger.debug(getClass().getName(), DELETE_BY_ID, SUCCESSFUL);
            return true;
        }
        catch ( SQLException e )
        {
            PFALogger.error(getClass().getName(), DELETE_BY_ID + ID + id, e);
            return false;
        }
    }

    protected Dao<T, Long> getDao(Class<T> type)
    {
        try
        {
            Dao<T, Long> dao = database.getDao(type);
            PFALogger.debug(getClass().getName(), GET_DAO, SUCCESSFUL);
            return dao;
        }
        catch ( SQLException e )
        {
            PFALogger.error(getClass().getName(), GET_DAO, e);
            return null;
        }
    }


}
