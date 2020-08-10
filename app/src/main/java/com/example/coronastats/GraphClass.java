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

    //this method is just used for setting up the GraphView library so it looks how i want it
    public void setUpGraph (GraphView maingraph, LineGraphSeries mainseries) {

        //Changing the style of the graph

        maingraph.getViewport().setScalable(true);
        maingraph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
        maingraph.getGridLabelRenderer().setVerticalLabelsVisible(false);
        maingraph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        maingraph.setBackgroundColor(Color.TRANSPARENT);
        mainseries.setColor(Color.parseColor("#BB86FC"));
        mainseries.setDrawDataPoints(false);
        mainseries.setAnimated(true);
        mainseries.setThickness(6);

        maingraph.addSeries(mainseries);
    }
}
