package com.example.coronastats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {

    GraphView maingraph;

    TextView txtotalcases;
    TextView txnewcases;
    TextView txactivecases;
    TextView txtotaldeaths;
    TextView txnewdeaths;
    TextView txtotalrecovered;
    TextView txcountry;

    EditText edtxsearch;

    ImageButton btsearch;

    LinearLayout lnlayoutmain;
    LinearLayout lnlayoutdata;

    ImageView imgloading;

    ApiClass ApiClassCall = new ApiClass();



    ExtraLogicClass extraLogicClass = new ExtraLogicClass();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        maingraph = findViewById(R.id.maingraph);

        txtotalcases = findViewById(R.id.txtotalcases);
        txnewcases = findViewById(R.id.txnewcases);
        txactivecases = findViewById(R.id.txactivecases);
        txtotaldeaths = findViewById(R.id.txtotaldeaths);
        txnewdeaths = findViewById(R.id.txnewdeaths);
        txtotalrecovered = findViewById(R.id.txtotalrecovered);
        txcountry = findViewById(R.id.txcountry);

        edtxsearch = findViewById(R.id.edtxsearch);

        btsearch = findViewById(R.id.btsearch);

        lnlayoutmain = findViewById(R.id.lnlayoutmain);
        lnlayoutdata = findViewById(R.id.lnlayoutdata);

        imgloading = findViewById(R.id.imgloading);

        startCall();

        btsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchClicked();
            }
        });

    }

    public void searchClicked() {

        //getting searchquery to then pass to ApiClass later
        String searchquery = edtxsearch.getText().toString();
        ApiClassCall.MainApiCall(searchquery, txtotalcases, txnewcases, txactivecases, txtotaldeaths, txnewdeaths, txtotalrecovered, txcountry);

        //I have to get the scale for converting to dp from here, since passing context to a class can lead to memory leaks
        final float scale = getResources().getDisplayMetrics().density;

        //calling method to make the graph invisible, because the API i'm using doesn't provide historical coronavirus data for all countries
        extraLogicClass.setGraphInvisible(lnlayoutmain, maingraph, scale);


    }

    public void startCall() {

        ApiClassCall.Graphcall(maingraph, lnlayoutdata, imgloading);


        ApiClassCall.MainApiCall("Denmark", txtotalcases, txnewcases, txactivecases, txtotaldeaths, txnewdeaths, txtotalrecovered, txcountry);

    }
}