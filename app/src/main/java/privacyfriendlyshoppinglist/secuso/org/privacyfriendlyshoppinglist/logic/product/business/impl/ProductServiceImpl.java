package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.impl;

import android.content.Context;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.DB;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.ProductService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductTemplateDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.TotalDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.impl.converter.ProductConverterService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.impl.validator.ProductValidatorService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence.ProductItemDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence.ProductTemplateDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence.entity.ProductItemEntity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence.entity.ProductTemplateEntity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.ShoppingListService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.entity.ShoppingListEntity;
import rx.Observable;

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
    public ProductDto getById(String entityId)
    {
        ProductItemEntity productEntity = productItemDao.getById(Long.valueOf(entityId));
        if ( productEntity == null ) return null;
        Long templateId = productEntity.getProductTemplate().getId();
        ProductTemplateEntity templateEntity = productTemplateDao.getById(Long.valueOf(templateId));

        ProductDto dto = new ProductDto();
        converterService.convertEntitiesToDto(templateEntity, productEntity, dto);
        return dto;
    }

    @Override
    public void deleteById(String id)
    {
        productItemDao.deleteById(Long.valueOf(id));
    }

    @Override
    public void deleteSelected(List<ProductDto> productDtos)
    {
        Observable.from(productDtos)
                .filter(dto -> dto.isSelectedForDeletion())
                .subscribe(dto -> deleteById(dto.getProductId()));
    }

    @Override
    public List<ProductDto> getAllProducts(String listId)
    {
        Observable<ProductDto> dtos = Observable
                .from(productItemDao.getAllEntities())
                .filter(entity -> entity.getShoppingList().getId() == Long.valueOf(listId))
                .map(this::getDto);

        return dtos.toList().toBlocking().single();
    }

    @Override
    public List<ProductTemplateDto> getAllTemplateProducts()
    {
        Observable<ProductTemplateDto> dtos = Observable
                .from(productTemplateDao.getAllEntities())
                .map(this::getDto);

        return dtos.toList().toBlocking().single();
    }

    @Override
    public List<ProductDto> moveSelectedToEnd(List<ProductDto> productDtos)
    {
        List<ProductDto> nonSelectedDtos = Observable
                .from(productDtos)
                .filter(dto -> !dto.isChecked())
                .toList().toBlocking().single();

        List<ProductDto> selectedDtos = Observable
                .from(productDtos)
                .filter(dto -> dto.isChecked())
                .toList().toBlocking().single();
        nonSelectedDtos.addAll(selectedDtos);
        productDtos = nonSelectedDtos;
        return productDtos;
    }

    @Override
    public TotalDto computeTotals(List<ProductDto> productDtos)
    {
        double totalAmount = 0.0;
        double checkedAmount = 0.0;
        for ( ProductDto dto : productDtos )
        {
            String price = dto.getProductPrice();
            if ( price != null )
            {
                Integer quantity = Integer.valueOf(dto.getQuantity());
                double priceAsDouble = Double.parseDouble(price) * quantity;
                totalAmount += priceAsDouble;
                if ( dto.isChecked() )
                {
                    checkedAmount += priceAsDouble;
                }
            }
        }

        TotalDto totalDto = new TotalDto();

        if ( totalAmount == 0.0 )
        {
            totalDto.setEqualsZero(true);
        }
        totalDto.setTotalAmount(converterService.getDoubleAsString(totalAmount));
        totalDto.setCheckedAmount(converterService.getDoubleAsString(checkedAmount));

        return totalDto;
    }

    @Override
    public void sortProducts(List<ProductDto> products, String criteria, boolean ascending)
    {

    }

    private ProductDto getDto(ProductItemEntity entity)
    {
        // the next line contains only the id. The whole entity is needed
        ProductTemplateEntity templateReference = entity.getProductTemplate();
        ProductTemplateEntity productTemplateEntity = productTemplateDao.getById(templateReference.getId());
        ProductDto dto = new ProductDto();
        converterService.convertEntitiesToDto(productTemplateEntity, entity, dto);
        return dto;
    }

    private ProductTemplateDto getDto(ProductTemplateEntity entity)
    {
        ProductTemplateDto dto = new ProductTemplateDto();
        converterService.convertTemplateEntityToDto(entity, dto);
        return dto;
    }
}
