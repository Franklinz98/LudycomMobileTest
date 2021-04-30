package com.frankz.ludycommobiletest.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.frankz.ludycommobiletest.R
import com.frankz.ludycommobiletest.adapters.CountriesViewAdapter
import com.frankz.ludycommobiletest.model.Country

class RegionCountries(private val liveData: LiveData<List<Country>>, val title: String) :
    Fragment(), CountriesViewAdapter.ListInteraction {

    lateinit var observer: Observer<List<Country>>
    lateinit var adapter: CountriesViewAdapter
    lateinit var progressBar: ProgressBar
    lateinit var list: RecyclerView
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_region_countries, container, false)
        configViews(view)
        configObserver()
        return view;
    }

    private fun configObserver() {
        observer = Observer { countries ->
            adapter = CountriesViewAdapter(countries, "", this)
            list.layoutManager = LinearLayoutManager(context)
            list.adapter = adapter
            progressBar.visibility = View.GONE
        }
        liveData.observe(viewLifecycleOwner, observer)
    }

    private fun configViews(view: View) {
        progressBar = view.findViewById(R.id.progressBar)
        list = view.findViewById(R.id.countriesList)
    }

    fun onQueryUpdated(query:String){
        adapter.filterValues(query)
        adapter.notifyChanges()
    }

    fun updateNavController(navController: NavController){
        this.navController = navController
    }

    override fun onItemMapAction(country: Country) {
        val gmmIntentUri = Uri.parse("geo:${country.location[0]},${country.location[1]}?z=${5}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        activity?.let {
            mapIntent.resolveActivity(it.packageManager)?.let {
                startActivity(mapIntent)
            }
        }
    }

    override fun onItemFavouriteAction(country: Country) {
        country.favourite = !country.favourite
    }

    override fun onItemAction(country: Country) {
        val bundle = bundleOf("countryData" to country)
        navController.navigate(R.id.action_countryList_to_countryDetails, bundle)
    }

}