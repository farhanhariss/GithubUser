package com.farhanharis.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.farhanharis.githubuser.databinding.ActivityDetailUserBinding
import com.farhanharis.githubuser.remote.ItemsItem
import com.farhanharis.githubuser.viewmodel.MainViewModel

class DetailUserActivity : AppCompatActivity() {

    private lateinit var detailUserBinding: ActivityDetailUserBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailUserBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(detailUserBinding.root)


        var username = intent.getStringExtra("username")
        mainViewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]

        if (username != null) {
            detailUser(username)
        }

        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }
    }


    //dah bener
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

    private fun showLoading(isLoading : Boolean){
        //progressBar mengikuti ID yang dibuat di acitivity
        if (isLoading){
            detailUserBinding.progressBar.visibility = View.VISIBLE
        }else {
            detailUserBinding.progressBar.visibility = View.INVISIBLE
        }

    }




}