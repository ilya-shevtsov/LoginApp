package com.example.loginapp.user.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (

    var email: String,

    var name: String,

    var password: String?,

    var hasSuccessLogin: Boolean

): Parcelable
