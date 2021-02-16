package com.example.loginapp.modelClasses

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class UserDtoResponse(

    @SerializedName("data")
    val data: UserDto

) : Serializable