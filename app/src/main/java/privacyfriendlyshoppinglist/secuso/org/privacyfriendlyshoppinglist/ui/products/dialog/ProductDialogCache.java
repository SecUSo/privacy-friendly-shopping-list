package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.dialog;

import android.view.View;
import android.widget.*;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 30.07.16 creation date
 */
public class ProductDialogCache
{

    private LinearLayout expandableLayout;
    private ImageView expandableImageView;

    private AutoCompleteTextView productName;
    private EditText quantity;
    private EditText price;
    private AutoCompleteTextView customStore;
    private AutoCompleteTextView category;
    private EditText productNotes;

    private Button buttonPlus;
    private Button buttonMinus;
    private CheckBox productCheckBox;

    private ImageView cameraIcon;
    private ImageView productImage;
    private TextView titleTextView;

    public ProductDialogCache(View rootview)
    {
        productName = (AutoCompleteTextView) rootview.findViewById(R.id.product_name);
        quantity = (EditText) rootview.findViewById(R.id.quantity);
        price = (EditText) rootview.findViewById(R.id.product_price);
        customStore = (AutoCompleteTextView) rootview.findViewById(R.id.store_input);
        category = (AutoCompleteTextView) rootview.findViewById(R.id.category_input);
        productNotes = (EditText) rootview.findViewById(R.id.product_notes);
        buttonPlus = (Button) rootview.findViewById(R.id.product_button_plus);
        buttonMinus = (Button) rootview.findViewById(R.id.product_button_minus);
        productCheckBox = (CheckBox) rootview.findViewById(R.id.product_checkbox);
        expandableLayout = (LinearLayout) rootview.findViewById(R.id.expandable_product_view);
        expandableImageView = (ImageView) rootview.findViewById(R.id.expand_button);
        cameraIcon = (ImageView) rootview.findViewById(R.id.camera_button);
        productImage = (ImageView) rootview.findViewById(R.id.image_view);
        titleTextView = (TextView) rootview.findViewById(R.id.dialog_title);
    }

    public LinearLayout getExpandableLayout()
    {
        return expandableLayout;
    }

    public ImageView getExpandableImageView()
    {
        return expandableImageView;
    }

    public AutoCompleteTextView getProductName()
    {
        return productName;
    }

    public EditText getQuantity()
    {
        return quantity;
    }

    public EditText getPrice()
    {
        return price;
    }

    public AutoCompleteTextView getCustomStore()
    {
        return customStore;
    }

    public AutoCompleteTextView getCategory()
    {
        return category;
    }

    public EditText getProductNotes()
    {
        return productNotes;
    }

    public Button getButtonPlus()
    {
        return buttonPlus;
    }

    public Button getButtonMinus()
    {
        return buttonMinus;
    }

    public CheckBox getProductCheckBox()
    {
        return productCheckBox;
    }

    public ImageView getCameraIcon()
    {
        return cameraIcon;
    }

    public ImageView getProductImage()
    {
        return productImage;
    }

    public TextView getTitleTextView()
    {
        return titleTextView;
    }
}
