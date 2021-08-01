package com.weatherapp.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.weatherapp.R
import com.weatherapp.utils.Generic.applyFullScreenStyle

class SplashActivity : BaseActivity() {
    private val screenTime: Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        applyFullScreenStyle(window)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }, screenTime)
    }

}