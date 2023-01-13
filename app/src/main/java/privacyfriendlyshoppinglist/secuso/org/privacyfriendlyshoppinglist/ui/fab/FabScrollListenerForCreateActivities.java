package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.fab;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class FabScrollListenerForCreateActivities extends RecyclerView.OnScrollListener {
    private final FloatingActionButton fab;

    public FabScrollListenerForCreateActivities(FloatingActionButton fab) {
        this.fab = fab;
    }

    /**
     * Hide the FloatingActionButton when scrolling down, show when scrolling up.
     *
     * @param recyclerView
     * @param dx
     * @param dy
     */
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy)
    {
        if (dy > 0 && fab.isShown())
        {
            fab.hide();
        }
        else if (dy < 0 && !fab.isShown())
        {
            fab.show();
        }
    }
}
