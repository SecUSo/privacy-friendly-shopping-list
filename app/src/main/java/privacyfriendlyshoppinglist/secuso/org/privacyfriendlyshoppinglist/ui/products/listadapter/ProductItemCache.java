package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.listadapter;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.*;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 20.07.16 creation date
 */
public class ProductItemCache
{
    private TextView productNameTextView;
    private TextView quantityTextView;
    private TextView productExtraInfoTextview;
    private CheckBox checkbox;
    private CardView productCard;
    private ImageButton showDetailsImageButton;
    private ImageView productImageInViewer;
    private ImageView productImageInDetail;
    private boolean detailsVisible;
    private TextView listDetailsTextView;
    private RelativeLayout detailsLayout;
    private Button plusButton;

    public ProductItemCache(View parent)
    {
        productNameTextView = (TextView) parent.findViewById(R.id.textview_product_name);
        quantityTextView = (TextView) parent.findViewById(R.id.textview_prod_quantity);
        productExtraInfoTextview = (TextView) parent.findViewById(R.id.textview_product_info);
        checkbox = (CheckBox) parent.findViewById(R.id.checkbox_is_selected);
        productCard = (CardView) parent.findViewById(R.id.cardview_item);
        showDetailsImageButton = (ImageButton) parent.findViewById(R.id.expand_button_list);
        productImageInViewer = (ImageView) parent.findViewById(R.id.product_image_in_viewer);
        productImageInDetail = (ImageView) parent.findViewById(R.id.product_image_in_detail);
        listDetailsTextView = (TextView) parent.findViewById(R.id.textview_list_details);
        detailsLayout = (RelativeLayout) parent.findViewById(R.id.layout_details);
        plusButton = (Button) parent.findViewById(R.id.product_button_plus);
    }

    public TextView getProductNameTextView()
    {
        return productNameTextView;
    }

    public TextView getQuantityTextView()
    {
        return quantityTextView;
    }

    public TextView getProductExtraInfoTextview()
    {
        return productExtraInfoTextview;
    }

    public CheckBox getCheckbox()
    {
        return checkbox;
    }

    public CardView getProductCard()
    {
        return productCard;
    }

    public ImageButton getShowDetailsImageButton()
    {
        return showDetailsImageButton;
    }

    public boolean isDetailsVisible()
    {
        return detailsVisible;
    }

    public void setDetailsVisible(boolean detailsVisible)
    {
        this.detailsVisible = detailsVisible;
    }

    public TextView getListDetailsTextView()
    {
        return listDetailsTextView;
    }

    public ImageView getProductImageInViewer()
    {
        return productImageInViewer;
    }

    public ImageView getProductImageInDetail()
    {
        return productImageInDetail;
    }

    public RelativeLayout getDetailsLayout()
    {
        return detailsLayout;
    }

    public Button getPlusButton()
    {
        return plusButton;
    }
}
