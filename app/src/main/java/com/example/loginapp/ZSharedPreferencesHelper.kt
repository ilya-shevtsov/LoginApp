package com.example.loginapp

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.loginapp.modelClasses.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ZSharedPreferencesHelper constructor(context: Context) {

    companion object {
        const val SHARED_PREF_NAME = "SHARED_PREF_NAME"
        const val USERS_KEY = "USERS_KEY"
        val USERS_TYPE: Type = object : TypeToken<List<User>>() {}.type
    }

    private var sharedPreferences: SharedPreferences
    private var gson: Gson = Gson()

    init {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }

    fun getUsers(): List<User> {
        val users: List<User>? = gson.fromJson(
            sharedPreferences.getString(USERS_KEY, ""),
            USERS_TYPE
        )
        return users.orEmpty()
    }

    fun addUser(newUser: User): Boolean {
        val users: MutableList<User> = getUsers().toMutableList()
        for (oldUser: User in users) {
            if (oldUser.email.equals(newUser.email, ignoreCase = true)) {
                return false
            }
        }
        users.add(newUser)
        val userString = gson.toJson(users, USERS_TYPE)
        Log.d("KEK", "userString >> $userString <<")
        sharedPreferences.edit().putString(USERS_KEY, userString).apply()
        return true
    }

    fun saveOrOverrideUser(newUser: User): Boolean {
        val users = getUsers().toMutableList()
        for (oldUser in users) {
            if (oldUser.email.equals(newUser.email, ignoreCase = true)) {
                users.remove(oldUser)
                break
            }
        }
        users.add(newUser)
        sharedPreferences.edit().putString(USERS_KEY, gson.toJson(users, USERS_TYPE)).apply()
        return true
    }

    fun getSuccessLogins(): List<String?> {
        val successLogins: MutableList<String> = ArrayList()
        val allUsers = getUsers()
        for (user in allUsers) {
            if (user.hasSuccessLogin) {
                successLogins.add(user.email)
            }
        }
        return successLogins
    }

    fun login(login: String, password: String): User? {
        val users = getUsers()
        for (user in users) {
            if (login.equals(user.email, ignoreCase = true)
                && password == user.password
            ) {
                user.hasSuccessLogin = true
                sharedPreferences.edit().putString(USERS_KEY, gson.toJson(users, USERS_TYPE))
                    .apply()
                return user
            }
        }
        return null
    }
}