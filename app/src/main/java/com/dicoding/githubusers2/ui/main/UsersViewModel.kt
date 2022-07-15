package com.dicoding.githubusers2.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.dicoding.githubusers2.api.Retrofit
import com.dicoding.githubusers2.data.model.User
import com.dicoding.githubusers2.data.model.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersViewModel : ViewModel() {
    val listUsers = MutableLiveData<ArrayList<User>>()

    fun setSearchUser(query: String){
        Retrofit.apiInstance
            .getUserSearch(query)
            .enqueue(object : Callback<UserData> {
                override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                    if (response.isSuccessful){
                        listUsers.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserData>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getSearchUsers(): LiveData<ArrayList<User>>{
        return listUsers
    }
}