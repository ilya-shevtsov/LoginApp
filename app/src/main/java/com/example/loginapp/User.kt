package com.example.loginapp

import android.media.Image
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    var mEmail: String,
    var mName: String,
    var mPassword: String,
    var mHasSuccessLogin: Boolean
): Parcelable {
}
