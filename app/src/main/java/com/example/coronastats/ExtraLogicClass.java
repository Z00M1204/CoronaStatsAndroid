package com.example.coronastats;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jjoe64.graphview.GraphView;

import java.util.ArrayList;

public class ExtraLogicClass {

    //method for checking whether the api has returned an empty value, and if so changing it from nothing to "N/A" since it makes more sense
    public String getNA (String NAString) {
        if (NAString.isEmpty()) {
            NAString = "N/A";
        }

        return NAString;
    }

    //method for when the total cases graph should be shown
    public void setGraphVisible (ImageView imgloading, LinearLayout lnlayoutdata) {
        imgloading.setVisibility(View.GONE);
        lnlayoutdata.setVisibility(View.VISIBLE);
    }

    //method for when the total cases graph shouldnt be shown
    public void setGraphInvisible(LinearLayout lnlayoutmain, GraphView maingraph, float scale) {
        maingraph.setVisibility(View.INVISIBLE);
        //converting to dp
        int dp = (int) (120 * scale + 0.5f);

        lnlayoutmain.getLayoutParams().height = dp;
    }
}
