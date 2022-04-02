package com.dicoding.submissionakhir.database.model

import com.dicoding.submissionakhir.database.data.DataUser
import com.google.gson.annotations.SerializedName

data class UserResponse(

    @field:SerializedName("items")
    val items : ArrayList<DataUser>
)
