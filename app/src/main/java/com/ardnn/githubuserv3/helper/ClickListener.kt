package com.ardnn.githubuserv3.helper

import com.ardnn.githubuserv3.api.responses.UserResponse

interface ClickListener {
    fun onItemClicked(user: UserResponse)
}