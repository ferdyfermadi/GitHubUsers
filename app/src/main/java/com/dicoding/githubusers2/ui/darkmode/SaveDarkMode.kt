package com.dicoding.githubusers2.ui.darkmode

import android.content.Context
import android.content.SharedPreferences

class SaveDarkMode (context: Context) {
    private var sharePreferences: SharedPreferences = context.getSharedPreferences("Dark Mode", Context.MODE_PRIVATE)


    fun setDarkModeState (state: Boolean?) {
        val editor = sharePreferences.edit()
        editor.putBoolean("Dark", state!!)
        editor.apply()
    }

    fun loadDarkModeState(): Boolean? {
        val state = sharePreferences.getBoolean("Dark", false)
        return (state)
    }
}