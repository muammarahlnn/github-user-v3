package com.ardnn.githubuserv3.api.responses

import com.google.gson.annotations.SerializedName

data class UserDetailResponse(
    @field:SerializedName("login")
    val username: String = "",

    @field:SerializedName("name")
    val name: String = "",

    @field:SerializedName("location")
    val location: String = "",

    @field:SerializedName("company")
    val company: String = "",

    @field:SerializedName("public_repos")
    val repositories: String = "",

    @field:SerializedName("followers")
    val followers: String = "",

    @field:SerializedName("following")
    val following: String = "",

    @field:SerializedName("avatar_url")
    val avatarUrl: String = "",
)