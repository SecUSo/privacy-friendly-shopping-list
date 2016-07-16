package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist.listadapter;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 09.07.16 creation date
 */
public class ListItemCache
{
    TextView listNameTextView;
    TextView nrProductsTextView;
    CardView listCard;

    public ListItemCache(View parent)
    {
        listNameTextView = (TextView) parent.findViewById(R.id.textview_list_name);
        nrProductsTextView = (TextView) parent.findViewById(R.id.textview_nr_products);
        listCard = (CardView) parent.findViewById(R.id.cardview_item);
    }

    public TextView getListNameTextView()
    {
        return listNameTextView;
    }

    public TextView getNrProductsTextView()
    {
        return nrProductsTextView;
    }

    public CardView getListCard()
    {
        return listCard;
    }
}
