package com.frankz.ludycommobiletest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: AppViewModel;


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_LudycomMobileTest)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = AppViewModel(application)
    }

}