package com.example.loginapp.user.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class UserResponse(

    @SerializedName("data")
    val data: UserDto

) : Serializable