package com.example.loginapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.example.loginapp.model.User
import com.example.loginapp.model.UserDto
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.*
import java.io.IOException


class AuthFragment : Fragment() {

    private lateinit var mEmail: AutoCompleteTextView
    private lateinit var mPassword: EditText
    private lateinit var mEnterButton: Button
    private lateinit var mRegisterButton: Button

    companion object {
        fun newInstance(): AuthFragment {
            val args = Bundle()
            val fragment = AuthFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fr_auth, container, false)

        mEmail = view.findViewById(R.id.authorizationEmailEditText)
        mPassword = view.findViewById(R.id.authorizationPasswordEditText)
        mEnterButton = view.findViewById(R.id.authorizationEnterButton)
        mRegisterButton = view.findViewById(R.id.authorizationRegistrationButton)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        mEmail.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                mEmail.showDropDown()
            }
        }

        mEnterButton.setOnClickListener {
            if (isEmailValid() && isPasswordValid()) {

                val request: Request = Request.Builder()
                    .url(BuildConfig.SERVER_URL + "user/")
                    .build()

                val client: OkHttpClient = ApiUtils.getBasicAuthClient(
                    email = mEmail.text.toString(),
                    password = mPassword.text.toString(),
                    newInstance = true
                )

                client.newCall(request = request).enqueue(object : Callback {

                    val handler = Handler(activity!!.mainLooper)

                    override fun onFailure(call: Call, e: IOException) {
                        handler.post {
//                            showMessage(R.string.request_error)
                            Log.e("Hehe", "Error: ${e.localizedMessage}")
                        }
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (response.isSuccessful) {

                            val gson = Gson()
                            val json = gson.fromJson(
                                response.body!!.string(),
                                JsonObject::class.java
                            )

                            val userDto =
                                gson.fromJson(
                                    json["data"],
                                    UserDto::class.java
                                )
                            val user = userDto.toUser()

                            val startProfileIntent = Intent(activity, ProfileActivity::class.java)
                            startProfileIntent.putExtra(ProfileActivity.USER_KEY, user)
                            startActivity(startProfileIntent)
                            activity?.finish()

                        } else {
                            handler.post { showMessage(R.string.login_error) }
                        }
                    }
                })
            } else {
                showMessage(R.string.input_error)
            }
        }

        mRegisterButton.setOnClickListener {

            fragmentManager!!
                .beginTransaction()
                .replace(R.id.fragmentContainer, RegistrationFragment.newInstance())
                .addToBackStack(RegistrationFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun isEmailValid(): Boolean {
        return !TextUtils.isEmpty(mEmail.text)
                && Patterns.EMAIL_ADDRESS.matcher(mEmail.text).matches()
    }

    private fun isPasswordValid(): Boolean {
        return !TextUtils.isEmpty(mPassword.text)
    }

    private fun showMessage(@StringRes stringId: Int) {
        Toast.makeText(activity, stringId, Toast.LENGTH_LONG).show()
    }

    private fun UserDto.toUser(): User {
        return User(
            email = email,
            name = name,
            password = password,
            hasSuccessLogin = hasSuccessLogin
        )

    }

}