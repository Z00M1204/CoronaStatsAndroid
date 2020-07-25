package com.example.coronastats;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiClass{

    public void


    public void CountryApiCall(final String CountryCode, final TextView txtotalcases, final TextView txnewcases,
                               final TextView txactivecases, final TextView txtotaldeaths, final TextView txnewdeaths, final TextView txtotalrecovered, final TextView txcountry) {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

                StringBuilder sbuilder = new StringBuilder();
                sbuilder.append("https://covid-19-tracking.p.rapidapi.com/v1/");
                sbuilder.append(CountryCode);

                OkHttpClient client = new OkHttpClient();
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
