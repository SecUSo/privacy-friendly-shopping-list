package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.deletelists.listadapter;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.07.16 creation date
 */
public class DeleteListItemCache
{
    private TextView listNameTextView;
    private TextView nrProductsTextView;
    private CardView listCard;

    public DeleteListItemCache(View parent)
    {
        listNameTextView = (TextView) parent.findViewById(R.id.textview_list_name);
        nrProductsTextView = (TextView) parent.findViewById(R.id.textview_prod_quantity);
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
