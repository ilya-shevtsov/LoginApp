package com.example.loginapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment


class AuthFragment : Fragment() {

    private lateinit var mLoginEditText: AutoCompleteTextView
    private lateinit var mPasswordEditText: EditText
    private lateinit var mEnterButton: Button
    private lateinit var mRegisterButton: Button
    private lateinit var mSharedPreferencesHelper: SharedPreferencesHelper
    private lateinit var mLoginedUsersAdapter: ArrayAdapter<String>

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

        mLoginEditText = view.findViewById(R.id.etEmail)
        mPasswordEditText = view.findViewById(R.id.etPassword)
        mEnterButton = view.findViewById(R.id.buttonEnter)
        mRegisterButton = view.findViewById(R.id.buttonRegister)
        mSharedPreferencesHelper = SharedPreferencesHelper(context!!)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mLoginedUsersAdapter = ArrayAdapter(
            context!!,
            android.R.layout.simple_dropdown_item_1line,
            mSharedPreferencesHelper.getSuccessLogins().toMutableList().filterNotNull()
        )

        mLoginEditText.setAdapter(mLoginedUsersAdapter)

        mLoginEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                mLoginEditText.showDropDown()
            }
        }

        mEnterButton.setOnClickListener {
            if (isEmailValid() && isPasswordValid()) {
                val user: User? = mSharedPreferencesHelper.login(
                    mLoginEditText.text.toString(),
                    mPasswordEditText.text.toString()
                )
                if (user != null) {
                    val startProfileIntent =
                        Intent(activity, ProfileActivity::class.java)
                    startProfileIntent.putExtra(
                        ProfileActivity.USER_KEY, user
                    )
                    startActivity(startProfileIntent)
                    activity!!.finish()
                } else {
                    showMessage(R.string.login_error)
                }
            } else {
                showMessage(R.string.input_error)
            }

            for (user in mSharedPreferencesHelper.getUsers()) {
                if (user.mEmail.equals(mLoginEditText.text.toString(), ignoreCase = true)
                    && user.mPassword == mPasswordEditText.text.toString()
                ) {
                    break
                }
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
        return !TextUtils.isEmpty(mLoginEditText.text)
                && Patterns.EMAIL_ADDRESS.matcher(mLoginEditText.text).matches()
    }

    private fun isPasswordValid(): Boolean {
        return !TextUtils.isEmpty(mPasswordEditText.text)
    }

    private fun showMessage(@StringRes stringId: Int) {
        Toast.makeText(activity, stringId, Toast.LENGTH_LONG).show()
    }


}