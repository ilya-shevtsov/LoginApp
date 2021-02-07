package com.example.loginapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class UserDto(

    @SerializedName("email")
    val email: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("password")
    val password: String? = null,

    var hasSuccessLogin: Boolean = false

) : Serializable