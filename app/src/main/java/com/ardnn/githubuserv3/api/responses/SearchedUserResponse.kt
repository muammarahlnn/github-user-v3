package com.ardnn.githubuserv3.api.responses

import com.google.gson.annotations.SerializedName

data class SearchedUserResponse(
    @field:SerializedName("items")
    val searchedUser: List<UserResponse>
)