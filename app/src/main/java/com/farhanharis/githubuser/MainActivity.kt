package com.farhanharis.githubuser

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.farhanharis.githubuser.adapter.UserAdapter
import com.farhanharis.githubuser.databinding.ActivityMainBinding
import com.farhanharis.githubuser.remote.response.ItemsItem
import com.farhanharis.githubuser.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var mainViewModel : MainViewModel
    private lateinit var progressBar : ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        progressBar = findViewById(R.id.progess_bar)

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]

        //Set User Data
        mainViewModel.listUser.observe(
            this,
        ){listUser ->
            setListUserData(listUser)
        }

        // Observe ProgressBar
        mainViewModel.isLoading.observe(this,
        ){
            showLoading(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        val searchManager  = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val menuItem = menu.findItem(R.id.menu_search)

        if(menuItem != null){
            val searchView = menuItem.actionView as SearchView
            searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
            searchView.queryHint = resources.getString(R.string.search_hint)
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {

                    if(!query.isNullOrEmpty()){
                        mainViewModel.run { githubFindUser(query) }
                    }

                    searchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
        return true
    }

    private fun setListUserData(listUser: List<ItemsItem>) {
        val adapter = UserAdapter(listUser)
        adapter.setOntItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ItemsItem) {
                val intent = Intent(this@MainActivity, DetailUserActivity::class.java)
                intent.putExtra("username", data.login)
                startActivity(intent)
            }
        })
        activityMainBinding.rvUser.adapter = adapter
        activityMainBinding.rvUser.layoutManager = LinearLayoutManager(this)

    }

    private fun showLoading(isLoading : Boolean){
        if (isLoading){
            activityMainBinding.progessBar.visibility = View.VISIBLE
        }else {
            activityMainBinding.progessBar.visibility = View.GONE
        }
    }


}