package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.fab;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;

/**
 * Description: This class is used by the xml.
 * Source: https://guides.codepath.com/android/floating-action-buttons
 * Created: 16.07.16 creation date
 */
public class ScrollAwareFabBehaviorForDeleteActivities extends ScrollAwareFabBehavior
{
    public ScrollAwareFabBehaviorForDeleteActivities(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target)
    {
        super.onStopNestedScroll(coordinatorLayout, child, target);
        child.show();
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dx, int dy, int[] consumed)
    {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        child.hide();
    }
}
