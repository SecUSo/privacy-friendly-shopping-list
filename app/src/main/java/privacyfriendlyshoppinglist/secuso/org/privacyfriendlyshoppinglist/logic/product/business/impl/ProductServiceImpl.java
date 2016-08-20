package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.impl;

import android.content.Context;
import android.content.ContextWrapper;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.comparators.PFAComparators;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.DB;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.StringUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.ProductService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.AutoCompleteLists;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.TotalDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.impl.comparators.ProductComparators;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.impl.converter.ProductConverterService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence.ProductItemDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence.entity.ProductItemEntity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.ShoppingListService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.entity.ShoppingListEntity;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import javax.inject.Inject;
import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 17.07.16 creation date
 */
public class ProductServiceImpl implements ProductService
{
    private static final String IMAGE_DIR_NAME = "imageDir";
    private static final String EXTENSION = ".jpg";

    private ProductItemDao productItemDao;
    private ProductConverterService converterService;
    private ShoppingListService shoppingListService;
    private Context context;

    @Inject
    public ProductServiceImpl(
            ProductItemDao productItemDao,
            ProductConverterService converterService,
            ShoppingListService shoppingListService
    )
    {
        this.productItemDao = productItemDao;
        this.converterService = converterService;
        this.shoppingListService = shoppingListService;
    }

    @Override
    public void setContext(Context context, DB db)
    {
        this.context = context;
        productItemDao.setContext(context, db);
        shoppingListService.setContext(context, db);
        converterService.setContext(context, db);
    }

    @Override
    public void saveOrUpdate(ProductDto dto, String listId)
    {
        ProductItemEntity entity = new ProductItemEntity();
        converterService.convertDtoToEntity(dto, entity);

        ShoppingListEntity shoppingListEntity = shoppingListService.getEntityById(listId);
        entity.setShoppingList(shoppingListEntity);

        productItemDao.save(entity);
        dto.setId(entity.getId().toString());
    }

    @Override
    public ProductDto getById(String entityId)
    {
        ProductItemEntity productEntity = productItemDao.getById(Long.valueOf(entityId));
        if ( productEntity == null ) return null;

        ProductDto dto = new ProductDto();
        converterService.convertEntitiesToDto(productEntity, dto);
        return dto;
    }

    @Override
    public String getProductImagePath(String id)
    {
        ContextWrapper contextWrapper = new ContextWrapper(context);
        File directory = contextWrapper.getDir(IMAGE_DIR_NAME, Context.MODE_PRIVATE);
        File path = new File(directory, getUniqueName(id));
        return path.getAbsolutePath();
    }

    @Override
    public void deleteById(String id)
    {
        productItemDao.deleteById(Long.valueOf(id));

        // delete imageFile if exists
        File imageFile = new File(getProductImagePath(id));
        imageFile.delete();
    }

    @Override
    public Observable<Void> deleteOnlyImage(String id)
    {
        Observable observable = Observable
                .create(subscriber ->
                {
                    subscriber.onNext(deleteOnlyImageSync(id));
                    subscriber.onCompleted();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private Void deleteOnlyImageSync(String id)
    {
        ProductItemEntity entity = productItemDao.getById(Long.valueOf(id));
        entity.setImageBytes(null);
        productItemDao.save(entity);

        File imageFile = new File(getProductImagePath(id));
        imageFile.delete();
        return null;
    }

    @Override
    public void deleteSelected(List<ProductDto> productDtos)
    {
        Observable.from(productDtos)
                .filter(dto -> dto.isSelectedForDeletion())
                .subscribe(dto -> deleteById(dto.getId()));
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
    public TotalDto getInfo(String listId)
    {
        List<ProductDto> allProducts = getAllProducts(listId);
        TotalDto totalDto = computeTotals(allProducts);
        return totalDto;
    }

    @Override
    public void deleteAllFromList(String listId)
    {
        List<ProductDto> productDtos = getAllProducts(listId);
        Observable.from(productDtos)
                .subscribe(dto -> deleteById(dto.getId()));
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
        int nrProducts = 0;
        for ( ProductDto dto : productDtos )
        {
            Integer quantity = Integer.valueOf(dto.getQuantity());
            nrProducts += quantity;
            String price = dto.getProductPrice();
            if ( price != null )
            {
                double priceAsDouble = converterService.getStringAsDouble(price) * quantity;
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
        totalDto.setNrProducts(nrProducts);

        return totalDto;
    }

    @Override
    public String getSharableText(ProductDto dto)
    {
        StringBuilder sb = new StringBuilder();
        sb
                .append(StringUtils.DASH)
                .append(StringUtils.LEFT_BRACE)
                .append(dto.getQuantity())
                .append(StringUtils.RIGHT_BRACE)
                .append(dto.getProductName());

        if ( !StringUtils.isEmpty(dto.getProductNotes()) )
        {
            sb
                    .append(StringUtils.NEW_LINE)
                    .append(StringUtils.NEW_LINE)
                    .append(dto.getProductNotes());
        }

        return sb.toString();
    }

    @Override
    public Observable<AutoCompleteLists> getAutoCompleteListsObservable()
    {
        Observable autoCompleteListsObservable = Observable
                .create(subscriber ->
                {
                    subscriber.onNext(getAutoCompleteLists());
                    subscriber.onCompleted();
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread());
        return autoCompleteListsObservable;
    }

    private AutoCompleteLists getAutoCompleteLists()
    {
        AutoCompleteLists autoCompleteLists = new AutoCompleteLists();

        Observable
                .from(productItemDao.getAllEntities())
                .map(this::getDto)
                .subscribe(dto -> autoCompleteLists.updateLists(dto));
        return autoCompleteLists;
    }

    @Override
    public void sortProducts(List<ProductDto> products, String criteria, boolean ascending)
    {
        if ( PFAComparators.SORT_BY_NAME.equals(criteria) )
        {
            Collections.sort(products, ProductComparators.getNameComparator(ascending));
        }
        else if ( PFAComparators.SORT_BY_QUANTITY.equals(criteria) )
        {
            Collections.sort(products, ProductComparators.getQuantityCompartor(ascending));
        }
        else if ( PFAComparators.SORT_BY_STORE.equals(criteria) )
        {
            Collections.sort(products, ProductComparators.getStoreComparator(ascending));
        }
        else if ( PFAComparators.SORT_BY_CATEGORY.equals(criteria) )
        {
            Collections.sort(products, ProductComparators.getCategoryComparator(ascending));
        }
        else if ( PFAComparators.SORT_BY_PRICE.equals(criteria) )
        {
            Collections.sort(products, ProductComparators.getPriceComparator(ascending, context));
        }
    }

    private ProductDto getDto(ProductItemEntity entity)
    {
        ProductDto dto = new ProductDto();
        converterService.convertEntitiesToDto(entity, dto);
        return dto;
    }

    private String getUniqueName(String productId)
    {
        StringBuilder sb = new StringBuilder();
        sb
                .append(productId)
                .append(EXTENSION);
        return sb.toString();
    }
}
