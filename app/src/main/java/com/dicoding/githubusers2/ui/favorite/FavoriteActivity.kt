package com.dicoding.githubusers2.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubusers2.R
import com.dicoding.githubusers2.data.local.UserFavorite
import com.dicoding.githubusers2.data.model.User
import com.dicoding.githubusers2.databinding.ActivityFavoriteBinding
import com.dicoding.githubusers2.ui.detail.DetailUserActivity
import com.dicoding.githubusers2.ui.main.UserAdapter

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                Intent(this@FavoriteActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_AVATAR, data.avatar_url)
                    startActivity(it)
                }
            }
        })
        binding.apply {
            rvFavorite.setHasFixedSize(true)
            rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFavorite.adapter = adapter
        }

        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FavoriteViewModel::class.java)
        viewModel.getUserFavorite()?.observe(this){
            if (it.isEmpty()) {
                val list = mapList(it)
                adapter.setList(list)
                showNoticenotfound(true)
                binding.tvNotfound.text = getString(R.string.nothing_favorite)
            }else{
                val list = mapList(it)
                showNoticenotfound(false)
                adapter.setList(list)
            }
            showLoading(false)

        }
    }

    private fun mapList(users: List<UserFavorite>): ArrayList<User> {
        val listUsers = ArrayList<User>()
        for (user in users) {
            val userMapped = User(
                user.login,
                user.id,
                user.avatar_url
            )
            listUsers.add(userMapped)
        }
        return listUsers
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
}