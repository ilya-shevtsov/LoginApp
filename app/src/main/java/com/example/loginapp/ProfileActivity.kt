package com.example.loginapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView

class ProfileActivity : AppCompatActivity() {

    companion object {
        const val USER_KEY = "USER_KEY"
        const val REQUEST_CODE_GET_PHOTO = 101
    }

    private lateinit var mPhoto: AppCompatImageView
    private lateinit var mCurrentUser: User
    private lateinit var mLogin: TextView
    private lateinit var mPassword: TextView
    private lateinit var mSharedPreferencesHelper: SharedPreferencesHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_profile)

        mPhoto = findViewById(R.id.tvPhoto)
        mLogin = findViewById(R.id.tvEmail)
        mPassword = findViewById(R.id.tvPassword)
        mSharedPreferencesHelper = SharedPreferencesHelper(this)

        val bundle = intent.extras
        val user: User = bundle?.get(USER_KEY) as User
        mLogin.text = user.mEmail
        mPassword.text = user.mPassword
        mCurrentUser = user
//        if (mCurrentUser.mPhoto != null){
//            val photoUri = Uri.parse(mCurrentUser.mPhoto)
//            getContentResolver().takePersistableUriPermission(
//                photoUri,
//                Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
//            )
//            mPhoto.setImageURI(photoUri)
//        }
        mPhoto.setOnClickListener {
            openGallery()
        }
    }

    private fun openGallery() {
        val intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }
        startActivityForResult(intent, REQUEST_CODE_GET_PHOTO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_GET_PHOTO
            && data != null
        ) {
            val photoUri: Uri? = data.data
            mPhoto.setImageURI(photoUri)
            mSharedPreferencesHelper.saveOrOverrideUser(mCurrentUser)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
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