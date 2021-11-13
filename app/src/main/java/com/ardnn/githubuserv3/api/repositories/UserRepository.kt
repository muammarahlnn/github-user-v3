package com.ardnn.githubuserv3.api.repositories

import com.ardnn.githubuserv3.api.callbacks.UserDetailCallback
import com.ardnn.githubuserv3.api.callbacks.UserListCallback
import com.ardnn.githubuserv3.api.responses.UserDetailResponse
import com.ardnn.githubuserv3.api.responses.UserResponse
import com.ardnn.githubuserv3.api.services.UserApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UserRepository {
    private val USER_SERVICE: UserApiService =
        Retrofit.Builder()
            .baseUrl(Consts.BASE_URL_USER)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApiService::class.java)


    // method to get user detail
    fun getUserDetail(username: String, callback: UserDetailCallback) {
        USER_SERVICE.getUserDetail(username).enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        callback.onSuccess(response.body() ?: UserDetailResponse())
                    } else {
                        callback.onFailure("response.body() is null")
                    }
                } else {
                    callback.onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                callback.onFailure(t.localizedMessage ?: "getUserDetail failure")
            }
        })
    }

    // method to get user followers
    fun getUserFollowers(username: String, callback: UserListCallback) {
        USER_SERVICE.getUserFollowers(username).enqueue(object : Callback<List<UserResponse>> {
            override fun onResponse(
                call: Call<List<UserResponse>>,
                response: Response<List<UserResponse>>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        callback.onSuccess(response.body() ?: listOf())
                    } else {
                        callback.onFailure("response.body() is null")
                    }
                } else {
                    callback.onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                callback.onFailure(t.localizedMessage ?: "getUserFollowers failure")
            }
        })
    }

    // method to get user following
    fun getUserFollowing(username: String, callback: UserListCallback) {
        USER_SERVICE.getUserFollowing(username).enqueue(object : Callback<List<UserResponse>> {
            override fun onResponse(
                call: Call<List<UserResponse>>,
                response: Response<List<UserResponse>>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        callback.onSuccess(response.body() ?: listOf())
                    } else {
                        callback.onFailure("response.body() is null")
                    }
                } else {
                    callback.onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                callback.onFailure(t.localizedMessage ?: "getUserFollowing failure")
            }
        })
    }
}