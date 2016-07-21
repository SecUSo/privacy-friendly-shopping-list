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
    private TextView productName;
    private TextView nrProducts;
    private CheckBox isSelected;
    private CardView listCard;

    public ProductItemCache(View parent)
    {
        productName = (TextView) parent.findViewById(R.id.textview_product_name);
        nrProducts = (TextView) parent.findViewById(R.id.textview_prod_quantity);
        isSelected = (CheckBox) parent.findViewById(R.id.checkbox_is_selected);
        listCard = (CardView) parent.findViewById(R.id.cardview_item);
    }

    public TextView getProductName()
    {
        return productName;
    }

    public TextView getNrProducts()
    {
        return nrProducts;
    }

    public CheckBox getIsSelected()
    {
        return isSelected;
    }

    public CardView getListCard()
    {
        return listCard;
    }
}
