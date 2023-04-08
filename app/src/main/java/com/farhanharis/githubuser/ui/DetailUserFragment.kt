package com.farhanharis.githubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farhanharis.githubuser.R
import com.farhanharis.githubuser.adapter.UserAdapter
import com.farhanharis.githubuser.remote.response.ItemsItem
import com.farhanharis.githubuser.viewmodel.MainViewModel


class DetailUserFragment : Fragment() {

    private lateinit var mainViewModel : MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    companion object{
        const val ARG_POSITION = "section_number"
        const val ARG_USERNAME = "username"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detail_user, container, false)
        recyclerView = view.findViewById(R.id.rvDetailUser)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        progressBar = view.findViewById(R.id.progressBar_detail)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            val position = arguments?.getInt(ARG_POSITION)
            val username = arguments?.getString(ARG_USERNAME)
            mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]


        if(position == 1){
            mainViewModel.githubGetFollowing(username.toString())
            mainViewModel.detailUsersFollowing.observe(viewLifecycleOwner){ listFollowing ->
                setUserFollowers(listFollowing)
            }
        }
        else{
            mainViewModel.githubGetFollowers(username.toString())
            mainViewModel.detailUsersFollowers.observe(viewLifecycleOwner){ listFollowers ->
                setUserFollowing(listFollowers)
            }
        }

        mainViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }


    }

    private fun setUserFollowing(listFollowing : List<ItemsItem>){
        val adapter = UserAdapter(listFollowing)
        recyclerView.adapter = adapter
    }

    private fun setUserFollowers(listFollowers : List<ItemsItem>){
        val adapter = UserAdapter(listFollowers)
        recyclerView.adapter = adapter
    }

    private fun showLoading(isLoading : Boolean){
        if(isLoading){
            progressBar.visibility = View.VISIBLE
        }else{
            progressBar.visibility = View.GONE
        }
    }

}
