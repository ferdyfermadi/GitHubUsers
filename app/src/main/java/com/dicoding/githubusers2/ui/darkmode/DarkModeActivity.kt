package com.dicoding.githubusers2.ui.darkmode

import android.content.Context
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubusers2.R
import com.google.android.material.switchmaterial.SwitchMaterial

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "Dark Mode")

class DarkModeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_darkmode)

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        val pref = DarkModePreferences.getInstance(dataStore)
        val mainViewModel = ViewModelProvider(this, DarkModeViewModelFactory(pref)).get(
            DarkModeViewModel::class.java
        )
        mainViewModel.getThemeSettings().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    switchTheme.isChecked = true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    switchTheme.isChecked = false
                }
            })

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            mainViewModel.saveThemeSetting(isChecked)
        }
    }
}