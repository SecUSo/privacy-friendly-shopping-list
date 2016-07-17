package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.impl;

import android.content.Context;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.DB;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.ProductService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductTemplateDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.impl.converter.ProductConverterService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.impl.validator.ProductValidatorService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence.ProductItemDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence.ProductTemplateDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence.entity.ProductItemEntity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence.entity.ProductTemplateEntity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.ShoppingListService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.entity.ShoppingListEntity;

import javax.inject.Inject;
import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 17.07.16 creation date
 */
public class ProductServiceImpl implements ProductService
{
    private ProductItemDao productItemDao;
    private ProductTemplateDao productTemplateDao;
    private ProductConverterService converterService;
    private ProductValidatorService validatorService;
    private ShoppingListService shoppingListService;

    @Inject
    public ProductServiceImpl(
            ProductItemDao productItemDao,
            ProductTemplateDao productTemplateDao,
            ProductConverterService converterService,
            ProductValidatorService validatorService,
            ShoppingListService shoppingListService
    )
    {
        this.productItemDao = productItemDao;
        this.productTemplateDao = productTemplateDao;
        this.converterService = converterService;
        this.validatorService = validatorService;
        this.shoppingListService = shoppingListService;
    }

    @Override
    public void setContext(Context context, DB db)
    {
        productItemDao.setContext(context, db);
        productTemplateDao.setContext(context, db);
        shoppingListService.setContext(context, db);
        converterService.setContext(context, db);
    }

    @Override
    public void saveOrUpdate(ProductDto dto, String listId)
    {
        validatorService.validate(dto);
        if ( !dto.hasErrors() )
        {
            ProductTemplateEntity templateEntity = new ProductTemplateEntity();
            converterService.convertDtoToTemplateEntity(dto, templateEntity);
            productTemplateDao.save(templateEntity);
            dto.setId(templateEntity.getId().toString());

            ProductItemEntity entity = new ProductItemEntity();
            converterService.convertDtoToEntity(dto, entity);
            entity.setProductTemplate(templateEntity);

            ShoppingListEntity shoppingListEntity = shoppingListService.getEntityById(listId);
            entity.setShoppingList(shoppingListEntity);

            productItemDao.save(entity);
            dto.setProductId(entity.getId().toString());
        }
    }

    @Override
    public ProductDto getById(String id)
    {
        return null;
    }

    @Override
    public void deleteById(String id)
    {

    }

    @Override
    public void deleteSelected(List<ProductDto> productDtos)
    {

    }

    @Override
    public List<ProductDto> getAllProducts(String listId)
    {
        return null;
    }

    @Override
    public List<ProductTemplateDto> getAllTemplateProducts()
    {
        return null;
    }

    @Override
    public List<ProductDto> getAllSortedBySelection(List<ProductDto> productDtos)
    {
        return null;
    }

    @Override
    public void sortProducts(List<ProductDto> products, String criteria, boolean ascending)
    {

    }
}
