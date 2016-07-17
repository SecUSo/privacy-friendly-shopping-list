package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.dagger.context.config.product;

import dagger.Module;
import dagger.Provides;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.dagger.context.config.shoppingList.ShoppingListModule;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AppModule;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.ProductService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.impl.ProductServiceImpl;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.impl.converter.ProductConverterService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.impl.converter.impl.ProductConverterServiceImpl;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.impl.validator.ProductValidatorService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.impl.validator.impl.ProductValidatorServiceImpl;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence.ProductItemDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence.ProductTemplateDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence.impl.ProductItemDaoImpl;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence.impl.ProductTemplateDaoImpl;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.ShoppingListService;

import javax.inject.Singleton;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 17.05.16 creation date
 */
@Module(
        includes = {
                ShoppingListModule.class
        },
        injects = {
                ProductService.class,
                ProductTemplateDao.class,
                ProductItemDao.class,
                ProductConverterService.class,
                ProductValidatorService.class
        }
)
public class ProductModule implements AppModule
{
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

    @Provides
    @Singleton
    ProductConverterService provideProductConverterService()
    {
        return new ProductConverterServiceImpl();
    }

    @Provides
    @Singleton
    ProductValidatorService provideProductValidatorService()
    {
        return new ProductValidatorServiceImpl();
    }

    @Provides
    @Singleton
    ProductService provideProductService(
            ProductItemDao productItemDao,
            ProductTemplateDao productTemplateDao,
            ProductConverterService converterService,
            ProductValidatorService validatorService,
            ShoppingListService shoppingListService
    )
    {
        return new ProductServiceImpl(
                productItemDao,
                productTemplateDao,
                converterService,
                validatorService,
                shoppingListService
        );
    }
}
