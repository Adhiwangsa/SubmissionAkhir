package com.dicoding.submissionakhir.database

import com.dicoding.submissionakhir.database.data.DataUser
import com.dicoding.submissionakhir.database.model.DetailUserResponse
import com.dicoding.submissionakhir.database.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.ArrayList

interface Api {

    @Headers("Authorization: token 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")

    @GET("search/users")
    fun getUser(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("/users/{username}")
    fun getDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<DataUser>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<DataUser>>
}