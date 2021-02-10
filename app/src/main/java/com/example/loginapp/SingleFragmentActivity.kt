package com.example.loginapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

abstract class SingleFragmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_single_fragment)
        if (savedInstanceState == null) {
            val fragmentManager: FragmentManager = supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, getFragment())
                .commit()
        }
    }

    abstract fun getFragment(): Fragment

    override fun onBackPressed() {
        if (fragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            fragmentManager.popBackStack()
        }
    }
}