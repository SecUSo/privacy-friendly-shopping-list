package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context;

import android.content.Context;
import dagger.ObjectGraph;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.dagger.context.config.AppContextModule;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.DB;

import javax.inject.Inject;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 18.05.16 creation date
 */
public class InstanceFactory<T>
{
    private final AppModule module;
    private DB db;

    @Inject
    public InstanceFactory(DB db)
    {
        this.db = db;
        this.module = new AppContextModule();
    }

    public T createInstance(Context context, Class<T> aClass)
    {
        T classInstance;
        ObjectGraph objectGraph = ObjectGraph.create(module);
        classInstance = objectGraph.get(aClass);
        ((ContextSetter) classInstance).setContext(context, this.db);
        return classInstance;
    }

    public T createInstance(Class<T> aClass)
    {
        T classInstance;
        ObjectGraph objectGraph = ObjectGraph.create(module);
        classInstance = objectGraph.get(aClass);
        return classInstance;
    }
}
