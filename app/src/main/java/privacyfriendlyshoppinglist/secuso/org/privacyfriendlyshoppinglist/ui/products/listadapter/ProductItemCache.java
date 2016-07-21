package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.listadapter;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.CheckBox;
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
    private CheckBox checkbox;
    private CardView productCard;

    public ProductItemCache(View parent)
    {
        productNameTextView = (TextView) parent.findViewById(R.id.textview_product_name);
        quantityTextView = (TextView) parent.findViewById(R.id.textview_prod_quantity);
        checkbox = (CheckBox) parent.findViewById(R.id.checkbox_is_selected);
        productCard = (CardView) parent.findViewById(R.id.cardview_item);
    }

    public TextView getProductNameTextView()
    {
        return productNameTextView;
    }

    public TextView getQuantityTextView()
    {
        return quantityTextView;
    }

    public CheckBox getCheckbox()
    {
        return checkbox;
    }

    public CardView getProductCard()
    {
        return productCard;
    }
}
