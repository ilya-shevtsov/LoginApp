package com.example.loginapp

import androidx.fragment.app.Fragment

class AuthActivity : SingleFragmentActivity(){

    override fun getFragment(): Fragment {
        return AuthFragment.newInstance()
    }
}