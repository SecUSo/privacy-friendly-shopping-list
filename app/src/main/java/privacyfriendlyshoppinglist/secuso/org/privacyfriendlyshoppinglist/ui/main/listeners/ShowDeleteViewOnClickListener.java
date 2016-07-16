package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.listeners;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.deletelists.DeleteListsActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.ShoppingListCache;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.07.16 creation date
 */
public class ShowDeleteViewOnClickListener implements View.OnClickListener
{
    ShoppingListCache cache;

    public ShowDeleteViewOnClickListener(ShoppingListCache cache)
    {
        this.cache = cache;
    }

    @Override
    public void onClick(View v)
    {
        AppCompatActivity activity = cache.getActivity();
        Intent intent = new Intent(activity, DeleteListsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }
}
