package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppingList;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 12.06.2016.
 */
public class ShoppingListFragement extends Fragment
{
    Activity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.shopping_list_layout, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        setupRecyclerView(recyclerView);

        container.removeAllViews();
        return rootView;

    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }


}
