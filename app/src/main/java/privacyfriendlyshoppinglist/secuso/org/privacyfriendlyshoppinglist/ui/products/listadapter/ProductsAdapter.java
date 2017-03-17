package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.listadapter;

import android.preference.PreferenceManager;
import android.view.View;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.ui.AbstractAdapter;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductActivityCache;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.settings.SettingsKeys;

import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 20.07.16 creation date
 */
public class ProductsAdapter extends AbstractAdapter<ProductItem, ProductActivityCache, ProductsItemViewHolder>
{

    public ProductsAdapter(List<ProductItem> productsList, ProductActivityCache cache)
    {
        super(
                productsList,
                cache,
                R.layout.product_list_item);
        setLayoutId(getListItemLayout());
    }

    private int getListItemLayout()
    {
        int listItemLayout;
        if ( PreferenceManager.getDefaultSharedPreferences(cache.getActivity()).getBoolean(SettingsKeys.CHECKBOX_POSITION_PREF, true) )
        {
            listItemLayout = R.layout.product_list_item;
        }
        else
        {
            listItemLayout = R.layout.product_list_item_left_hand;
        }
        return listItemLayout;
    }

    @Override
    protected ProductsItemViewHolder createNewViewHolder(View view)
    {
        return new ProductsItemViewHolder(view, cache);
    }
}
