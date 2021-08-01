package com.weatherapp.view.activity

import android.os.Bundle
import com.weatherapp.R
import com.weatherapp.view.fragment.HomeFragment


class HomeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        changeFragment(HomeFragment(), R.id.mainContainer, null)

    }
}