package com.ardnn.githubuserv3.database

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val favoriteUserDao: FavoriteUserDao =
        FavoriteUserDatabase.getDatabase(application).getFavoriteUserDao()
    private val executorService: ExecutorService =
        Executors.newSingleThreadExecutor()

    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>> {
        return favoriteUserDao.getAllFavoriteUsers()
    }

    fun insert(favoriteUser: FavoriteUser) {
        executorService.execute {
            favoriteUserDao.insert(favoriteUser)
        }
    }

    fun delete(favoriteUser: FavoriteUser) {
        executorService.execute {
            favoriteUserDao.delete(favoriteUser)
        }
    }

    suspend fun getFavoriteUser(username: String): FavoriteUser {
        return favoriteUserDao.getFavoriteUser(username)
    }

    suspend fun isFavoriteUserExists(username: String): Boolean {
        return favoriteUserDao.isFavoriteUserExists(username)
    }
}