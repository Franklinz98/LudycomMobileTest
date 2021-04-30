package com.frankz.ludycommobiletest.apiservice;

import org.json.JSONArray;

public interface VolleyCallback {
    void onRequestSuccess(JSONArray response);
    void onRequestError();
}
