package com.frankz.ludycommobiletest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.frankz.ludycommobiletest.R
import com.frankz.ludycommobiletest.viewmodel.AppViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: AppViewModel;


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_LudycomMobileTest)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel =
            AppViewModel(application)
    }

}