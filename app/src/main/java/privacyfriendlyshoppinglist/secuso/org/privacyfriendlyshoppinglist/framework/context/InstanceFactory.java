package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context;

import android.content.Context;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.DB;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 11.06.16 creation date
 */
public class InstanceFactory extends AbstractInstanceFactory
{
    public InstanceFactory(Context context)
    {
        super(context);
    }

    @Override
    protected DB getDB()
    {
        return DB.APP;
    }
}
