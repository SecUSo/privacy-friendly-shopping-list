package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.listadapter;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Paint;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.ProductService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductDto;
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
public class ProductsItemViewHolder extends RecyclerView.ViewHolder
{
    private ProductItemCache productItemCache;
    private ProductActivityCache productActivityCache;
    private ProductService productService;
    private StatisticsService statisticsService;

    public ProductsItemViewHolder(final View parent, ProductActivityCache cache)
    {
        super(parent);
        this.productItemCache = new ProductItemCache(parent);
        this.productActivityCache = cache;
        AbstractInstanceFactory instanceFactory = new InstanceFactory(productActivityCache.getActivity());
        this.productService = (ProductService) instanceFactory.createInstance(ProductService.class);
        this.statisticsService = (StatisticsService) instanceFactory.createInstance(StatisticsService.class);

    }

    public void processDto(ProductDto dto)
    {
        final CheckBox checkbox = productItemCache.getCheckbox();
        checkbox.setChecked(dto.isChecked());
        productItemCache.getProductNameTextView().setText(dto.getProductName());
        productItemCache.getQuantityTextView().setText(dto.getQuantity());
        productItemCache.getProductExtraInfoTextview().setText(dto.getSummary(productActivityCache.getActivity()));
        productItemCache.getListDetailsTextView().setText(dto.getDetailInfo(productActivityCache.getActivity()));

        if ( !dto.isDefaultImage() )
        {
            productItemCache.getProductImageInDetail().setVisibility(View.VISIBLE);
            productItemCache.getProductImageInDetail().setImageBitmap(dto.getThumbnailBitmap());
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
                Intent viewPhotoIntent = new Intent(productActivityCache.getActivity(), PhotoPreviewActivity.class);
                viewPhotoIntent.putExtra(ProductsActivity.PRODUCT_ID_KEY, dto.getId());
                viewPhotoIntent.putExtra(ProductsActivity.PRODUCT_NAME, dto.getProductName());
                viewPhotoIntent.putExtra(ProductsActivity.FROM_DIALOG, false);
                ProductsActivity activity = (ProductsActivity) productActivityCache.getActivity();
                activity.startActivityForResult(viewPhotoIntent, ProductsActivity.REQUEST_PHOTO_PREVIEW_FROM_ITEM);
            }
        });

        updateVisibilityFormat(dto);

        checkbox.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dto.setChecked(checkbox.isChecked());
                productService.saveOrUpdate(dto, productActivityCache.getListId()).subscribe();
                if ( checkbox.isChecked() && productActivityCache.getStatisticsEnabled() )
                {
                    statisticsService.saveRecord(dto).subscribe();
                }

                ProductsActivity host = (ProductsActivity) productActivityCache.getActivity();
                host.updateTotals();
                host.changeItemPosition(dto);

                updateVisibilityFormat(dto);
            }
        });


        productItemCache.getProductCard().setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if ( !ProductDialogFragment.isOpened() )
                {
                    DialogFragment productFragement = ProductDialogFragment.newEditDialogInstance(dto, productActivityCache);
                    productFragement.show(productActivityCache.getActivity().getSupportFragmentManager(), "Product");
                }

            }
        });

        productItemCache.getProductCard().setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                DialogFragment editDeleteFragment = EditDeleteProductDialog.newEditDeleteInstance(dto, productActivityCache);
                editDeleteFragment.show(productActivityCache.getActivity().getSupportFragmentManager(), "Product");
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

    private void updateVisibilityFormat(ProductDto dto)
    {
        Resources resources = productActivityCache.getActivity().getResources();
        TextView productNameTextView = productItemCache.getProductNameTextView();
        TextView productQuantityTextView = productItemCache.getQuantityTextView();
        TextView quantityTextView = productItemCache.getQuantityTextView();
        CardView productCard = productItemCache.getProductCard();
        AppCompatCheckBox checkbox = (AppCompatCheckBox) productItemCache.getCheckbox();

        if ( dto.isChecked() )
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
