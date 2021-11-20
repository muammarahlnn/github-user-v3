package com.ardnn.githubuserv3.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardnn.githubuserv3.api.callbacks.UserListCallback
import com.ardnn.githubuserv3.api.repositories.SearchedUserRepository
import com.ardnn.githubuserv3.api.responses.UserResponse

class MainViewModel : ViewModel(){
    companion object {
        private const val TAG = "MainViewModel"
    }

    private val _searchedUsers = MutableLiveData<List<UserResponse>>()
    val searchedUsers: LiveData<List<UserResponse>> = _searchedUsers

    private val _isSearched = MutableLiveData(false)
    val isSearched: LiveData<Boolean> = _isSearched

    private val _isSearchedUsersEmpty = MutableLiveData<Boolean>()
    val isSearchedUsersEmpty: LiveData<Boolean> = _isSearchedUsersEmpty

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isFailure = MutableLiveData<Boolean>()
    val isFailure: LiveData<Boolean> = _isFailure

    fun setSearchedUsers(searchedWords: String) {
        // first searching
        _isSearched.value = true

        // show loading
        _isLoading.value = true

        // make it false so the alert text gonna be invisible everytime we searching
        _isSearchedUsersEmpty.value = false

        SearchedUserRepository.getSearchedUsers(searchedWords, object : UserListCallback {
            override fun onSuccess(userList: List<UserResponse>) {
                _isLoading.value = false // hide loading
                _isFailure.value = false // success fetch data

                // check is list empty or not
                _isSearchedUsersEmpty.value = userList.isNullOrEmpty()

                // set searched users value
                _searchedUsers.value = userList
            }

            override fun onFailure(message: String) {
                _isLoading.value = false // hide loading
                _isFailure.value = true // unable to fetch data
                Log.d(TAG, message)
            }

        })
    }
}