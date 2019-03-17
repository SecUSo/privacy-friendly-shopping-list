package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.listadapter;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Paint;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.ui.AbstractViewHolder;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.ProductService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.StatisticsService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.PhotoPreviewActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductActivityCache;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductsActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.dialog.EditDeleteProductDialog;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.dialog.ProductDialogFragment;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 20.07.16 creation date
 */
public class ProductsItemViewHolder extends AbstractViewHolder<ProductItem, ProductActivityCache>
{
    private ProductItemCache productItemCache;
    private ProductService productService;
    private StatisticsService statisticsService;

    public ProductsItemViewHolder(final View parent, ProductActivityCache cache)
    {
        super(parent, cache);
        this.productItemCache = new ProductItemCache(parent);
        AbstractInstanceFactory instanceFactory = new InstanceFactory(this.cache.getActivity());
        this.productService = (ProductService) instanceFactory.createInstance(ProductService.class);
        this.statisticsService = (StatisticsService) instanceFactory.createInstance(StatisticsService.class);

    }

    public void processItem(ProductItem item)
    {
        final CheckBox checkbox = productItemCache.getCheckbox();
        checkbox.setChecked(item.isChecked());
        productItemCache.getProductNameTextView().setText(item.getProductName());
        productItemCache.getQuantityTextView().setText(item.getQuantity());
        productItemCache.getProductExtraInfoTextview().setText(item.getSummary(cache.getActivity()));
        productItemCache.getListDetailsTextView().setText(item.getDetailInfo(cache.getActivity()));

        Button plusButton = productItemCache.getPlusButton();
        if ( !item.getListId().equals(cache.getListId()) )
        {
            plusButton.setVisibility(View.VISIBLE);
        }
        else
        {
            plusButton.setVisibility(View.GONE);
        }

        if ( !item.isDefaultImage() )
        {
            productItemCache.getProductImageInDetail().setVisibility(View.VISIBLE);
            productItemCache.getProductImageInDetail().setImageBitmap(item.getThumbnailBitmap());
        }
        else
        {
            productItemCache.getProductImageInDetail().setVisibility(View.GONE);
            productItemCache.getProductImageInDetail().setImageBitmap(null);
        }

        productItemCache.getProductImageInDetail().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent viewPhotoIntent = new Intent(cache.getActivity(), PhotoPreviewActivity.class);
                viewPhotoIntent.putExtra(ProductsActivity.PRODUCT_ID_KEY, item.getId());
                viewPhotoIntent.putExtra(ProductsActivity.PRODUCT_NAME, item.getProductName());
                viewPhotoIntent.putExtra(ProductsActivity.FROM_DIALOG, false);
                ProductsActivity activity = (ProductsActivity) cache.getActivity();
                activity.startActivityForResult(viewPhotoIntent, ProductsActivity.REQUEST_PHOTO_PREVIEW_FROM_ITEM);
            }
        });

        updateVisibilityFormat(item);

        checkbox.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                item.setChecked(checkbox.isChecked());
                productService.saveOrUpdate(item, cache.getListId())
                        .doOnError(Throwable::printStackTrace).subscribe();
                if ( checkbox.isChecked() && cache.getStatisticsEnabled() )
                {
                    statisticsService.saveRecord(item)
                            .doOnError(Throwable::printStackTrace).subscribe();
                }

                ProductsActivity host = (ProductsActivity) cache.getActivity();
                host.updateTotals();
                host.changeItemPosition(item);

                updateVisibilityFormat(item);
            }
        });

        plusButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                productService.copyToList(item, cache.getListId())
                        .doOnCompleted(() ->
                        {
                            plusButton.setVisibility(View.GONE);
                        })
                        .doOnError(Throwable::printStackTrace).subscribe();
            }
        });


        productItemCache.getProductCard().setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if ( !ProductDialogFragment.isOpened() )
                {
                    DialogFragment productFragement = ProductDialogFragment.newEditDialogInstance(item, cache);
                    productFragement.show(cache.getActivity().getSupportFragmentManager(), "Product");
                }

            }
        });

        productItemCache.getProductCard().setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                DialogFragment editDeleteFragment = EditDeleteProductDialog.newEditDeleteInstance(item, cache);
                editDeleteFragment.show(cache.getActivity().getSupportFragmentManager(), "Product");
                return true;
            }
        });

        ImageButton showDetailsButton = productItemCache.getShowDetailsImageButton();
        showDetailsButton.setOnClickListener(v ->
        {
            productItemCache.setDetailsVisible(!productItemCache.isDetailsVisible());
            if ( productItemCache.isDetailsVisible() )
            {
                showDetailsButton.setImageResource(R.drawable.ic_keyboard_arrow_up_white_48sp);
                productItemCache.getDetailsLayout().setVisibility(View.VISIBLE);

            }
            else
            {
                showDetailsButton.setImageResource(R.drawable.ic_keyboard_arrow_down_white_48sp);
                productItemCache.getDetailsLayout().setVisibility(View.GONE);
            }
        });

    }

    private void updateVisibilityFormat(ProductItem item)
    {
        Resources resources = cache.getActivity().getResources();
        TextView productNameTextView = productItemCache.getProductNameTextView();
        TextView productQuantityTextView = productItemCache.getQuantityTextView();
        TextView quantityTextView = productItemCache.getQuantityTextView();
        CardView productCard = productItemCache.getProductCard();
        AppCompatCheckBox checkbox = (AppCompatCheckBox) productItemCache.getCheckbox();

        if ( item.isChecked() )
        {
            int grey = resources.getColor(R.color.middlegrey);
            productCard.setCardBackgroundColor(resources.getColor(R.color.transparent));
            checkbox.setSupportButtonTintList(ColorStateList.valueOf(resources.getColor(R.color.middleblue)));
            productNameTextView.setTextColor(grey);
            quantityTextView.setTextColor(grey);
            productNameTextView.setPaintFlags(productNameTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            productQuantityTextView.setPaintFlags(productQuantityTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else
        {
            int black = resources.getColor(R.color.black);
            productCard.setCardBackgroundColor(resources.getColor(R.color.white));
            checkbox.setSupportButtonTintList(ColorStateList.valueOf(resources.getColor(R.color.colorAccent)));
            productNameTextView.setTextColor(black);
            quantityTextView.setTextColor(black);
            productNameTextView.setPaintFlags(productNameTextView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            productQuantityTextView.setPaintFlags(productQuantityTextView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

    }
}
