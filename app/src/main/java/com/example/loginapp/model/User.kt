package com.example.loginapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    var mEmail: String,
    var mName: String,
    var mPassword: String?,
    var mHasSuccessLogin: Boolean
): Parcelable
