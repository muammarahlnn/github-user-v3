package com.ardnn.githubuserv3.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteUser: FavoriteUser)

    @Delete
    fun delete(favoriteUser: FavoriteUser)

    @Query("SELECT * FROM favorite_user ORDER BY username ASC")
    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>>

    @Query("SELECT * FROM favorite_user WHERE username = :username LIMIT 1")
    suspend fun getFavoriteUser(username: String): FavoriteUser

    @Query("SELECT EXISTS (SELECT * FROM favorite_user WHERE username = :username)")
    suspend fun isFavoriteUserExists(username: String): Boolean
}