package com.example.coronastats;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiClass{

    OkHttpClient client = new OkHttpClient();

    ExtraLogicClass extraLogicClass = new ExtraLogicClass();

    //method for my API call that gets the historic corona infection data in a JSON file, i then parse that JSON file to get the infection numbers from the each JSON object inside the JSON array, and then add that data to the graph
    public void Graphcall (final GraphView maingraph, final LinearLayout lnlayoutdata, final ImageView imgloading) {
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


                                                //getting response body and storing it in a string
                                                String myresponse = response.body().string();

                                                //getting the JSON array which holds each object which holds a days worth of infection data
                                                JSONArray grapharray = new JSONArray(myresponse);

                                                //defining a new LineGraphSeries, which is used for my GraphView(from the GraphView library)

                                                LineGraphSeries<DataPoint> mainseries = new LineGraphSeries<>();


                                                //For loop for going through the JSON array and getting each object, and the objects "infected" value
                                                for (int i = 0; grapharray.length() > i; i++) {

                                                    //getting the JSON object in the position in the array of the index i
                                                    JSONObject graphobject = grapharray.getJSONObject(i);


                                                        //The reason behind the long if statement, is the fact that the API im using, is complete garbage, and for some reason it sometimes just throws in random amounts of infected for example 0, 4 and some completely unrealistic very high numbers. Therefore i filter them out through the if statement below
                                                        if (graphobject.isNull("infected") || graphobject.getInt("infected") == 0 || graphobject.getInt("infected") == 4 || graphobject.getInt("infected") > 300000) {

                                                            //logging to the logcat, in the case that one of the conditions in the if statements is true
                                                            Log.e("myTag", "graphobject is null");


                                                        } else {

                                                            //getting the amount of infected from the JSON object
                                                            Integer graphdata = graphobject.getInt("infected");

                                                            //creating a new DataPoint and then appending it to the GraphView
                                                            DataPoint point = new DataPoint(i, graphdata);
                                                            mainseries.appendData(point, true, grapharray.length());

                                                            //some of the set up of the graph is done here(because of an issue with the GraphView library), while the most of it is done in the GraphClass
                                                            maingraph.getViewport().setYAxisBoundsManual(true);
                                                            maingraph.getViewport().setMinY(1);
                                                            maingraph.getViewport().setMaxY(graphdata);
                                                            maingraph.getViewport().setXAxisBoundsManual(true);
                                                            maingraph.getViewport().setMinX(0);
                                                            maingraph.getViewport().setMaxX(i);
                                                        }




                                                }


                                                //initiating my GraphClass so i can call methods from it
                                                final GraphClass GraphClassCall = new GraphClass();

                                                //calling my GraphView setup class
                                                GraphClassCall.setUpGraph(maingraph, mainseries);

                                                //calling a method from my ExtraLogicClass, that sets up most of the GraphViews property so it looks how i want it to
                                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                       extraLogicClass.setGraphVisible(imgloading, lnlayoutdata);
                                                    }
                                                });


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

        //starting the thread which the API call runs on
        thread.start();
    }

    //this method is used for the other API call needed for this app. This method gets all the current corona virus data from another API than the previous one, since the previous one wasn't generally very reliable and also it didn't have all the data i need.
    public void MainApiCall(final String CountryCode, final TextView txtotalcases, final TextView txnewcases,
                               final TextView txactivecases, final TextView txtotaldeaths, final TextView txnewdeaths, final TextView txtotalrecovered, final TextView txcountry) {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

                //creating a new StringBuilder and appending the url and the countrycode so API returns the coronavirus data of the country that the user searched for
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

                                                //getting the response body
                                                JSONObject txobj = new JSONObject(response.body().string());

                                                //Creating new strings for the needed data and setting the text of the appropriate TextViews to the given strings, and running it through the getNA method that checks whether or not the API has returned an empty value, in which case the string is just set to "N/A" so its clearer for the user
                                                final String casesstring = extraLogicClass.getNA(txobj.getString("Total Cases_text"));
                                                final String deathsstring = extraLogicClass.getNA(txobj.getString("Total Deaths_text"));
                                                final String countrystring = extraLogicClass.getNA(txobj.getString("Country_text"));
                                                final String recoveriesstring = extraLogicClass.getNA(txobj.getString("Total Recovered_text"));
                                                final String activestring = extraLogicClass.getNA(txobj.getString("Active Cases_text"));
                                                final String newcasesstring = extraLogicClass.getNA(txobj.getString("New Cases_text"));
                                                final String newdeathsstring = extraLogicClass.getNA(txobj.getString("New Deaths_text"));


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

        //starting the thread which the API call runs on
        thread.start();
    }

}
