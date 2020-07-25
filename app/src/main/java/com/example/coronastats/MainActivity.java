package com.example.coronastats;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtotalcases = findViewById(R.id.txtotalcases);
        txnewcases = findViewById(R.id.txnewcases);
        txactivecases = findViewById(R.id.txactivecases);
        txtotaldeaths = findViewById(R.id.txtotaldeaths);
        txnewdeaths = findViewById(R.id.txnewdeaths);
        txtotalrecovered = findViewById(R.id.txtotalrecovered);
        txcountry = findViewById(R.id.txcountry);

        edtxsearch = findViewById(R.id.edtxsearch);

        btsearch = findViewById(R.id.btsearch);


        //calling graphclass to show graph
        maingraph = findViewById(R.id.maingraph);

        GraphClass GraphClassCall = new GraphClass();
        GraphClassCall.setUpGraph(maingraph);


        btsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchClicked();
            }
        });

    }

    public void searchClicked() {
        ApiClass ApiClassCall = new ApiClass();
        String searchquery = edtxsearch.getText().toString();
        ApiClassCall.CountryApiCall(searchquery, txtotalcases, txnewcases, txactivecases, txtotaldeaths, txnewdeaths, txtotalrecovered, txcountry);


    }
}