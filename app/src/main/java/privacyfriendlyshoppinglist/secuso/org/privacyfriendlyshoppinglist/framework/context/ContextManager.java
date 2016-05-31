package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context;

import android.content.Context;
import dagger.ObjectGraph;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.dagger.context.config.AppContextModule;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.DB;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 18.05.16 creation date
 */
public class ContextManager<T extends ContextSetter>
{
    private final AppModule module;

    public ContextManager()
    {
        this.module = new AppContextModule();
    }

    public ContextManager(AppModule module)
    {
        this.module = module;
    }


    public T getInstance(Context context, DB db, Class<T> aClass)
    {
        T classInstance;
        ObjectGraph objectGraph = ObjectGraph.create(module);
        classInstance = objectGraph.get(aClass);
        classInstance.setContext(context, db);
        return classInstance;
    }

    public T getInstance(Class<T> aClass)
    {
        T classInstance;
        ObjectGraph objectGraph = ObjectGraph.create(module);
        classInstance = objectGraph.get(aClass);
        return classInstance;
    }
}
