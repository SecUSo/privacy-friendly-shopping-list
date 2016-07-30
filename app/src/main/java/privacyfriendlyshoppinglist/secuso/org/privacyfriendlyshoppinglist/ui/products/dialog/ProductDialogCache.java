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

    private EditText productName;
    private EditText quantity;
    private EditText price;
    private EditText customStore;
    private EditText category;
    private EditText productNotes;

    private Button buttonPlus;
    private Button buttonMinus;
    private CheckBox productCheckBox;

    private Button cameraButton;
    private ImageView cameraImage;

    public ProductDialogCache(View rootview)
    {
        productName = (EditText) rootview.findViewById(R.id.product_name);
        quantity = (EditText) rootview.findViewById(R.id.quantity);
        price = (EditText) rootview.findViewById(R.id.product_price);
        customStore = (EditText) rootview.findViewById(R.id.store_input);
        category = (EditText) rootview.findViewById(R.id.category_input);
        productNotes = (EditText) rootview.findViewById(R.id.product_notes);
        buttonPlus = (Button) rootview.findViewById(R.id.product_button_plus);
        buttonMinus = (Button) rootview.findViewById(R.id.product_button_minus);
        productCheckBox = (CheckBox) rootview.findViewById(R.id.product_checkbox);
        expandableLayout = (LinearLayout) rootview.findViewById(R.id.expandable_product_view);
        expandableImageView = (ImageView) rootview.findViewById(R.id.expand_button);
        cameraButton = (Button) rootview.findViewById(R.id.camera_button);
        cameraImage = (ImageView) rootview.findViewById(R.id.image_view);
    }

    public LinearLayout getExpandableLayout()
    {
        return expandableLayout;
    }

    public ImageView getExpandableImageView()
    {
        return expandableImageView;
    }

    public EditText getProductName()
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

    public EditText getCustomStore()
    {
        return customStore;
    }

    public EditText getCategory()
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

    public Button getCameraButton()
    {
        return cameraButton;
    }

    public ImageView getCameraImage()
    {
        return cameraImage;
    }
}
