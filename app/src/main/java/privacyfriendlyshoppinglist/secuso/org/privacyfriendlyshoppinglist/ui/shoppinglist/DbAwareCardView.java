package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.shoppinglist;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 09.07.16 creation date
 */
public class DbAwareCardView extends CardView
{
    private Long databaseId;

    public DbAwareCardView(Context context)
    {
        super(context);
    }

    public DbAwareCardView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public DbAwareCardView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public Long getDatabaseId()
    {
        return databaseId;
    }

    public void setDatabaseId(Long databaseId)
    {
        this.databaseId = databaseId;
    }
}
