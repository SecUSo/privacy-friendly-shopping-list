package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppingList.ShoppingListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 17.05.16 creation date
 */
public class MainActivity extends AppCompatActivity
{

    @Override
    protected final void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        setupRecyclerView(recyclerView);
    }

    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle(getString(R.string.app_name));
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ShoppingListAdapter shoppingListAdapter = new ShoppingListAdapter(createItemList());
        recyclerView.setAdapter(shoppingListAdapter);
    }

    private List<String> createItemList() {
        List<String> itemList = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                itemList.add("Item " + i);
            }
        return itemList;
    }

    @Override
    protected final void onStart()
    {
        super.onStart();
    }

    @Override
    protected final void onStop()
    {
        super.onStop();
    }
}
