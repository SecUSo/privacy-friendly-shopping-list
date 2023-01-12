package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.fab;

import android.content.Context;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Description: This class is used by the xml.
 * Source: https://guides.codepath.com/android/floating-action-buttons
 * Created: 16.07.16 creation date
 */
public class ScrollAwareFabBehaviorForDeleteActivities extends FloatingActionButton.Behavior
{
    public ScrollAwareFabBehaviorForDeleteActivities(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(
            CoordinatorLayout coordinatorLayout,
            FloatingActionButton child,
            View directTargetChild,
            View target,
            int nestedScrollAxes)
    {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target)
    {
        child.show();
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dx, int dy, int[] consumed)
    {
        child.hide();
    }
}
