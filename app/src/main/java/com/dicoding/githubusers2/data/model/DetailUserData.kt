package com.dicoding.githubusers2.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailUserData(
    val login: String,
    val id: Int,
    val avatar_url: String,
    val followers_url: String,
    val following_url: String,
    val name: String,
    val company: String,
    val location: String,
    val public_repos: String,
    val followers: String,
    val following: String,
): Parcelable
