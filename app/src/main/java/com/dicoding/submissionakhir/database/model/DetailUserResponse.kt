package com.dicoding.submissionakhir.database.model

import com.google.gson.annotations.SerializedName

data class DetailUserResponse(

    @field:SerializedName("repos_url")
    val reposUrl: String? = null,

    @field:SerializedName("bio")
    val bio: Any? = null,

    @field:SerializedName("login")
    val login: String? = null,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("blog")
    val blog: String? = null,


    @field:SerializedName("company")
    val company: String? = null,

    @field:SerializedName("id")
    val id: Int = 0,

    @field:SerializedName("public_repos")
    val publicRepos: Int? = null,

    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("followers")
    val followers: Int? = null,

    @field:SerializedName("html_url")
    val htmlUrl: String? = null,

    @field:SerializedName("following")
    val following: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("location")
    val location: String? = null,

)
