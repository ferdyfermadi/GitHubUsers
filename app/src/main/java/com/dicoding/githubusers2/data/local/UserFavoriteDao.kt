package com.dicoding.githubusers2.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserFavoriteDao {
    @Insert
    fun addToFavorite(userFavorite: UserFavorite)

    @Query("SELECT * FROM favorite_user")
    fun getUserFavorite(): LiveData<List<UserFavorite>>

    @Query("SELECT count(*) FROM favorite_user WHERE favorite_user.id = :id")
    fun checkUser(id: Int): Int

    @Query("DELETE FROM favorite_user WHERE favorite_user.id = :id")
    fun removeFromFavorite(id: Int): Int

}