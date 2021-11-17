package com.ardnn.githubuserv3.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardnn.githubuserv3.api.callbacks.UserDetailCallback
import com.ardnn.githubuserv3.api.repositories.UserRepository
import com.ardnn.githubuserv3.api.responses.UserDetailResponse

class UserDetailViewModel(
    private val username: String
) : ViewModel() {

    companion object {
        private const val TAG = "UserDetailViewModel"
    }

    private val _user = MutableLiveData<UserDetailResponse>()
    val user: LiveData<UserDetailResponse> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isFailure = MutableLiveData<Boolean>()
    val isFailure: LiveData<Boolean> = _isFailure

    init {
        fetchUserDetail(username)
    }

    private fun fetchUserDetail(username: String) {
        // show progressbar
        _isLoading.value = true

        UserRepository.getUserDetail(username, object : UserDetailCallback {
            override fun onSuccess(userDetail: UserDetailResponse) {
                _isLoading.value = false // hide progressbar
                _isFailure.value = false  // success fetch data
                _user.value = userDetail
            }

            override fun onFailure(message: String) {
                // unable to fetch data
                _isFailure.value = true
                Log.d(TAG, message)
            }
        })
    }
}