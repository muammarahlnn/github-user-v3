package com.ardnn.githubuserv3.api.callbacks

import com.ardnn.githubuserv3.api.responses.UserDetailResponse

interface UserDetailCallback {
    fun onSuccess(userDetail: UserDetailResponse)
    fun onFailure(message: String)
}