package com.dicoding.githubusers2.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dicoding.githubusers2.databinding.ActivityDetailUserBinding
import com.dicoding.githubusers2.ui.main.UserAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR = "extra_avatar"
    }

    private lateinit var binding : ActivityDetailUserBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatar = intent.getStringExtra(EXTRA_AVATAR)
        val bundle = Bundle()

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        bundle.putString(EXTRA_USERNAME, username)

        showLoading(true)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        viewModel.setUserDetail(username.toString())
        viewModel.getUserDetail().observe(this) {
            if (it != null){
                binding.apply {
                    tvNameuser.text = it.name
                    tvUsername.text = it.login
                    tvCompany.text = it.company
                    tvLocation.text = it.location
                    tvRepository.text = "Repository ${it.public_repos}"
                    tvFollowers.text = "${it.followers} Followers"
                    tvFollowing.text = "${it.following} Following"
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(imageViewUser)
                    showLoading(false)
                }
            }
        }

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if (count != null){
                    if(count > 0) {
                        binding.togglebtnFavorite.isChecked = true
                        _isChecked = true
                    } else {
                        binding.togglebtnFavorite.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.togglebtnFavorite.setOnClickListener{
            _isChecked = !_isChecked
            if (_isChecked){
                viewModel.addToFavorite(username.toString(), id, avatar.toString())
            } else {
                viewModel.removeFromFavorite(id)
            }
            binding.togglebtnFavorite.isChecked = _isChecked
        }

        val sectionPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabsLayout.setupWithViewPager(viewPager)
        }
    }

    private fun showLoading(state: Boolean){
        if(state) {
            binding.progressBar.visibility = View.VISIBLE
        }else {
            binding.progressBar.visibility = View.GONE
        }
    }
}