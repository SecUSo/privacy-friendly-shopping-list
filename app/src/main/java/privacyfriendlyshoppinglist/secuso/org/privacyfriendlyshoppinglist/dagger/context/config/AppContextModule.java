package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.dagger.context.config;

import dagger.Module;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.dagger.context.config.product.ProductModule;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.dagger.context.config.shoppingList.ShoppingListModule;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.dagger.context.config.statistics.StatisticsModule;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AppModule;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 17.05.16 creation date
 */
@Module(
        includes = {
                // DEPENDENCY_INJECTION add all Modules here
                ProductModule.class,
                ShoppingListModule.class,
                StatisticsModule.class
        }
)
public class AppContextModule implements AppModule
{
}
