package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence;

import android.content.Context;
import android.util.Log;
import com.j256.ormlite.dao.Dao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.ContextSetter;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.logger.Logger;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.services.products.persistence.entity.ProductEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.05.16 creation date
 */
public abstract class AbstractDao<T extends AbstractEntity> implements ContextSetter
{
    protected Context context;
    protected DataBaseHelper database;

    @Override
    public void setContext(Context context)
    {
        this.context = context;
        database = new DataBaseHelper(context);
    }

    public Long saveOrUpdate(T entity)
    {
        Logger.info(getClass().getName(), "saveOrUpdate", entity);
        try
        {
            Dao<T, Long> dao = database.getDao((Class) entity.getClass());
            dao.createOrUpdate(entity);
            Logger.info(getClass().getName(), "saveOrUpdate", "successful");
            return entity.getId();
        }
        catch ( SQLException e )
        {
            Logger.error(getClass().getName(), "saveOrUpdate", entity, e);
            return null;
        }
    }

    public T getById(Long id, Class<T> type)
    {
        Logger.info(getClass().getName(), "getById", id);
        try
        {
            Dao<T, Long> dao = database.getDao(type);
            T entity = dao.queryForId(id);
            Logger.info(getClass().getName(), "getById", "successful");
            return entity;
        }
        catch ( SQLException e )
        {
            Logger.error(getClass().getName(), "getById", id, e);
            return null;
        }
    }

    public List<T> getAllEntities(Class<T> type){
        List<T> entities;
        Logger.info(getClass().getName(), "getAllEntities", "start");
        try
        {
            Dao<T, Long> dao = database.getDao(type);
            entities = dao.queryForAll();
            Logger.info(getClass().getName(), "getById", "successful");
            return entities;
        }
        catch ( SQLException e )
        {
            Logger.error(getClass().getName(), "getById", type, e);
            return null;
        }
    }


}
