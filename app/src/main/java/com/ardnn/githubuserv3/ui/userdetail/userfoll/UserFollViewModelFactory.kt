package com.ardnn.githubuserv3.ui.userdetail.userfoll

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UserFollViewModelFactory(
    private val section: Int,
    private val username: String
) : ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserFollViewModel::class.java)) {
            return UserFollViewModel(section, username) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

}