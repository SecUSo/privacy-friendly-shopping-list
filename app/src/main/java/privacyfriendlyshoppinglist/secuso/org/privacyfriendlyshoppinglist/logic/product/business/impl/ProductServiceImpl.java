package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.impl;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.comparators.PFAComparators;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.logger.PFALogger;
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
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListDto;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.persistence.entity.ShoppingListEntity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.statistics.chart.NumberScale;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.*;

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
    public Observable<Void> saveOrUpdate(ProductDto dto, String listId)
    {
        Observable<Void> observable = Observable
                .fromCallable(() -> saveOrUpdateSync(dto, listId))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private Void saveOrUpdateSync(ProductDto dto, String listId)
    {
        ProductItemEntity entity = new ProductItemEntity();
        converterService.convertDtoToEntity(dto, entity);

        ShoppingListEntity shoppingListEntity = shoppingListService.getEntityByIdSync(listId);
        entity.setShoppingList(shoppingListEntity);

        productItemDao.save(entity);
        dto.setId(entity.getId().toString());
        return null;
    }

    @Override
    public Observable<Void> createTemplate(ListDto newList)
    {
        Observable<Void> observable = Observable
                .fromCallable(() -> createTemplateSync(newList))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private Void createTemplateSync(ListDto newList)
    {
        Map<String, List<ProductDto>> productsMap = createProductsMap();

        shoppingListService.saveOrUpdateSync(newList);

        for ( String productName : productsMap.keySet() )
        {
            ProductDto productDto = createTemplateProduct(productsMap, productName);
            copyToListSync(productDto, newList.getId());
        }

        return null;
    }

    private Map<String, List<ProductDto>> createProductsMap()
    {
        Map<String, List<ProductDto>> productsMap = new HashMap<>();
        List<ProductItemEntity> productEntities = productItemDao.getAllEntities();
        for ( ProductItemEntity entity : productEntities )
        {
            List<ProductDto> accumList = productsMap.get(entity.getProductName());
            if ( accumList == null )
            {
                accumList = new ArrayList<>();
            }
            ProductDto dto = new ProductDto();
            converterService.convertEntitiesToDto(entity, dto);
            accumList.add(dto);
            productsMap.put(dto.getProductName(), accumList);
        }

        return productsMap;
    }

    private ProductDto createTemplateProduct(Map<String, List<ProductDto>> productsMap, String productName)
    {
        Resources resources = context.getResources();
        String format = resources.getString(R.string.number_format_2_decimals);
        ProductDto productDto = new ProductDto();
        productDto.setProductName(productName);
        List<ProductDto> accumProducts = productsMap.get(productName);
        double price = 0.0;
        int quantity = 0;
        Set<String> categories = new TreeSet<>();
        Set<String> stores = new TreeSet<>();
        Bitmap thumbnail = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_menu_camera);
        boolean defaultImage = true;
        for ( ProductDto refProductDto : accumProducts )
        {
            price = price + StringUtils.getStringAsDouble(refProductDto.getProductPrice(), format);
            quantity = quantity + Integer.parseInt(refProductDto.getQuantity());
            String productCategory = refProductDto.getProductCategory();
            if ( !StringUtils.isEmpty(productCategory) ) categories.add(productCategory);
            String productStore = refProductDto.getProductStore();
            if ( !StringUtils.isEmpty(productStore) ) stores.add(productStore);
            if ( !refProductDto.isDefaultImage() && defaultImage )
            {
                productDto.setId(refProductDto.getId());
                thumbnail = refProductDto.getThumbnailBitmap();
                defaultImage = false;
            }
        }
        int numProducts = accumProducts.size();
        price = price / numProducts;
        quantity = quantity / numProducts;

        productDto.setProductPrice(converterService.getDoubleAsString(price));
        productDto.setQuantity(String.valueOf(quantity));
        productDto.setProductCategory(listToText(categories));
        productDto.setProductStore(listToText(stores));
        productDto.setDefaultImage(defaultImage);
        productDto.setThumbnailBitmap(thumbnail);
        return productDto;
    }

    private String listToText(Set<String> list)
    {
        return list.toString().replace("[", "").replace("]", "");
    }

    @Override
    public Observable<Void> duplicateProducts(String listId)
    {
        Observable<Void> observable = Observable
                .fromCallable(() -> duplicateProductsSync(listId))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private Void duplicateProductsSync(String listId)
    {
        ListDto newList = shoppingListService.getById(listId)
                .doOnNext(listDto ->
                {
                    listDto.setId(null); // new list
                    listDto.setListName(getNewName(listDto));
                })
                .toBlocking().single();

        shoppingListService.saveOrUpdateSync(newList);

        List<ProductDto> products = getAllProductsSync(listId);
        for ( ProductDto product : products )
        {
            copyToListSync(product, newList.getId());
        }
        return null;
    }

    @Override
    public Observable<Void> copyToList(ProductDto product, String listId)
    {
        Observable<Void> observable = Observable
                .fromCallable(() -> copyToListSync(product, listId))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private Void copyToListSync(ProductDto product, String listId)
    {
        String originalProductId = product.getId();
        product.setId(null); // new product
        product.setChecked(false);
        saveOrUpdateSync(product, listId);
        String newProductId = product.getId();
        copyImage(originalProductId, newProductId);
        return null;
    }

    private void copyImage(String sourceProductId, String destProductId)
    {
        File srcImage = new File(getProductImagePath(sourceProductId));
        if ( srcImage.exists() )
        {
            try
            {
                File destFile = new File(getProductImagePath(destProductId));
                copy(srcImage, destFile);
            }
            catch ( IOException e )
            {
                PFALogger.error("ProductServiceImpl", "duplicateProductSync", e);
            }
        }
    }

    @Override
    public Observable<Void> resetCheckedProducts(String listId)
    {
        Observable<Void> observable = Observable
                .fromCallable(() -> resetCheckedProductsSync(listId))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private Void resetCheckedProductsSync(String listId)
    {
        List<ProductDto> products = getAllProductsSync(listId);
        for ( ProductDto product : products )
        {
            product.setChecked(false);
            saveOrUpdateSync(product, listId);
        }
        return null;
    }

    private String getNewName(ListDto listDto)
    {
        return listDto.getListName() + StringUtils.SPACE + context.getResources().getString(R.string.duplicated_suffix);
    }

    @Override
    public Observable<ProductDto> getById(String entityId)
    {
        Observable<ProductDto> observable = Observable
                .fromCallable(() -> getByIdSync(entityId))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private ProductDto getByIdSync(String entityId)
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
    public Observable<Void> deleteById(String id)
    {
        Observable<Void> observable = Observable
                .fromCallable(() -> deleteByIdSync(id))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private Void deleteByIdSync(String id)
    {
        productItemDao.deleteById(Long.valueOf(id));

        // delete imageFile if exists
        File imageFile = new File(getProductImagePath(id));
        imageFile.delete();
        return null;
    }

    @Override
    public Observable<Void> deleteOnlyImage(String id)
    {
        Observable observable = Observable
                .defer(() -> Observable.just(deleteOnlyImageSync(id)))
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
    public Observable<Void> deleteSelected(List<ProductDto> productDtos)
    {
        Observable<Void> observable = Observable
                .fromCallable(() -> deleteSelectedSync(productDtos))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private Void deleteSelectedSync(List<ProductDto> productDtos)
    {
        Observable.from(productDtos)
                .filter(dto -> dto.isSelectedForDeletion())
                .subscribe(dto -> deleteByIdSync(dto.getId()));
        return null;
    }

    @Override
    public Observable<ProductDto> getAllProducts(String listId)
    {
        Observable<ProductDto> observable = Observable
                .defer(() -> Observable.from(getAllProductsSync(listId)))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private List<ProductDto> getAllProductsSync(String listId)
    {
        List<ProductDto> productDtos = new ArrayList<>();

        Observable
                .from(productItemDao.getAllEntities())
                .filter(entity -> entity.getShoppingList().getId() == Long.valueOf(listId))
                .map(this::getDto)
                .subscribe(dto -> productDtos.add(dto));

        return productDtos;
    }

    @Override
    public Observable<ProductDto> getAllProducts()
    {
        Observable<ProductDto> observable = Observable
                .defer(() -> Observable.from(getAllProductsSync()))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private List<ProductDto> getAllProductsSync()
    {
        List<ProductDto> productDtos = new ArrayList<>();

        Observable
                .from(productItemDao.getAllEntities())
                .map(this::getDto)
                .subscribe(dto -> productDtos.add(dto));

        return productDtos;
    }

    @Override
    public Observable<TotalDto> getInfo(String listId)
    {
        Observable<TotalDto> observable = Observable
                .fromCallable(() -> getInfoSync(listId))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private TotalDto getInfoSync(String listId)
    {
        List<ProductDto> productDtos = getAllProductsSync(listId);
        TotalDto totalDto = computeTotals(productDtos);
        return totalDto;
    }

    @Override
    public Observable<Void> deleteAllFromList(String listId)
    {
        Observable<Void> observable = Observable
                .fromCallable(() -> deleteAllFromListSync(listId))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private Void deleteAllFromListSync(String listId)
    {
        List<ProductDto> productDtos = getAllProductsSync(listId);
        Observable.from(productDtos)
                .subscribe(dto -> deleteByIdSync(dto.getId()));
        return null;
    }

    @Override
    public List<ProductDto> moveSelectedToEnd(List<ProductDto> productDtos)
    {
        List<ProductDto> nonSelectedDtos = new ArrayList<>();
        Observable
                .from(productDtos)
                .filter(dto -> !dto.isChecked())
                .subscribe(dto -> nonSelectedDtos.add(dto));

        List<ProductDto> selectedDtos = new ArrayList<>();
        Observable
                .from(productDtos)
                .filter(dto -> dto.isChecked())
                .subscribe(dto -> selectedDtos.add(dto));

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

        NumberScale numberScale = getNumberScale(totalAmount);
        totalAmount = totalAmount / numberScale.getValue(context);
        checkedAmount = checkedAmount / numberScale.getValue(context);

        String totalAmountString = converterService.getDoubleAsString(totalAmount);
        String checkedAmountString = converterService.getDoubleAsString(checkedAmount);
        totalDto.setTotalAmount(totalAmountString + StringUtils.SPACE + numberScale.getAbbreviation(context));
        totalDto.setCheckedAmount(checkedAmountString + StringUtils.SPACE + numberScale.getAbbreviation(context));
        totalDto.setNrProducts(nrProducts);

        return totalDto;
    }

    private NumberScale getNumberScale(double value)
    {
        NumberScale numberScale = NumberScale.NO_SCALE;
        if ( value > NumberScale.MILLION.getValue(context) )
        {
            numberScale = NumberScale.MILLION;
        }
        // use kilo scale if value greatuer than 100,000.00
        else if ( value > NumberScale.KILO.getValue(context) * 100 )
        {
            numberScale = NumberScale.KILO;
        }
        return numberScale;
    }

    @Override
    public Boolean isSearched(String[] searchedTexts, ProductDto dto)
    {
        String name = dto.getProductName().toLowerCase();
        String category = dto.getProductCategory().toLowerCase();
        String store = dto.getProductStore().toLowerCase();
        String notes = dto.getProductNotes().toLowerCase();
        String searchableText = name + StringUtils.SPACE + category + StringUtils.SPACE + store + StringUtils.SPACE + notes;
        for ( String searchedText : searchedTexts )
        {
            if ( searchableText.contains(searchedText.toLowerCase()) )
            {
                return true;
            }
        }
        return false;
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
                .defer(() -> Observable.just(getAutoCompleteLists()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation());
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
    public Observable<Boolean> deleteInvisibleProductsFromDb(List<String> listIds)
    {
        Observable<Boolean> observable = Observable.from(productItemDao.getAllEntities())
                .filter(entity -> !listIds.contains(String.valueOf(entity.getShoppingList().getId())))
                .map(entity -> productItemDao.deleteById(entity.getId()))
                .subscribeOn(Schedulers.computation());
        return observable;
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

    private void copy(File src, File dst) throws IOException
    {
        FileInputStream inStream = new FileInputStream(src);
        FileOutputStream outStream = new FileOutputStream(dst);
        FileChannel inChannel = inStream.getChannel();
        FileChannel outChannel = outStream.getChannel();
        inChannel.transferTo(0, inChannel.size(), outChannel);
        inStream.close();
        outStream.close();
    }
}
