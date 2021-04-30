package com.frankz.ludycommobiletest.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.frankz.ludycommobiletest.apiservice.VolleyCallback;
import com.frankz.ludycommobiletest.apiservice.VolleyService;
import com.frankz.ludycommobiletest.model.Country;
import com.frankz.ludycommobiletest.utils.CountryUtils;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class AppViewModel extends AndroidViewModel implements VolleyCallback {

    private final Application application;
    private final MutableLiveData<List<Country>> asia, africa, americas, europe, oceania, polar;
    private final MutableLiveData<String> searchQuery, title;


    public AppViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        this.asia = new MutableLiveData<>();
        this.africa = new MutableLiveData<>();
        this.americas = new MutableLiveData<>();
        this.europe = new MutableLiveData<>();
        this.oceania = new MutableLiveData<>();
        this.polar = new MutableLiveData<>();
        this.searchQuery = new MutableLiveData<>();
        this.title = new MutableLiveData<>();
    }

    public LiveData<List<Country>> getAsia() {
        return asia;
    }

    public LiveData<List<Country>> getAfrica() {
        return africa;
    }

    public LiveData<List<Country>> getAmericas() {
        return americas;
    }

    public LiveData<List<Country>> getEurope() {
        return europe;
    }

    public LiveData<List<Country>> getOceania() {
        return oceania;
    }

    public LiveData<List<Country>> getPolar() {
        return polar;
    }

    public LiveData<String> getSearchQuery() {
        return searchQuery;
    }

    public MutableLiveData<String> getTitle() {
        return title;
    }

    public void updateSearchQuery(String query) {
        this.searchQuery.setValue(query);
    }

    public void updateTitle(String title) {
        this.title.setValue(title);
    }

    public void fetchCountries() {
        if (this.asia.getValue() == null){
            Log.d("Response", "fetchCountries: Make Request");
            VolleyService.fetchCountries(application.getBaseContext(), this);
        }
    }

    @Override
    public void onRequestSuccess(JSONArray response) {
        HashMap<String, List<Country>> countries = CountryUtils.sortCountries(response);
        asia.setValue(countries.get("asia"));
        africa.setValue(countries.get("africa"));
        americas.setValue(countries.get("americas"));
        europe.setValue(countries.get("europe"));
        oceania.setValue(countries.get("oceania"));
        polar.setValue(countries.get("polar"));
        Log.d("Response", "onRequestSuccess: " + Objects.requireNonNull(polar.getValue()).toString());
    }

    @Override
    public void onRequestError() {
        americas.setValue(new ArrayList<>());
    }
}
