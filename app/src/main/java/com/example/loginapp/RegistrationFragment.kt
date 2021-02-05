package com.example.loginapp

import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException


class RegistrationFragment : Fragment() {

    private lateinit var mLogin: EditText
    private lateinit var mName: EditText
    private lateinit var mPassword: EditText
    private lateinit var mPasswordAgain: EditText
    private lateinit var mRegistration: Button
    private lateinit var mSharedPreferencesHelper: SharedPreferencesHelper


    companion object {
        val JSON: MediaType = "application/json; charset=utf-8".toMediaTypeOrNull()!!
        fun newInstance(): RegistrationFragment {
            return RegistrationFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fr_registration, container, false)

        mName = view.findViewById(R.id.etName)
        mLogin = view.findViewById(R.id.etEmail)
        mPassword = view.findViewById(R.id.etPassword)
        mPasswordAgain = view.findViewById(R.id.etPasswordAgain)
        mRegistration = view.findViewById(R.id.RegistrationButton)
        mSharedPreferencesHelper = SharedPreferencesHelper(context!!)

        mRegistration.setOnClickListener {
            if (isInputValid()) {
                val user = User(
                    mEmail = mLogin.text.toString(),
                    mName = mName.text.toString(),
                    mPassword = mPassword.text.toString(),
                    mHasSuccessLogin = false
                )
                val userDto = user.toUserDto()

                val request: Request = Request.Builder()
                    .url(BuildConfig.SERVER_URL + "registration/")
                    .post(Gson().toJson(userDto).toRequestBody(JSON)).build()

                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                val client = OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build()

                client.newCall(request = request).enqueue(object : Callback {
                    val handler = Handler(activity!!.mainLooper)

                    override fun onFailure(call: Call, e: IOException) {
                        handler.post {
                            showMassage(R.string.request_error)
                            Log.e("Haha", "Error: ${e.localizedMessage}")
                        }
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (response.isSuccessful) {
//                            showMassage(R.string.login_register_success)
                            fragmentManager!!.popBackStack()
                        } else {
                            handler.post { showMassage(R.string.login_error) }
                        }
                    }

                })
            } else {
                showMassage(R.string.input_error)
            }
        }
        return view
    }

    private fun isInputValid(): Boolean {
        val email = mLogin.text.toString()
        if (isEmailValid(email) && isPasswordValid()) {
            return true
        }
        return false
    }

    private fun isEmailValid(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(): Boolean {
        val password = mPassword.text.toString()
        val passwordAgain = mPasswordAgain.text.toString()

        return password == passwordAgain
                && !TextUtils.isEmpty(password)
                && !TextUtils.isEmpty(passwordAgain)
    }

    fun showMassage(@StringRes stringId: Int) {
        Toast.makeText(activity, stringId, Toast.LENGTH_LONG).show()
    }

    private fun User.toUserDto(): UserDto {
        return UserDto(
            mEmail = mEmail,
            mName = mName,
            mPassword = mPassword,
            mHasSuccessLogin = mHasSuccessLogin
        )

    }
}