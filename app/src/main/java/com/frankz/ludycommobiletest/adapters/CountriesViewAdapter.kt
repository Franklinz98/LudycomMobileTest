package com.frankz.ludycommobiletest.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.frankz.ludycommobiletest.model.Country
import com.frankz.ludycommobiletest.R


class CountriesViewAdapter(
    private val values: List<Country>,
    var query: String,
    val interactor: ListInteraction
) :
    RecyclerView.Adapter<CountriesViewAdapter.ViewHolder>() {

    var filteredVals : MutableList<Country> = mutableListOf();

    init {
        filterValues(query)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountriesViewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountriesViewAdapter.ViewHolder, position: Int) {
        val country = filteredVals[position]
        holder.countryName.text = country.name
        holder.capitalName.text = country.capital
        holder.favouriteAction.setImageResource(
            if (country.favourite) R.drawable.ic_heart_filled else R.drawable.ic_heart
        )
        holder.mapsAction.setOnClickListener {
            interactor.onItemMapAction(country)
        }
        holder.favouriteAction.setOnClickListener {
            interactor.onItemFavouriteAction(country)
            notifyChanges()
            Log.d("favourites", "onBindViewHolder: ")
        }
        holder.view.setOnClickListener {
            interactor.onItemAction(country)
        }
    }

    override fun getItemCount(): Int = filteredVals.size

    fun filterValues(query: String) {
        filteredVals.clear();
        if (query.isEmpty()){
            filteredVals.addAll(values)
        }else{
            values.forEach{
                if (it.name.contains(query, ignoreCase = true)){
                    filteredVals.add(it)
                }
            }
        }
    }

    fun notifyChanges() {
        notifyDataSetChanged()
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val mapsAction: ImageButton = view.findViewById(R.id.mapsAction)
        val favouriteAction: ImageButton = view.findViewById(R.id.favoriteAction)
        val countryName: TextView = view.findViewById(R.id.countryName)
        val capitalName: TextView = view.findViewById(R.id.capitalName)
    }

    interface ListInteraction {
        fun onItemMapAction(country: Country)
        fun onItemFavouriteAction(country: Country)
        fun onItemAction(country: Country)
    }
}