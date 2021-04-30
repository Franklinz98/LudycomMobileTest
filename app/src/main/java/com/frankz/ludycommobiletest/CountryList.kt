package com.frankz.ludycommobiletest

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.frankz.ludycommobiletest.adapters.SlidePagerAdapter
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class CountryList : Fragment() {

    private lateinit var pager: ViewPager2
    private lateinit var pagerAdapter: SlidePagerAdapter
    private lateinit var viewTitle: TextView
    lateinit var querySearch: EditText
    lateinit var titleObserver: Observer<String>
    lateinit var queryObserver: Observer<String>
    private val list = ArrayList<RegionCountries>()
    lateinit var navController: NavController
    private val model: AppViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_country_list, container, false)
        configView(view)
        configurePager(view)
        configListeners()
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Response", "onViewCreated: ")
        configObserver()
        navController = Navigation.findNavController(view)
        configNavController()
    }

    private fun configNavController() {
        list.forEach {
            it.updateNavController(navController)
        }
    }

    private fun configView(view: View) {
        viewTitle = view.findViewById(R.id.regionName)
        if (list.isEmpty()) {
            list.add(RegionCountries(model.asia, "Asia"))
            list.add(RegionCountries(model.africa, "Africa"))
            list.add(RegionCountries(model.americas, "America"))
            list.add(RegionCountries(model.europe, "Europa"))
            list.add(RegionCountries(model.oceania, "Oceanía"))
            list.add(RegionCountries(model.polar, "Otros"))
        }
        pager = view.findViewById(R.id.viewPager)
        pagerAdapter = SlidePagerAdapter(this.requireActivity(), list)
        querySearch = view.findViewById(R.id.searchBar)
    }

    private fun configurePager(view: View) {
        pager.adapter = pagerAdapter
        val wormDotsIndicator = view.findViewById<WormDotsIndicator>(R.id.dotsIndicator)
        wormDotsIndicator.setViewPager2(pager)
    }

    private fun configListeners() {
        pager.registerOnPageChangeCallback(
            object : OnPageChangeCallback() {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                }

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    model.updateTitle(list[position].title)
                }

                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                }
            }
        )
        querySearch.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                model.updateSearchQuery(s.toString())
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
            }
        })
    }

    private fun configObserver() {
        titleObserver = Observer { title ->
            viewTitle.text = title
        }
        model.title.observe(viewLifecycleOwner, titleObserver)
        queryObserver = Observer { query ->
            list[pager.currentItem].onQueryUpdated(query)
        }
        model.searchQuery.observe(viewLifecycleOwner, queryObserver)
        model.fetchCountries()
    }
}