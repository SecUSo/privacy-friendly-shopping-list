package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.impl;

import android.content.Context;
import android.content.ContextWrapper;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.comparators.PFAComparators;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.logger.PFALogger;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.DB;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.StringUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.ProductService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.AutoCompleteLists;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.TotalItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.impl.comparators.ProductComparators;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.impl.converter.ProductConverterService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence.ProductItemDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.persistence.entity.ProductItemEntity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.ShoppingListService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.shoppingList.business.domain.ListItem;
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
import java.util.ArrayList;
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
    public Observable<Void> saveOrUpdate(ProductItem item, String listId)
    {
        Observable<Void> observable = Observable
                .fromCallable(() -> saveOrUpdateSync(item, listId))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private Void saveOrUpdateSync(ProductItem item, String listId)
    {
        ProductItemEntity entity = new ProductItemEntity();
        converterService.convertItemToEntity(item, entity);

        ShoppingListEntity shoppingListEntity = shoppingListService.getEntityByIdSync(listId);
        entity.setShoppingList(shoppingListEntity);

        productItemDao.save(entity);
        item.setId(entity.getId().toString());
        return null;
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
        ListItem newList = shoppingListService.getById(listId)
                .doOnNext(listItem ->
                {
                    listItem.setId(null); // new list
                    listItem.setListName(getNewName(listItem));
                })
                .toBlocking().single();

        shoppingListService.saveOrUpdateSync(newList);

        List<ProductItem> products = getAllProductsSync(listId);
        for ( ProductItem product : products )
        {
            copyToListSync(product, newList.getId());
        }
        return null;
    }

    @Override
    public Observable<Void> copyToList(ProductItem product, String listId)
    {
        Observable<Void> observable = Observable
                .fromCallable(() -> copyToListSync(product, listId))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private Void copyToListSync(ProductItem product, String listId)
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
        List<ProductItem> products = getAllProductsSync(listId);
        for ( ProductItem product : products )
        {
            product.setChecked(false);
            saveOrUpdateSync(product, listId);
        }
        return null;
    }

    private String getNewName(ListItem listItem)
    {
        return listItem.getListName() + StringUtils.SPACE + context.getResources().getString(R.string.duplicated_suffix);
    }

    @Override
    public Observable<ProductItem> getById(String entityId)
    {
        Observable<ProductItem> observable = Observable
                .fromCallable(() -> getByIdSync(entityId))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private ProductItem getByIdSync(String entityId)
    {
        ProductItemEntity productEntity = productItemDao.getById(Long.valueOf(entityId));
        if ( productEntity == null ) return null;

        ProductItem item = new ProductItem();
        converterService.convertEntitiesToItem(productEntity, item);
        return item;
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
    public Observable<Void> deleteSelected(List<ProductItem> productItems)
    {
        Observable<Void> observable = Observable
                .fromCallable(() -> deleteSelectedSync(productItems))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private Void deleteSelectedSync(List<ProductItem> productItems)
    {
        Observable.from(productItems)
                .filter(item -> item.isSelectedForDeletion())
                .subscribe(item -> deleteByIdSync(item.getId()));
        return null;
    }

    @Override
    public Observable<ProductItem> getAllProducts(String listId)
    {
        Observable<ProductItem> observable = Observable
                .defer(() -> Observable.from(getAllProductsSync(listId)))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private List<ProductItem> getAllProductsSync(String listId)
    {
        List<ProductItem> productItems = new ArrayList<>();

        Observable
                .from(productItemDao.getAllEntities())
                .filter(entity -> entity.getShoppingList().getId() == Long.valueOf(listId))
                .map(this::getItem)
                .subscribe(item -> productItems.add(item));

        return productItems;
    }

    @Override
    public Observable<ProductItem> getAllProducts()
    {
        Observable<ProductItem> observable = Observable
                .defer(() -> Observable.from(getAllProductsSync()))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private List<ProductItem> getAllProductsSync()
    {
        List<ProductItem> productDtos = new ArrayList<>();

        Observable
                .from(productItemDao.getAllEntities())
                .map(this::getItem)
                .subscribe(item -> productDtos.add(item));

        return productDtos;
    }

    @Override
    public Observable<TotalItem> getInfo(String listId)
    {
        Observable<TotalItem> observable = Observable
                .fromCallable(() -> getInfoSync(listId))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private TotalItem getInfoSync(String listId)
    {
        List<ProductItem> productItems = getAllProductsSync(listId);
        TotalItem totalItem = computeTotals(productItems);
        return totalItem;
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
        List<ProductItem> productItems = getAllProductsSync(listId);
        Observable.from(productItems)
                .subscribe(item -> deleteByIdSync(item.getId()));
        return null;
    }

    @Override
    public List<ProductItem> moveSelectedToEnd(List<ProductItem> productItems)
    {
        List<ProductItem> nonSelectedItems = new ArrayList<>();
        Observable
                .from(productItems)
                .filter(item -> !item.isChecked())
                .subscribe(item -> nonSelectedItems.add(item));

        List<ProductItem> selectedItems = new ArrayList<>();
        Observable
                .from(productItems)
                .filter(item -> item.isChecked())
                .subscribe(item -> selectedItems.add(item));

        nonSelectedItems.addAll(selectedItems);
        productItems = nonSelectedItems;
        return productItems;
    }

    @Override
    public TotalItem computeTotals(List<ProductItem> productItems)
    {
        double totalAmount = 0.0;
        double checkedAmount = 0.0;
        int nrProducts = 0;
        for ( ProductItem item : productItems )
        {
            Integer quantity = Integer.valueOf(item.getQuantity());
            nrProducts += quantity;
            String price = item.getProductPrice();
            if ( price != null )
            {
                double priceAsDouble = converterService.getStringAsDouble(price) * quantity;
                totalAmount += priceAsDouble;
                if ( item.isChecked() )
                {
                    checkedAmount += priceAsDouble;
                }
            }
        }

        TotalItem totalItem = new TotalItem();

        if ( totalAmount == 0.0 )
        {
            totalItem.setEqualsZero(true);
        }

        NumberScale numberScale = getNumberScale(totalAmount);
        totalAmount = totalAmount / numberScale.getValue(context);
        checkedAmount = checkedAmount / numberScale.getValue(context);

        String totalAmountString = converterService.getDoubleAsString(totalAmount);
        String checkedAmountString = converterService.getDoubleAsString(checkedAmount);
        totalItem.setTotalAmount(totalAmountString + StringUtils.SPACE + numberScale.getAbbreviation(context));
        totalItem.setCheckedAmount(checkedAmountString + StringUtils.SPACE + numberScale.getAbbreviation(context));
        totalItem.setNrProducts(nrProducts);

        return totalItem;
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
    public Boolean isSearched(String[] searchedTexts, ProductItem item)
    {
        String name = item.getProductName().toLowerCase();
        String category = item.getProductCategory().toLowerCase();
        String store = item.getProductStore().toLowerCase();
        String notes = item.getProductNotes().toLowerCase();
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
    public String getSharableText(ProductItem item)
    {
        StringBuilder sb = new StringBuilder();
        sb
                .append(StringUtils.DASH)
                .append(StringUtils.LEFT_BRACE)
                .append(item.getQuantity())
                .append(StringUtils.RIGHT_BRACE)
                .append(item.getProductName());

        if ( !StringUtils.isEmpty(item.getProductNotes()) )
        {
            sb
                    .append(StringUtils.NEW_LINE)
                    .append(StringUtils.NEW_LINE)
                    .append(item.getProductNotes());
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
                .map(this::getItem)
                .subscribe(item -> autoCompleteLists.updateLists(item));
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
    public void sortProducts(List<ProductItem> products, String criteria, boolean ascending)
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

    private ProductItem getItem(ProductItemEntity entity)
    {
        ProductItem item = new ProductItem();
        converterService.convertEntitiesToItem(entity, item);
        return item;
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
