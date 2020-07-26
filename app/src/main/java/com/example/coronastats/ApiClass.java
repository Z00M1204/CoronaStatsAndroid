package com.example.coronastats;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.Console;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiClass{

    OkHttpClient client = new OkHttpClient();


    public void Graphcall (final GraphView maingraph) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                final Request request = new Request.Builder().url("https://api.apify.com/v2/datasets/Ugq8cNqnhUSjfJeHr/items?format=json&clean=1").build();
                try  {
                    try {
                        Response CountryRespone = client.newCall(request).execute();

                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                Log.e("myTag", "Failure in graphapicall");

                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {

                                if (!response.isSuccessful()) {
                                    Log.e("myTag", "Failure in graphapicall2");
                                }

                                if (response.isSuccessful()) {
                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                        public void run() {
                                            try {

                                                String myresponse = response.body().string();

                                                JSONArray grapharray = new JSONArray(myresponse);

                                                LineGraphSeries<DataPoint> mainseries = new LineGraphSeries<>();


                                                for (int i = 0; grapharray.length() > i; i++) {
                                                    JSONObject graphobject = grapharray.getJSONObject(i);


                                                        //The reason behind the long if statement, is the fact that the API im using, is complete garbage, and for some reason it sometimes just throws in random amounts of infected for example 0, 4 and some completely unrealistic very high numbers. Therefore i filter them out through the if statement below
                                                        if (graphobject.isNull("infected") || graphobject.getInt("infected") == 0 || graphobject.getInt("infected") == 4 || graphobject.getInt("infected") > 300000) {


                                                            Log.e("myTag", "graphobject is null");


                                                        } else {
                                                            Integer graphdata = graphobject.getInt("infected");

                                                            DataPoint point = new DataPoint(i, graphdata);
                                                            Log.e("myTag", "got this far " + point.toString());
                                                            mainseries.appendData(point, true, grapharray.length());

                                                            maingraph.getViewport().setYAxisBoundsManual(true);
                                                            maingraph.getViewport().setMinY(1);
                                                            maingraph.getViewport().setMaxY(graphdata);

                                                            maingraph.getViewport().setXAxisBoundsManual(true);
                                                            maingraph.getViewport().setMinX(0);
                                                            maingraph.getViewport().setMaxX(i);
                                                        }




                                                }
                                                Log.e("myTag", "it jumped out ");



                                                GraphClass GraphClassCall = new GraphClass();

                                                GraphClassCall.setUpGraph(maingraph, mainseries);


                                            } catch (JSONException | IOException j) {
                                                j.printStackTrace();
                                            }
                                        }
                                    });
                                }

                            }
                        });

                    } catch (IOException e) {

                        e.printStackTrace();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        thread.start();
    }


    public void MainApiCall(final String CountryCode, final TextView txtotalcases, final TextView txnewcases,
                               final TextView txactivecases, final TextView txtotaldeaths, final TextView txnewdeaths, final TextView txtotalrecovered, final TextView txcountry) {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

                StringBuilder sbuilder = new StringBuilder();
                sbuilder.append("https://covid-19-tracking.p.rapidapi.com/v1/");
                sbuilder.append(CountryCode);

                final Request request = new Request.Builder().url(sbuilder.toString()).get()
                        .addHeader("x-rapidapi-host", "covid-19-tracking.p.rapidapi.com").addHeader("x-rapidapi-key", "7e94b1bcc2mshfc292ee8ce5527ap108986jsn1ec40be8b063").build();
                try  {
                    try {
                        Response CountryRespone = client.newCall(request).execute();

                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                    e.printStackTrace();
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {

                                if (response.isSuccessful()) {
                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                        public void run() {
                                            try {

                                                JSONObject txobj = new JSONObject(response.body().string());
                                                final String casesstring = txobj.getString("Total Cases_text");
                                                final String deathsstring = txobj.getString("Total Deaths_text");
                                                final String countrystring = txobj.getString("Country_text");
                                                final String recoveriesstring = txobj.getString("Total Recovered_text");
                                                final String activestring = txobj.getString("Active Cases_text");
                                                final String newcasesstring = txobj.getString("New Cases_text");
                                                final String newdeathsstring = txobj.getString("New Deaths_text");

                                                txtotalcases.setText(casesstring);
                                                txtotaldeaths.setText(deathsstring);
                                                txcountry.setText(countrystring);
                                                txtotalrecovered.setText(recoveriesstring);
                                                txactivecases.setText(activestring);
                                                txnewcases.setText(newcasesstring);
                                                txnewdeaths.setText(newdeathsstring);

                                            }catch (IOException f) {
                                                f.printStackTrace();

                                            } catch (JSONException j) {
                                                j.printStackTrace();
                                            }
                                        }
                                    });
                                }

                            }
                        });

                    } catch (IOException e) {

                        e.printStackTrace();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

}
