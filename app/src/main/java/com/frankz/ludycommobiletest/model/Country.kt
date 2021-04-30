package com.frankz.ludycommobiletest.model

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.google.gson.internal.LinkedTreeMap
import java.util.*

class Country : Parcelable {
    val name: String
    val region: String
    val capital: String
    val currency: String
    val language: String
    val alpha3Code: String
    val flag: String
    val population: Double
    var borders: List<*>
    val location: DoubleArray
    var favourite: Boolean

    constructor(
        alpha3Code: String,
        name: String,
        region: String,
        capital: String,
        currency: String,
        language: String,
        flag: String,
        population: Double,
        location: DoubleArray,
        borders: List<*>,
    ) {
        this.alpha3Code = alpha3Code
        this.name = name
        this.region = region
        this.capital = capital
        this.currency = currency
        this.language = language
        this.flag = flag
        this.population = population
        this.location = location
        this.borders = borders
        this.favourite = false
    }

    private constructor(`in`: Parcel) {
        name = `in`.readString()!!
        region = `in`.readString()!!
        capital = `in`.readString()!!
        currency = `in`.readString()!!
        language = `in`.readString()!!
        flag = `in`.readString()!!
        alpha3Code = `in`.readString()!!
        population = `in`.readDouble()
        borders = `in`.createStringArrayList()!!
        location = `in`.createDoubleArray()!!
        favourite = `in`.readByte().toInt() != 0
    }

    fun updateBorders(countries: List<Country>) {
        Log.d("Sort:$name", "sortCountries: $borders")
        borders = getBorders(borders, countries)
        Log.d("Sort:$name", "sortCountries: $borders")
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeString(region)
        dest.writeString(capital)
        dest.writeString(currency)
        dest.writeString(language)
        dest.writeString(flag)
        dest.writeString(alpha3Code)
        dest.writeDouble(population)
        dest.writeStringList(borders as List<String>)
        dest.writeDoubleArray(location)
        dest.writeByte((if (favourite) 1 else 0).toByte())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        val CREATOR: Parcelable.Creator<Country> = object : Parcelable.Creator<Country> {
            override fun createFromParcel(`in`: Parcel): Country {
                return Country(`in`)
            }

            override fun newArray(size: Int): Array<Country?> {
                return arrayOfNulls(size)
            }
        }

        @JvmStatic
        fun fromJson(map: HashMap<String, Any>): Country {
            val currencies = map["currencies"] as ArrayList<*>
            val languages = map["languages"] as ArrayList<*>
            val location = map["latlng"] as ArrayList<*>
            val latlng = DoubleArray(location.size)
            for (i in location.indices) {
                latlng[i] = location[i] as Double
            }
            return Country(
                map["alpha3Code"] as String,
                map["name"] as String,
                map["region"] as String,
                map["capital"] as String,
                (currencies[0] as LinkedTreeMap<*, *>)["name"] as String,
                (languages[0] as LinkedTreeMap<*, *>)["nativeName"] as String,
                map["flag"] as String,
                (map["population"] as Double),
                latlng,
                map["borders"] as List<*>
            )
        }

        private fun getBorders(
            bordersCodes: List<*>,
            countries: List<Country>
        ): List<String> {
            val borders: MutableList<String> = ArrayList()
            for (country in countries) {
                if (bordersCodes.contains(country.alpha3Code)) {
                    borders.add(country.name)
                }
            }
            return borders
        }
    }
}