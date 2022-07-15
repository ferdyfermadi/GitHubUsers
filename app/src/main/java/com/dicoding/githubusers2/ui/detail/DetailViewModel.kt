package com.dicoding.githubusers2.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.githubusers2.api.Retrofit
import com.dicoding.githubusers2.data.local.UserDatabase
import com.dicoding.githubusers2.data.local.UserFavorite
import com.dicoding.githubusers2.data.local.UserFavoriteDao
import com.dicoding.githubusers2.data.model.DetailUserData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    val user = MutableLiveData<DetailUserData>()

    private var userDao: UserFavoriteDao?
    private var userDb: UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.userFavoriteDao()
    }

    fun setUserDetail(username: String) {
        Retrofit.apiInstance
            .getUserDetail(username)
            .enqueue(object : Callback<DetailUserData> {
                override fun onResponse(
                    call: Call<DetailUserData>,
                    response: Response<DetailUserData>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserData>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }
            })
    }

    fun getUserDetail(): LiveData<DetailUserData> {
        return user
    }

    fun addToFavorite(username: String, id: Int, avatarUrl: String){
        CoroutineScope(Dispatchers.IO).launch {
            var user = UserFavorite(
                username,
                id,
                avatarUrl
            )
            userDao?.addToFavorite(user)
        }
    }

    fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFromFavorite (id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
        }
    }
}