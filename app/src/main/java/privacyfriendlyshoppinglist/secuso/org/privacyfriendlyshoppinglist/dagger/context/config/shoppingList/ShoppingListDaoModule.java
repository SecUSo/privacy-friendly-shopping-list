package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.dagger.context.config.shoppingList;

import dagger.Module;
import dagger.Provides;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AppModule;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.ShoppingListDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.ShoppingListDaoNew;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.impl.ShoppingListDaoImpl;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.impl.ShoppingListDaoNewImpl;

import javax.inject.Singleton;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 05.06.16 creation date
 */
@Module(
        injects = {
                ShoppingListDao.class,
                ShoppingListDaoNew.class
        }
)
public class ShoppingListDaoModule implements AppModule
{
    @Provides
    @Singleton
    ShoppingListDao provideShoppingListDao()
    {
        return new ShoppingListDaoImpl();
    }

    @Provides
    @Singleton
    ShoppingListDaoNew provideShoppingListDaoNew () {
        return new ShoppingListDaoNewImpl();
    }
}
