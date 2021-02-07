package com.example.loginapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class UserDto(
    @SerializedName("email") val mEmail: String,
    @SerializedName("name") val mName: String,
    @SerializedName("password") val mPassword: String? = null,
    var mHasSuccessLogin: Boolean = false
) : Serializable