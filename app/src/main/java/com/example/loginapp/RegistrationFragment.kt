package com.example.loginapp

import android.os.Bundle
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
import com.example.loginapp.modelClasses.User
import com.example.loginapp.modelClasses.UserDto
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class RegistrationFragment : Fragment() {

    private lateinit var login: EditText
    private lateinit var name: EditText
    private lateinit var password: EditText
    private lateinit var passwordAgain: EditText
    private lateinit var registration: Button
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    companion object {
        fun newInstance(): RegistrationFragment {
            return RegistrationFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fr_registration, container, false)

        name = view.findViewById(R.id.registrationNameEditText)
        login = view.findViewById(R.id.registrationEmailEditText)
        password = view.findViewById(R.id.registrationPasswordEditText)
        passwordAgain = view.findViewById(R.id.registrationPasswordAgainEditText)
        registration = view.findViewById(R.id.registrationRegistrationButton)
        sharedPreferencesHelper = SharedPreferencesHelper(context!!)

        registration.setOnClickListener {
            if (isInputValid()) {
                val user = User(
                    email = login.text.toString(),
                    name = name.text.toString(),
                    password = password.text.toString(),
                    hasSuccessLogin = false
                )
                val userDto = user.toUserDto()

                ApiUtils.getApiService()
                    .registration(userDto)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(onComplete = {
                        showMassage(R.string.login_register_success)
                        fragmentManager!!.popBackStack()
                    }, onError = {
                        showMassage(R.string.request_error)
                        Log.e("Haha", "Error: ${it.localizedMessage}")
                    })
            } else {
                showMassage(R.string.input_error)
            }
        }
        return view
    }

    private fun isInputValid(): Boolean {
        val email = login.text.toString()
        if (isEmailValid(email) && isPasswordValid()) {
            return true
        }
        return false
    }

    private fun isEmailValid(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(): Boolean {
        val password = password.text.toString()
        val passwordAgain = passwordAgain.text.toString()

        return password == passwordAgain
                && !TextUtils.isEmpty(password)
                && !TextUtils.isEmpty(passwordAgain)
    }

    fun showMassage(@StringRes stringId: Int) {
        Toast.makeText(activity, stringId, Toast.LENGTH_LONG).show()
    }

    private fun User.toUserDto(): UserDto {
        return UserDto(
            email = email,
            name = name,
            password = password,
            hasSuccessLogin = hasSuccessLogin
        )
    }
}