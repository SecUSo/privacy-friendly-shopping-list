package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.sort;

import android.view.View;
import android.widget.RadioButton;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 22.07.16 creation date
 */
public class SortProductsDialogCache
{
    private RadioButton ascending;
    private RadioButton descending;
    private RadioButton name;
    private RadioButton quantity;
    private RadioButton price;
    private RadioButton store;
    private RadioButton category;

    public SortProductsDialogCache(View rootview)
    {
        ascending = (RadioButton) rootview.findViewById(R.id.radiobutton_ascending);
        descending = (RadioButton) rootview.findViewById(R.id.radiobutton_descending);
        name = (RadioButton) rootview.findViewById(R.id.radiobutton_name);
        quantity = (RadioButton) rootview.findViewById(R.id.radiobutton_quantity);
        price = (RadioButton) rootview.findViewById(R.id.radiobutton_price);
        store = (RadioButton) rootview.findViewById(R.id.radiobutton_store);
        category = (RadioButton) rootview.findViewById(R.id.radiobutton_category);
    }

    public RadioButton getAscending()
    {
        return ascending;
    }

    public RadioButton getDescending()
    {
        return descending;
    }

    public RadioButton getName()
    {
        return name;
    }

    public RadioButton getQuantity()
    {
        return quantity;
    }

    public RadioButton getPrice()
    {
        return price;
    }

    public RadioButton getStore()
    {
        return store;
    }

    public RadioButton getCategory()
    {
        return category;
    }
}
