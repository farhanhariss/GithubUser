package com.farhanharis.githubuser.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.farhanharis.githubuser.R
import com.farhanharis.githubuser.adapter.UserAdapter
import com.farhanharis.githubuser.databinding.ActivityMainBinding
import com.farhanharis.githubuser.remote.response.ItemsItem
import com.farhanharis.githubuser.viewmodel.MainViewModel
import com.farhanharis.githubuser.viewmodel.SettingViewModel
import com.farhanharis.githubuser.viewmodel.ViewModelFactory
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var mainViewModel : MainViewModel
    private lateinit var progressBar : ProgressBar


    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val pref = SettingPreferences.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(
            this,
            ViewModelFactory(pref)
        )[SettingViewModel::class.java]
        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.favorite ->{
                startActivity(Intent(this@MainActivity,FavoriteUserActivity::class.java))
                true
            }
            R.id.setting ->{
                val intentSetting = Intent(this@MainActivity,SettingActivity::class.java)
                startActivity(intentSetting)
                true
            }
            else -> true

        }
    }

    private fun setListUserData(listUser: List<ItemsItem>) {
        val adapter = UserAdapter(listUser)
        adapter.setOntItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ItemsItem) {
                val intent = Intent(this@MainActivity, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.USERNAME, data.login)
                intent.putExtra(DetailUserActivity.KEY_USERS, data)
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