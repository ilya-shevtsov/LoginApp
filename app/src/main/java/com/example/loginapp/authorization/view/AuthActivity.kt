package com.example.loginapp.authorization.view

import androidx.fragment.app.Fragment
import com.example.loginapp.common.view.SingleFragmentActivity

class AuthActivity : SingleFragmentActivity() {

    override fun getFragment(): Fragment {
        return AuthFragment.newInstance()
    }
}