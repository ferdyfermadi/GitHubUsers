package com.dicoding.githubusers2.api

import com.dicoding.githubusers2.data.model.DetailUserData
import com.dicoding.githubusers2.data.model.User
import com.dicoding.githubusers2.data.model.UserData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    @Headers("Authorization: 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")
    fun getUserSearch(
        @Query("q") query: String
    ): Call<UserData>

    @GET("users/{username}")
    @Headers("Authorization: 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")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserData>

    @GET("users/{username}/followers")
    @Headers("Authorization: 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}