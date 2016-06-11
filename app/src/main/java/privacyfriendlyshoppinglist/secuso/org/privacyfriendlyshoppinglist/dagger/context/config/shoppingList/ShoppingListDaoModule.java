package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.dagger.context.config.shoppingList;

import dagger.Module;
import dagger.Provides;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AppModule;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.ShoppingListDaoNew;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.impl.ShoppingListDaoNewImpl;

import javax.inject.Singleton;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 05.06.16 creation date
 */
@Module(
        injects = {
                ShoppingListDaoNew.class
        }
)
public class ShoppingListDaoModule implements AppModule
{

    @Provides
    @Singleton
    ShoppingListDaoNew provideShoppingListDaoNew () {
        return new ShoppingListDaoNewImpl();
    }
}
