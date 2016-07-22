package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.statistics;

import android.view.MotionEvent;
import android.view.View;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

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
    private static final String EMPTY = "";

    private List<Double> data;
    private BarChart chart;
    private BarDataSet dataSet;

    public PFAChart(BarChart chart)
    {
        this.chart = chart;
        this.chart.setDrawBarShadow(false);
        this.chart.setDrawValueAboveBar(true);
        this.chart.setDescription(EMPTY);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        this.chart.setMaxVisibleValueCount(60);
        this.chart.setPinchZoom(false);
        this.chart.setDrawGridBackground(false);

        YAxis leftAxis = this.chart.getAxisLeft();
        leftAxis.setLabelCount(10, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(20f);
        leftAxis.setAxisMinValue(0f);

        YAxis rightAxis = this.chart.getAxisRight();
        rightAxis.setEnabled(false);
    }

    public BarChart getChart()
    {
        return chart;
    }

    public BarDataSet getDataSet()
    {
        return dataSet;
    }

    public void setXlabels(String[] xlabels)
    {
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(5);
        xAxis.setValueFormatter(new AxisLabels(xlabels));

    }

    public void updateChart(List<Double> inputData, int... colors)
    {
        this.data = inputData;
        int count = this.data.size();

        float start = 0f;
        chart.getXAxis().setAxisMinValue(start);
        chart.getXAxis().setAxisMaxValue(start + count + 1);

        ArrayList<BarEntry> yValues = new ArrayList<BarEntry>();

        for ( int i = (int) start; i < start + count; i++ )
        {
            BigDecimal number = new BigDecimal(data.get(i));
            yValues.add(new BarEntry(i + 1, number.floatValue()));
        }



        if ( chart.getData() != null && chart.getData().getDataSetCount() > 0 )
        {
            dataSet = (BarDataSet) chart.getData().getDataSetByIndex(0);
            dataSet.setValues(yValues);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        }
        else
        {
            dataSet = new BarDataSet(yValues, EMPTY);
            dataSet.setColors(colors);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(dataSet);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);
            chart.setOnTouchListener(new View.OnTouchListener()
            {
                @Override
                public boolean onTouch(View v, MotionEvent event)
                {
                    updateValuesVisibility();
                    return false;
                }
            });

            chart.setData(data);
            updateValuesVisibility();
        }
    }

    public void updateValuesVisibility()
    {
        float visibleXRange = chart.getVisibleXRange();
        if ( visibleXRange <= 15 )
        {
            dataSet.setDrawValues(true);
        }
        else
        {
            dataSet.setDrawValues(false);
        }
    }
}
