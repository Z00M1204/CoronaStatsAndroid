package com.example.coronastats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;

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

    ApiClass ApiClassCall = new ApiClass();





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

        startCall();

        btsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchClicked();
            }
        });

    }

    public void searchClicked() {
        String searchquery = edtxsearch.getText().toString();
        ApiClassCall.MainApiCall(searchquery, txtotalcases, txnewcases, txactivecases, txtotaldeaths, txnewdeaths, txtotalrecovered, txcountry);

        maingraph.setVisibility(View.INVISIBLE);

        //converting to dp
        final float scale = getResources().getDisplayMetrics().density;
        int dp = (int) (120 * scale + 0.5f);

        lnlayoutmain.getLayoutParams().height = dp;



    }

    public void startCall() {

        ApiClassCall.Graphcall(maingraph);


        ApiClassCall.MainApiCall("Denmark", txtotalcases, txnewcases, txactivecases, txtotaldeaths, txnewdeaths, txtotalrecovered, txcountry);

    }
}