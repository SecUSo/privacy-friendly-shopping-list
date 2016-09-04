package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.statistics.chart;

import android.content.Context;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils.StringUtils;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.domain.StatisticsQuery;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.statistics.StatisticsCache;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * This class uses MPAndroidChart
 * <p>
 * Copyright 2016 Philipp Jahoda
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
public class PFAChart
{
    private static final int ANIMATION_DURATION = 2000;
    private BarChart chart;
    private Context context;
    private StatisticsCache cache;

    public PFAChart(StatisticsCache cache)
    {
        this.cache = cache;
        this.context = cache.getActivity().getApplicationContext();

        this.chart = cache.getChart();
        this.chart.setDrawBarShadow(false);
        this.chart.setDrawValueAboveBar(true);
        this.chart.setDescription(StringUtils.EMPTY);
        this.chart.setMaxVisibleValueCount(12);
        this.chart.setPinchZoom(false);
        this.chart.setDrawGridBackground(false);
        this.chart.getLegend().setEnabled(false);
        setupYAxis();
    }

    public void updateChart(List<Double> inputData, List<String> labels, int... colors)
    {
        int count = inputData.size();
        setupYAxis();
        setXlabels(labels);

        float start = 0f;
        chart.getXAxis().setAxisMinValue(start);
        chart.getXAxis().setAxisMaxValue(start + count + 1);

        ArrayList<BarEntry> yValues = new ArrayList<>();

        for ( int i = (int) start; i < start + count; i++ )
        {
            BigDecimal number = new BigDecimal(inputData.get(i));
            yValues.add(new BarEntry(i + 1, number.floatValue()));
        }

        BarDataSet dataSet;
        int valuesSelectedItemPos = cache.getValuesSpinner().getSelectedItemPosition();

        if ( chart.getData() != null && chart.getData().getDataSetCount() > 0 )
        {
            dataSet = (BarDataSet) chart.getData().getDataSetByIndex(0);
            dataSet.setValues(yValues);

            chart.getData().setValueFormatter(new PFAValueFormatter(context, valuesSelectedItemPos, cache.getNumberScale()));

            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
            chart.invalidate();
        }
        else
        {
            dataSet = new BarDataSet(yValues, StringUtils.EMPTY);
            dataSet.setColors(colors);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(dataSet);

            BarData data = new BarData(dataSets);
            data.setValueFormatter(new PFAValueFormatter(context, valuesSelectedItemPos, cache.getNumberScale()));
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);
            chart.setData(data);
        }
        chart.animateY(ANIMATION_DURATION);
        chart.fitScreen();
    }

    private void setXlabels(List<String> labelList)
    {
        int valuesSelectedItemPos = cache.getValuesSpinner().getSelectedItemPosition();
        String[] labels = new String[ labelList.size() ];
        labelList.toArray(labels);

        PFAXAxisLabels xFormatter = new PFAXAxisLabels(labels);
        chart.setMarkerView(new PFAMarkerView(context, xFormatter, valuesSelectedItemPos, cache.getNumberScale()));

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(5);
        xAxis.setValueFormatter(xFormatter);
    }

    private void setupYAxis()
    {
        int valuesSelectedItemPos = cache.getValuesSpinner().getSelectedItemPosition();
        YAxis leftAxis = this.chart.getAxisLeft();
        leftAxis.setLabelCount(10, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(20f);
        leftAxis.setAxisMinValue(0f);
        leftAxis.setValueFormatter(new PFAYAxisLabels(context, valuesSelectedItemPos, cache.getNumberScale()));

        if ( valuesSelectedItemPos == StatisticsQuery.QUANTITY )
        {
            leftAxis.setGranularity(1f); // interval 1
        }

        YAxis rightAxis = this.chart.getAxisRight();
        rightAxis.setEnabled(false);
    }
}
