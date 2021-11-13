package com.ardnn.githubuserv3.api.responses

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("login")
    val username: String = "",

    @field:SerializedName("avatar_url")
    val avatarUrl: String = "",
)