package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.fab;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;


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
