package com.dicoding.githubusers2.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.CompoundButton
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubusers2.R
import com.dicoding.githubusers2.data.model.User
import com.dicoding.githubusers2.databinding.ActivityMainBinding
import com.dicoding.githubusers2.ui.darkmode.*
import com.dicoding.githubusers2.ui.detail.DetailUserActivity
import com.dicoding.githubusers2.ui.favorite.FavoriteActivity
import com.google.android.material.switchmaterial.SwitchMaterial

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "Dark Mode")

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel : UsersViewModel
    private lateinit var adapter: UserAdapter
    private var switch: Switch? = null
    private lateinit var saveDarkMode: SaveDarkMode

    override fun onCreate(savedInstanceState: Bundle?) {

        saveDarkMode = SaveDarkMode(this)
        if (saveDarkMode.loadDarkModeState() == true) {
            setTheme(R.style.Theme_GitHubUsers2)
        } else {
            setTheme(R.style.Theme_GitHubUsers2)
        }

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_AVATAR, data.avatar_url)
                    startActivity(it)
                }
            }
        })

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UsersViewModel::class.java)

        binding.apply {
            rvGithub.layoutManager = LinearLayoutManager(this@MainActivity)
            rvGithub.setHasFixedSize(true)
            rvGithub.adapter = adapter

            btnSearch.setOnClickListener{
                searchUser()
            }

            etQuery.setOnKeyListener{v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    searchUser()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        viewModel.getSearchUsers().observe(this) {
            if (it.isEmpty()){
                adapter.setList(it)
                binding.tvNotfound.text = getString(R.string.user_not_found)
                showNoticenotfound(true)
                showLoading(true)
            } else {
                showNoticenotfound(false)
                adapter.setList(it)
            }
            showLoading(false)
        }

        switch = findViewById(R.id.switch_theme) as Switch?
        if (saveDarkMode.loadDarkModeState() == true){
            switch?.isChecked = true
        }

        val pref = DarkModePreferences.getInstance(dataStore)
        val mainViewModel = ViewModelProvider(this, DarkModeViewModelFactory(pref)).get(
            DarkModeViewModel::class.java
        )
        mainViewModel.getThemeSettings().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            })
    }
    private fun searchUser(){
        binding.apply {
            val query = etQuery.text.toString()
            if(query.isEmpty()) return
            showLoading (true)
            viewModel.setSearchUser(query)
        }
    }

    private fun showLoading(state: Boolean){
        if(state) {
            binding.progressBar.visibility = View.VISIBLE
        }else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showNoticenotfound(state: Boolean){
        if(state) {
            binding.tvNotfound.visibility = View.VISIBLE
        }else {
            binding.tvNotfound.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.my_favorite -> {
                Intent(this, FavoriteActivity::class.java).also{
                    startActivity(it)
                }
            }
            R.id.setting -> {
                    Intent(this, DarkModeActivity::class.java).also{
                        startActivity(it)
                    }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}