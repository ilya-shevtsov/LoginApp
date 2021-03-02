package com.example.loginapp.common.view

import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.loginapp.R
import com.example.loginapp.authorization.view.AuthFragment

abstract class SingleFragmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.ac_single_fragment)

        if (savedInstanceState == null) {
            openFragment(fragment = AuthFragment.newInstance())
        }

        this.onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (supportFragmentManager.backStackEntryCount == 0) {
                    finish()
                    Log.e("SingleFragmentActivity", "PopBackStack has finished the activity")
                } else {
                    isEnabled = false
                    supportFragmentManager.popBackStack()
                }
            }
        })
    }

    fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(fragment::class.java.simpleName)
            .commit()
    }

    abstract fun getFragment(): Fragment

}