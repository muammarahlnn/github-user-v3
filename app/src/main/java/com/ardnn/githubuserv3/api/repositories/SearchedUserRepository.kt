package com.ardnn.githubuserv3.api.repositories

import com.ardnn.githubuserv3.api.callbacks.UserListCallback
import com.ardnn.githubuserv3.api.responses.SearchedUserResponse
import com.ardnn.githubuserv3.api.services.SearchUserApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SearchedUserRepository {
    private val SEARCHED_USER_SERVICE =
        Retrofit.Builder()
            .baseUrl(Consts.SEARCH_USER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SearchUserApiService::class.java)


    // method to get searched users
    fun getSearchedUsers(newText: String, callback: UserListCallback) {
        SEARCHED_USER_SERVICE.getSearchedUsers(newText).enqueue(object : Callback<SearchedUserResponse> {
            override fun onResponse(
                call: Call<SearchedUserResponse>,
                response: Response<SearchedUserResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        if (response.body()?.searchedUser != null) {
                            callback.onSuccess(response.body()?.searchedUser ?: listOf())
                        } else {
                            callback.onFailure("response.body().searchedUser is null")
                        }
                    } else {
                        callback.onFailure("response.body() is null")
                    }
                } else {
                    callback.onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<SearchedUserResponse>, t: Throwable) {
                callback.onFailure(t.localizedMessage ?: "getSearchedUsers failure")
            }

        })
    }
}