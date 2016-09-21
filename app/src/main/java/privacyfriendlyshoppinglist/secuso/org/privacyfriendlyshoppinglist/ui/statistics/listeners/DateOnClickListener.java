package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.statistics.listeners;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import org.joda.time.DateTime;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.logger.PFALogger;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.DateUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.domain.StatisticsQuery;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.statistics.StatisticsCache;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 23.07.16 creation date
 */
class DateOnClickListener implements View.OnClickListener
{
    boolean dataFrom;

    private static final int COMPATIBILITY_FACTOR = 1;
    private TextView referenceTextView;
    private StatisticsCache cache;
    private StatisticsQuery query;

    DateOnClickListener(StatisticsCache cache, StatisticsQuery query, TextView referenceTextView)
    {
        this.cache = cache;
        this.referenceTextView = referenceTextView;
        this.query = query;
    }

    @Override
    public void onClick(View v)
    {
        PFALogger.debug(getClass().getSimpleName(), "onClick", "user clicked select date");

        String fullDate = (String) referenceTextView.getText();
        String datePattern = cache.getDatePattern();
        String dateLanguage = cache.getDateLanguage();
        DateTime date = DateUtils.getDateFromString(fullDate, datePattern, dateLanguage);

        DatePickerDialog datePickerDialog;
        datePickerDialog = new DatePickerDialog(cache.getActivity(), new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(final DatePicker dp, final int currentYear,
                                  final int currentMonth, final int currentDay)


            {
                Calendar currentDate = new GregorianCalendar();
                currentDate.set(
                        currentYear,
                        currentMonth,
                        currentDay,
                        date.getHourOfDay(),
                        date.getMinuteOfDay());

                String stringDate = DateUtils.getDateAsString(currentDate.getTimeInMillis(), datePattern, dateLanguage);
                if ( dataFrom )
                {
                    query.setDateFrom(stringDate);
                }
                else
                {
                    query.setDateTo(stringDate);
                }
                query.notifyObservers();
                referenceTextView.setText(stringDate);
            }
        },
                date.getYear(),
                date.getMonthOfYear() - COMPATIBILITY_FACTOR,
                date.getDayOfMonth());

        datePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, cache.getActivity().getResources().getString(R.string.okay), datePickerDialog);
        datePickerDialog.show();
    }
}
