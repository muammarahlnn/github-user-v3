package com.ardnn.githubuserv3.ui.userdetail.userfoll

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardnn.githubuserv3.api.callbacks.UserListCallback
import com.ardnn.githubuserv3.api.repositories.UserRepository
import com.ardnn.githubuserv3.api.responses.UserResponse

class UserFollViewModel(
    section: Int,
    username: String
) : ViewModel() {

    companion object {
        private const val TAG = "UserFollViewModel"
    }

    private val _followerList = MutableLiveData<List<UserResponse>>()
    val followerList: LiveData<List<UserResponse>> = _followerList

    private val _followingList = MutableLiveData<List<UserResponse>>()
    val followingList: LiveData<List<UserResponse>> = _followingList

    private val _isListEmpty = MutableLiveData(false)
    val isListEmpty: LiveData<Boolean> = _isListEmpty

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isFailure = MutableLiveData<Boolean>()
    val isFailure: LiveData<Boolean> = _isFailure

    init {
        when (section) {
            0 -> { // followers
                setUserFollowers(username)
            }
            1 -> { // following
                setUserFollowing(username)
            }
        }
    }

    private fun setUserFollowers(username: String) {
        // show progressbar
        _isLoading.value = true

        UserRepository.getUserFollowers(username, object : UserListCallback {
            override fun onSuccess(userList: List<UserResponse>) {
                _isLoading.value = false // hide progressbar
                _isFailure.value = false // success fetch data

                _followerList.value = userList
                if (userList.isNullOrEmpty()) {
                    _isListEmpty.value = true // list empty
                }
            }

            override fun onFailure(message: String) {
                _isLoading.value = false // hide progressbar
                _isFailure.value = true // unable to fetch data
                Log.d(TAG, message)
            }

        })
    }

    private fun setUserFollowing(username: String) {
        // show progressbar
        _isLoading.value = true

        UserRepository.getUserFollowing(username, object : UserListCallback {
            override fun onSuccess(userList: List<UserResponse>) {
                _isLoading.value = false // hide progressbar
                _isFailure.value = false // success fetch data

                _followingList.value = userList
                if (userList.isNullOrEmpty()) {
                    _isListEmpty.value = true // list empty
                }
            }

            override fun onFailure(message: String) {
                _isLoading.value = false // hide progressbar
                _isFailure.value = true // unable to fetch data
                Log.d(TAG, message)
            }
        })
    }
}