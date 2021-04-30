package com.frankz.ludycommobiletest.apiservice;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.frankz.ludycommobiletest.apiservice.VolleyCallback;

import org.json.JSONArray;

public class VolleyService {

    public static void fetchCountries(Context context, VolleyCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://restcountries.eu/rest/v2/all";
        // Request a string response from the provided URL.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        callback.onRequestSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        // Add the request to the RequestQueue.
        queue.add(jsonArrayRequest);
    }
}
