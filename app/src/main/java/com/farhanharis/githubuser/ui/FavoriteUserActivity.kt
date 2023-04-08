package com.farhanharis.githubuser.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.farhanharis.githubuser.adapter.UserAdapter
import com.farhanharis.githubuser.databinding.ActivityFavoriteUserBinding
import com.farhanharis.githubuser.remote.response.ItemsItem
import com.farhanharis.githubuser.viewmodel.FavUserViewModel
import com.farhanharis.githubuser.viewmodel.FavUserViewModelFactory

class FavoriteUserActivity : AppCompatActivity() {

    private var _activityFavoriteUserBinding : ActivityFavoriteUserBinding? = null
    private val binding get() = _activityFavoriteUserBinding

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionBar = supportActionBar
        actionBar?.setDefaultDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title= "Favorite User"

        _activityFavoriteUserBinding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.rvFavoriteUser?.layoutManager = LinearLayoutManager(this)
        binding?.rvFavoriteUser?.setHasFixedSize(true)

        val favUserViewModel = obtainViewModel(this)


        favUserViewModel.getAllUserFavorite().observe(this) { favoriteUser ->
            if(favoriteUser != null){
                setFavoriteUserData(favoriteUser)
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity) : FavUserViewModel{
        val factory = FavUserViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavUserViewModel::class.java]
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setFavoriteUserData(listUser:List<ItemsItem>) {
        val userAdapter = UserAdapter(listUser)
        binding?.rvFavoriteUser?.adapter = userAdapter

        userAdapter.setOntItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ItemsItem) {
                val intent = Intent(this@FavoriteUserActivity, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.USERNAME,data.login)
                intent.putExtra(DetailUserActivity.KEY_USERS, data)
                startActivity(intent)
            }
        })
    }
}