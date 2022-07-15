package com.dicoding.githubusers2.ui.darkmode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DarkModeViewModelFactory(private val pref: DarkModePreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DarkModeViewModel::class.java)) {
            return DarkModeViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}