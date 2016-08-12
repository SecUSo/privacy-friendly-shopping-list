package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist.listadapter;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 09.07.16 creation date
 */
public class ListItemCache
{
    private TextView listNameTextView;
    private TextView nrProductsTextView;
    private TextView notesTextView;
    private CardView listCard;
    private ImageView highPriorityImageView;

    public ListItemCache(View parent)
    {
        listNameTextView = (TextView) parent.findViewById(R.id.textview_list_name);
        notesTextView = (TextView) parent.findViewById(R.id.textview_list_info);
        nrProductsTextView = (TextView) parent.findViewById(R.id.textview_prod_quantity);
        listCard = (CardView) parent.findViewById(R.id.cardview_item);
        highPriorityImageView = (ImageView) parent.findViewById(R.id.imageview_high_prio_icon);
    }

    public TextView getListNameTextView()
    {
        return listNameTextView;
    }

    public TextView getNrProductsTextView()
    {
        return nrProductsTextView;
    }

    public TextView getNotesTextView()
    {
        return notesTextView;
    }

    public CardView getListCard()
    {
        return listCard;
    }

    public ImageView getHighPriorityImageView()
    {
        return highPriorityImageView;
    }
}
