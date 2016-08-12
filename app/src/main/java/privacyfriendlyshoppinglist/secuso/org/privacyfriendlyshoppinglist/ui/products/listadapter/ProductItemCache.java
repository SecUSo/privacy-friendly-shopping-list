package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.listadapter;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
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
    private boolean detailsVisible;
    private TextView listDetails;

    public ProductItemCache(View parent)
    {
        productNameTextView = (TextView) parent.findViewById(R.id.textview_product_name);
        quantityTextView = (TextView) parent.findViewById(R.id.textview_prod_quantity);
        productExtraInfoTextview = (TextView) parent.findViewById(R.id.textview_product_info);
        checkbox = (CheckBox) parent.findViewById(R.id.checkbox_is_selected);
        productCard = (CardView) parent.findViewById(R.id.cardview_item);
        showDetailsImageButton = (ImageButton) parent.findViewById(R.id.expand_button_list);
        listDetails = (TextView) parent.findViewById(R.id.textview_list_details);
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

    public TextView getListDetails()
    {
        return listDetails;
    }
}
