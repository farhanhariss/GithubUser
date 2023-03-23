package com.farhanharis.githubuser

import android.content.ClipData.Item
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.farhanharis.githubuser.databinding.ActivityMainBinding
import com.farhanharis.githubuser.remote.GithubResponse
import com.farhanharis.githubuser.remote.ItemsItem
import com.farhanharis.githubuser.remote.network.ApiConfig
import com.farhanharis.githubuser.viewmodel.MainViewModel
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {

    val listUser = ArrayList<ItemsItem>()
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var mainViewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]
        mainViewModel.listUser.observe(
            this,
        ){listUser ->
            setListUserData(listUser)
        }
    }

    private fun setListUserData(listUser: List<ItemsItem>) {
        val adapter = UserAdapter(listUser)

        activityMainBinding.rvUser.adapter = adapter
        activityMainBinding.rvUser.layoutManager = LinearLayoutManager(this)
    }


    //Menampilkan Loading Bar
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            activityMainBinding.progessBar.visibility = View.VISIBLE
        } else {
            activityMainBinding.progessBar.visibility = View.INVISIBLE
        }


    }
}