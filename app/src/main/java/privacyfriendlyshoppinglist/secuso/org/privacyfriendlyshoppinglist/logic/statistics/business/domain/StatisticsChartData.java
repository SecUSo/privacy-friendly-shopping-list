package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.domain;

import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.statistics.chart.NumberScale;

import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 23.07.16 creation date
 */
public class StatisticsChartData
{
    private List<Double> data;

    private List<String> labels;

    private Double total;

    private String title;

    private NumberScale numberScale;

    public List<Double> getData()
    {
        return data;
    }

    public void setData(List<Double> data)
    {
        this.data = data;
    }

    public List<String> getLabels()
    {
        return labels;
    }

    public void setLabels(List<String> labels)
    {
        this.labels = labels;
    }

    public Double getTotal()
    {
        return total;
    }

    public void setTotal(Double total)
    {
        this.total = total;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public NumberScale getNumberScale()
    {
        return numberScale;
    }

    public void setNumberScale(NumberScale numberScale)
    {
        this.numberScale = numberScale;
    }
}
