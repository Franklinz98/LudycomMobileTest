package com.frankz.ludycommobiletest.utils;

import android.graphics.drawable.Drawable;
import android.util.Log;

import com.frankz.ludycommobiletest.R;
import com.frankz.ludycommobiletest.model.Country;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class CountryUtils {

    public static HashMap<String, List<Country>> sortCountries(JSONArray countriesArray) {
        HashMap<String, List<Country>> countries = new HashMap<>();
        countries.put("asia", new ArrayList<>());
        countries.put("africa", new ArrayList<>());
        countries.put("americas", new ArrayList<>());
        countries.put("europe", new ArrayList<>());
        countries.put("oceania", new ArrayList<>());
        countries.put("polar", new ArrayList<>());
        final ArrayList<Country> allCountries = new ArrayList<>();
        for (int i = 0; i < countriesArray.length(); i++) {
            try {
                final JSONObject object = countriesArray.getJSONObject(i);
                final HashMap<String, Object> map = new Gson().fromJson(
                        object.toString(), new TypeToken<HashMap<String, Object>>() {
                        }.getType()
                );
                final Country country = Country.fromJson(map);
                switch (Objects.requireNonNull(map.get("region")).toString()) {
                    case "Asia":
                        Objects.requireNonNull(countries.get("asia")).add(country);
                        break;
                    case "Africa":
                        Objects.requireNonNull(countries.get("africa")).add(country);
                        break;
                    case "Americas":
                        Objects.requireNonNull(countries.get("americas")).add(country);
                        break;
                    case "Europe":
                        Objects.requireNonNull(countries.get("europe")).add(country);
                        break;
                    case "Oceania":
                        Objects.requireNonNull(countries.get("oceania")).add(country);
                        break;
                    default:
                        Objects.requireNonNull(countries.get("polar")).add(country);
                        break;
                }
                allCountries.add(country);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        updateCountryBorders(allCountries);
        return countries;
    }

    private static void updateCountryBorders(List<Country> countries) {
        for (Country country : countries) {
            country.updateBorders(countries);
        }
    }
}
