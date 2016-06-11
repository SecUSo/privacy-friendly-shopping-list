package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context;

import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.DB;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 18.05.16 creation date
 */
public class InstanceFactoryForTests extends AbstractInstanceFactory
{

    @Override
    protected DB getDB()
    {
        return DB.TEST;
    }
}
