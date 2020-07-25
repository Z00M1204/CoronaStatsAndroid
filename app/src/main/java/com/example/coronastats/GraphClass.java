package com.example.coronastats;

import android.content.Context;
import android.graphics.Color;

import androidx.annotation.ColorRes;
import androidx.core.content.res.ResourcesCompat;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

public class GraphClass{

    public void setUpGraph (GraphView maingraph, LineGraphSeries mainseries) {

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6),
                new DataPoint(7, 8),
                new DataPoint(9, 10),
                new DataPoint(11, 20),
                new DataPoint(15, 34),
                new DataPoint(19, 55)

        });

        //Changing the style of the graph
        maingraph.getViewport().setYAxisBoundsManual(true);
        maingraph.getViewport().setMinY(1);
        maingraph.getViewport().setMaxY(55);

        maingraph.getViewport().setXAxisBoundsManual(true);
        maingraph.getViewport().setMinX(0);
        maingraph.getViewport().setMaxX(19);

        maingraph.getViewport().setScalable(true);
        maingraph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
        maingraph.getGridLabelRenderer().setVerticalLabelsVisible(false);
        maingraph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        maingraph.setBackgroundColor(Color.TRANSPARENT);
        series.setColor(Color.WHITE);
        series.setDrawDataPoints(true);
        series.setAnimated(true);
        series.setThickness(6);

        maingraph.addSeries(series);
    }
}
