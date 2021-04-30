package com.frankz.ludycommobiletest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.frankz.ludycommobiletest.R
import com.frankz.ludycommobiletest.model.Country
import com.frankz.ludycommobiletest.utils.SvgUtils
import java.lang.Exception

class CountryDetails : Fragment() {

    lateinit var country: Country
    lateinit var name: TextView
    lateinit var region: TextView
    lateinit var population: TextView
    lateinit var capital: TextView
    lateinit var currency: TextView
    lateinit var language: TextView
    lateinit var borders: TextView
    lateinit var favourite: ImageButton
    lateinit var flag: ImageView
    lateinit var backButton: ImageButton
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_country_details, container, false)
        configView(view);
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        country = requireArguments().getParcelable("countryData")!!
        updateView(country)
    }

    private fun updateView(country: Country) {
        name.text = country.name
        favourite.setImageResource(
            if (country.favourite) R.drawable.ic_heart_filled else R.drawable.ic_heart
        )
        region.text = country.region
        population.text = country.population.toInt().toString()
        capital.text = country.capital
        currency.text = country.currency
        language.text = country.language
        borders.text = country.borders.toString().substring(1, country.borders.toString().length-1)
        try {
            SvgUtils.fetchSvg(activity, country.flag, flag);
        } catch (e: Exception) {

        }
    }

    private fun configView(view: View) {
        name = view.findViewById(R.id.country_detail_name)
        favourite = view.findViewById(R.id.country_detail_fav_action)
        region = view.findViewById(R.id.country_region)
        population = view.findViewById(R.id.country_population)
        capital = view.findViewById(R.id.country_capital)
        currency = view.findViewById(R.id.country_currency)
        language = view.findViewById(R.id.country_language)
        borders = view.findViewById(R.id.country_borders)
        backButton = view.findViewById(R.id.backButton)
        flag = view.findViewById(R.id.country_flag)
        backButton.setOnClickListener {
            navController.popBackStack()
        }
    }

}