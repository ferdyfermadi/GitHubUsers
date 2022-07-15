package com.dicoding.githubusers2.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.githubusers2.data.local.UserDatabase
import com.dicoding.githubusers2.data.local.UserFavorite
import com.dicoding.githubusers2.data.local.UserFavoriteDao

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private var userDao: UserFavoriteDao?
    private var userDb: UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.userFavoriteDao()
    }

    fun getUserFavorite(): LiveData<List<UserFavorite>>? {
        return userDao?.getUserFavorite()
    }

}