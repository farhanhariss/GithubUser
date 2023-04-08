package com.farhanharis.githubuser.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.farhanharis.githubuser.ui.DetailUserFragment

class SectionsPageAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var username : String = ""

    override fun getItemCount(): Int {
        return 2
    }
    override fun createFragment(position: Int): Fragment {
        val fragment = DetailUserFragment()
        fragment.arguments = Bundle().apply {
            putInt(DetailUserFragment.ARG_POSITION, position + 1)
            putString(DetailUserFragment.ARG_USERNAME, username)
        }
        return fragment
    }

}