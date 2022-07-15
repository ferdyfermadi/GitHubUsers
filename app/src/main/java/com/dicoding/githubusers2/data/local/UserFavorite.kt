package com.dicoding.githubusers2.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite_user")
data class UserFavorite (
    val login: String,
    @PrimaryKey
    val id: Int,
    val avatar_url: String
): Serializable