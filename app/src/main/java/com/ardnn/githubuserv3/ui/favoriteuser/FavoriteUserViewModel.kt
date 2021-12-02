package com.ardnn.githubuserv3.ui.favoriteuser

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardnn.githubuserv3.database.FavoriteUser
import com.ardnn.githubuserv3.database.FavoriteUserRepository

class FavoriteUserViewModel(application: Application) : ViewModel() {
    private val favoriteUserRepository = FavoriteUserRepository(application)

    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>> {
        return favoriteUserRepository.getAllFavoriteUsers()
    }

    fun deleteAllFavorites() {
        favoriteUserRepository.deleteAllFavoriteUsers()
    }
}