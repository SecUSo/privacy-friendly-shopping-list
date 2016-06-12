package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context;

import android.content.Context;
import dagger.ObjectGraph;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.dagger.context.config.AppContextModule;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.DB;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 11.06.16 creation date
 */
abstract public class AbstractInstanceFactory
{
    private Context context;

    public AbstractInstanceFactory(Context context)
    {
        this.context = context;
    }

    public Object createInstance(Class aClass)
    {
        ObjectGraph objectGraph = ObjectGraph.create(new AppContextModule());
        Object classInstance = objectGraph.get(aClass);
        ((ContextSetter) classInstance).setContext(context, getDB());
        return classInstance;
    }

    abstract protected DB getDB();
}
