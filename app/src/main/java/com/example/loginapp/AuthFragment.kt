package com.example.loginapp


import android.os.Bundle
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
import com.example.loginapp.albumsPreview.AlbumsPreviewFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class AuthFragment : Fragment() {

    private lateinit var email: AutoCompleteTextView
    private lateinit var password: EditText
    private lateinit var enterButton: Button
    private lateinit var registerButton: Button

    companion object {
        fun newInstance(): AuthFragment {
            return AuthFragment()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_authorization, container, false)

        email = view.findViewById(R.id.authorizationEmailEditText)
        password = view.findViewById(R.id.authorizationPasswordEditText)
        enterButton = view.findViewById(R.id.authorizationEnterButton)
        registerButton = view.findViewById(R.id.authorizationRegistrationButton)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        email.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                email.showDropDown()
            }
        }

        enterButton.setOnClickListener {
            if (isEmailValid() && isPasswordValid()) {
                authorizeUser()
            } else {
                showMessage(R.string.input_error)
            }
        }

        registerButton.setOnClickListener {
            (activity as SingleFragmentActivity).openFragment(
                fragment = RegistrationFragment.newInstance()
            )
        }
    }

    private fun authorizeUser() {
        ApiUtils.getApiService()
            .enterUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onSuccess = {
                (activity as SingleFragmentActivity).openFragment(fragment = AlbumsPreviewFragment.newInstance())
            }, onError = {
                showMessage(R.string.request_error)
                Log.e("AuthFragment", "AuthRequestError: ${it.localizedMessage}")
            })
            .disposeOnDestroy(viewLifecycleOwner)
    }

    private fun isEmailValid(): Boolean {
        return !TextUtils.isEmpty(email.text)
                && Patterns.EMAIL_ADDRESS.matcher(email.text).matches()
    }

    private fun isPasswordValid(): Boolean {
        return !TextUtils.isEmpty(password.text)
    }

    private fun showMessage(@StringRes stringId: Int) {
        Toast.makeText(activity, stringId, Toast.LENGTH_LONG).show()
    }
}