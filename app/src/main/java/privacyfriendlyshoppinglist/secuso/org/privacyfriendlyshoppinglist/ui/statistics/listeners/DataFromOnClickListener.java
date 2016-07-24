package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.statistics.listeners;

import android.widget.TextView;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.domain.StatisticsQuery;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.statistics.StatisticsCache;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 23.07.16 creation date
 */
public class DataFromOnClickListener extends DateOnClickListener
{
    public DataFromOnClickListener(StatisticsCache cache, StatisticsQuery query, TextView referenceTextView)
    {
        super(cache, query, referenceTextView);
        dataFrom = true;
    }
}
