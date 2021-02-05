package com.example.loginapp

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class SharedPreferencesHelper constructor(context: Context) {

    companion object {
        const val SHARED_PREF_NAME = "SHARED_PREF_NAME"
        const val USERS_KEY = "USERS_KEY"
        val USERS_TYPE: Type = object : TypeToken<List<User>>() {}.type
    }

    private var mSharedPreferences: SharedPreferences
    private var mGson: Gson = Gson()

    init {
        mSharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }

    fun getUsers(): List<User> {
        val users: List<User>? = mGson.fromJson(
            mSharedPreferences.getString(USERS_KEY, ""),
            USERS_TYPE
        )
        return users.orEmpty()
    }

    fun addUser(newUser: User): Boolean {
        val users: MutableList<User> = getUsers().toMutableList()
        for (oldUser: User in users) {
            if (oldUser.mEmail.equals(newUser.mEmail, ignoreCase = true)) {
                return false
            }
        }
        users.add(newUser)
        val userString = mGson.toJson(users, USERS_TYPE)
        Log.d("KEK", "userString >> $userString <<")
        mSharedPreferences.edit().putString(USERS_KEY, userString).apply()
        return true
    }

    fun saveOrOverrideUser(newUser: User): Boolean {
        val users = getUsers().toMutableList()
        for (oldUser in users) {
            if (oldUser.mEmail.equals(newUser.mEmail, ignoreCase = true)) {
                users.remove(oldUser)
                break
            }
        }
        users.add(newUser)
        mSharedPreferences.edit().putString(USERS_KEY, mGson.toJson(users, USERS_TYPE)).apply()
        return true
    }

    fun getSuccessLogins(): List<String?> {
        val successLogins: MutableList<String> = ArrayList()
        val allUsers = getUsers()
        for (user in allUsers) {
            if (user.mHasSuccessLogin) {
                successLogins.add(user.mEmail)
            }
        }
        return successLogins
    }

    fun login(login: String, password: String): User? {
        val users = getUsers()
        for (user in users) {
            if (login.equals(user.mEmail, ignoreCase = true)
                && password == user.mPassword
            ) {
                user.mHasSuccessLogin = true
                mSharedPreferences.edit().putString(USERS_KEY, mGson.toJson(users, USERS_TYPE))
                    .apply()
                return user
            }
        }
        return null
    }
}