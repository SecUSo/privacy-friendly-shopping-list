package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.dagger.context.config.product;

import dagger.Module;
import dagger.Provides;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AppModule;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.products.persistence.ProductDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.products.persistence.ProductItemDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.products.persistence.ProductTemplateDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.products.persistence.impl.ProductDaoImpl;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.products.persistence.impl.ProductItemDaoImpl;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.products.persistence.impl.ProductTemplateDaoImpl;

import javax.inject.Singleton;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 17.05.16 creation date
 */
@Module(
        injects = {
                ProductDao.class,
                ProductTemplateDao.class,
                ProductItemDao.class
        }
)
public class ProductDaoModule implements AppModule
{
    @Provides
    @Singleton
    ProductDao provideProductDao()
    {
        return new ProductDaoImpl();
    }

    @Provides
    @Singleton
    ProductTemplateDao provideProductTemplateDao()
    {
        return new ProductTemplateDaoImpl();
    }

    @Provides
    @Singleton
    ProductItemDao provideProductItemDao()
    {
        return new ProductItemDaoImpl();
    }
}
