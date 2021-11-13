package com.ardnn.githubuserv3.api.callbacks

import com.ardnn.githubuserv3.api.responses.UserResponse

interface UserListCallback {
    fun onSuccess(userList: List<UserResponse>)
    fun onFailure(message: String)
}