package com.dicoding.githubusers2.data.local

import android.content.Context
import android.service.autofill.UserData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [UserFavorite::class],
    version = 1
)
abstract class UserDatabase: RoomDatabase() {
    companion object {
        var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase? {
            if (INSTANCE == null) {
                synchronized(UserDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "user_database"
                    ).build()
                }
            }
            return INSTANCE
        }
    }

    abstract fun userFavoriteDao(): UserFavoriteDao
}