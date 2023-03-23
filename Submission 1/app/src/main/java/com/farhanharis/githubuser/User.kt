package com.farhanharis.githubuser

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    val username : String,
    val avatar : Int = 0,
    val name : String,
    val followers : String,
    val following : String,
    ) : Parcelable