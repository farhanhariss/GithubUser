package com.farhanharis.githubuser.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    val username : String,
    val avatar : Int,
    val name : String,
    val followers : String,
    val following : String,
) : Parcelable