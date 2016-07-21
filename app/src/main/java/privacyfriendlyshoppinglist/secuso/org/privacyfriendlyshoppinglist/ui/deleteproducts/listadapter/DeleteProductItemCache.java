package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.deleteproducts.listadapter;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 21.07.16 creation date
 */
public class DeleteProductItemCache
{
    private TextView productNameTextView;
    private TextView productQuantityTextView;
    private CheckBox checkBox;
    private CardView productCard;

    public DeleteProductItemCache(View parent)
    {
        productNameTextView = (TextView) parent.findViewById(R.id.textview_product_name);
        productQuantityTextView = (TextView) parent.findViewById(R.id.textview_prod_quantity);
        checkBox = (CheckBox) parent.findViewById(R.id.checkbox_is_selected);
        productCard = (CardView) parent.findViewById(R.id.cardview_item);
    }

    public TextView getProductNameTextView()
    {
        return productNameTextView;
    }

    public TextView getProductQuantityTextView()
    {
        return productQuantityTextView;
    }

    public CheckBox getCheckBox()
    {
        return checkBox;
    }

    public CardView getProductCard()
    {
        return productCard;
    }
}
