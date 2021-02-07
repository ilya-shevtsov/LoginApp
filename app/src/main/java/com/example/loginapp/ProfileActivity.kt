package com.example.loginapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.loginapp.model.User

class ProfileActivity : AppCompatActivity() {

    companion object {
        const val USER_KEY = "USER_KEY"
    }

    private lateinit var mCurrentUser: User
    private lateinit var mLogin: TextView
    private lateinit var mName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_profile)

        mLogin = findViewById(R.id.tvEmail)
        mName = findViewById(R.id.tvName)

        val bundle = intent.extras
        val user: User = bundle?.get(USER_KEY) as User
        mLogin.text = user.mEmail
        mName.text = user.mName
        mCurrentUser = user
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.profile_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionLogout -> {
                startActivity(Intent(this, AuthActivity::class.java))
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}