package com.farhanharis.githubuser.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.farhanharis.githubuser.R
import com.farhanharis.githubuser.adapter.SectionsPageAdapter
import com.farhanharis.githubuser.databinding.ActivityDetailUserBinding
import com.farhanharis.githubuser.remote.response.ItemsItem
import com.farhanharis.githubuser.viewmodel.DetailViewModel
import com.farhanharis.githubuser.viewmodel.FavUserViewModelFactory
import com.farhanharis.githubuser.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var detailUserBinding: ActivityDetailUserBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var mainViewModel: MainViewModel
    private var isFav : Boolean = false

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers_detail_user,
            R.string.following_detail_user
        )
        const val USERNAME = "username"
        const val KEY_USERS = "key_user"
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailUserBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(detailUserBinding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        //Obtain View Model
        mainViewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]
        detailViewModel = obtainViewModel(this)

        val username = intent.getStringExtra(USERNAME)

        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }

        if (username != null) {

            setPagerAdapter(username)
            detailUser(username)
        }
        val favUser = intent.getParcelableExtra<ItemsItem>(KEY_USERS) as ItemsItem

        detailViewModel.getAllUser().observe(this){
            isFav = it.contains(favUser)
            if(isFav){
                detailUserBinding.fabAdd.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_favorite_border,theme
                    )
                )
            } else{
                detailUserBinding.fabAdd.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_favorite,theme
                    )
                )
            }
        }

        detailUserBinding.fabAdd.setOnClickListener {
            if(isFav){
                detailViewModel.delete(favUser)
                detailUserBinding.fabAdd.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_favorite_border,theme
                    )
                )
                Snackbar.make(
                    detailUserBinding.root,
                    StringBuilder(favUser.login + " ").append(R.string.delete_from_favorite),
                    Snackbar.LENGTH_LONG
                )
                    .setAction(
                        R.string.undo
                    ){
                        detailViewModel.insert(favUser)
                        Toast.makeText(this,R.string.undo, Toast.LENGTH_SHORT)
                            .show()
                    }.show()
            }else{
                detailViewModel.insert(favUser)
                detailUserBinding.fabAdd.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_favorite,theme
                    )
                )
                Snackbar.make(
                    detailUserBinding.root,
                    StringBuilder(favUser.login + " ").append(R.string.insert_into_favorite),
                    Snackbar.LENGTH_LONG
                )
                    .setAction(
                        R.string.check_favorite
                    ){
                        startActivity(Intent(this, FavoriteUserActivity::class.java))
                    }.show()
            }
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

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel{
        val factory = FavUserViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity,factory)[DetailViewModel::class.java]
    }

    // Menangani klik tombol "Back" pada App Bar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showLoading(state: Boolean) { detailUserBinding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }
}