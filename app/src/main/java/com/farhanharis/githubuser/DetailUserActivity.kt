package com.farhanharis.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.farhanharis.githubuser.adapter.SectionsPageAdapter
import com.farhanharis.githubuser.databinding.ActivityDetailUserBinding
import com.farhanharis.githubuser.viewmodel.MainViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var detailUserBinding: ActivityDetailUserBinding
    private lateinit var mainViewModel: MainViewModel

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers_detail_user,
            R.string.following_detail_user
        )
        const val USERNAME = "username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailUserBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(detailUserBinding.root)


        val username = intent.getStringExtra(USERNAME)
        mainViewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]

        if (username != null) {
            setPagerAdapter(username)
            detailUser(username)
        }

        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }
    }

    private fun setPagerAdapter(username : String){
        val sectionsPagerAdapter = SectionsPageAdapter(this)
        val viewPager: ViewPager2 = detailUserBinding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = detailUserBinding.tabs
        sectionsPagerAdapter.username = username
        TabLayoutMediator(tabs, viewPager){ tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }


    private fun detailUser(username : String){
        mainViewModel.githubGetDetailUser(username)
        mainViewModel.detailDataUser.observe(this){ DetailUserResponse ->
            detailUserBinding.detailUserFullname.text = DetailUserResponse.name
            detailUserBinding.detailUserUsername.text = DetailUserResponse.login
            Glide.with(this)
                .load(DetailUserResponse.avatarUrl)
                .into(detailUserBinding.detailUserAvatar)
            detailUserBinding.detailUserFollowing.text = getString(R.string.user_number_following, DetailUserResponse.following)
            detailUserBinding.detailUserFollowers.text = getString(R.string.user_number_followers, DetailUserResponse.followers)
        }
    }

    private fun showLoading(state: Boolean) { detailUserBinding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }
}