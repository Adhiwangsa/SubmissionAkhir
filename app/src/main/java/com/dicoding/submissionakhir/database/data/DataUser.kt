package com.dicoding.submissionakhir.database.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataUser(
    val login : String,
    val id : Int,
    val avatar_url : String,
    val html_url: String
):Parcelable
