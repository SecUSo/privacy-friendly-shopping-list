package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.statistics.listeners;

import android.view.View;
import android.widget.AdapterView;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.domain.StatisticsQuery;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 23.07.16 creation date
 */
public class GroupBySpinnerOnItemSelectListener implements AdapterView.OnItemSelectedListener
{
    private StatisticsQuery query;

    public GroupBySpinnerOnItemSelectListener(StatisticsQuery query)
    {
        this.query = query;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        query.setGroupBy(position);
        query.notifyObservers();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }
}
