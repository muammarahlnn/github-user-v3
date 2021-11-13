package com.ardnn.githubuserv3.api.services

import com.ardnn.githubuserv3.api.responses.UserDetailResponse
import com.ardnn.githubuserv3.api.responses.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApiService {
    @GET("{username}")
    fun getUserDetail(
        @Path("username")
        username: String
    ): Call<UserDetailResponse>

    @GET("{username}/followers")
    fun getUserFollowers(
        @Path("username")
        username: String
    ): Call<List<UserResponse>>

    @GET("{username}/following")
    fun getUserFollowing(
        @Path("username")
        username: String
    ): Call<List<UserResponse>>
}