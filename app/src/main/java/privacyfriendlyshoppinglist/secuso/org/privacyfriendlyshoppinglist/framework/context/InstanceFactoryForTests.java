package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context;

import android.content.Context;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.DB;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 18.05.16 creation date
 */
public class InstanceFactoryForTests extends AbstractInstanceFactory
{

    public InstanceFactoryForTests(Context context)
    {
        super(context);
    }

    @Override
    protected DB getDB()
    {
        return DB.TEST;
    }
}
