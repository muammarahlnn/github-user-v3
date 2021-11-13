package com.ardnn.githubuserv3.api.services

import com.ardnn.githubuserv3.api.responses.SearchedUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchUserApiService {
    @GET("users")
    fun getSearchedUsers(
        @Query("q")
        username: String
    ): Call<SearchedUserResponse>
}