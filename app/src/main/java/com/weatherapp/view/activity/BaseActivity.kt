package com.weatherapp.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

abstract class BaseActivity : AppCompatActivity() {

    fun changeFragment(fragment: Fragment, containerID: Int, args: Bundle?) {
        val tag = fragment.javaClass.name
        val fm = supportFragmentManager

        if (args != null) {
            fragment.arguments = args
        }

        val ts = fm.beginTransaction()
        ts.replace(containerID, fragment, tag)
        // used for store fragment in stack
        ts.addToBackStack(tag)
        ts.commit()
    }

}
