package com.example.loginapp.albums

import androidx.fragment.app.Fragment
import com.example.loginapp.SingleFragmentActivity

class AlbumsActivity : SingleFragmentActivity() {

    override fun getFragment(): Fragment {
        return AlbumsFragment.newInstance()
    }
}
