package com.farhanharis.githubuser.remote.response

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class GithubResponse(

	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean,

	@field:SerializedName("items")
	val items: List<ItemsItem>
)

@Entity(tableName = "itemUser")
@Parcelize
data class ItemsItem(
	@field:PrimaryKey
	@field:ColumnInfo(name = "login")
	@field:SerializedName("login")
	val login: String,

	@field:ColumnInfo(name = "avatarUrl")
	@field:SerializedName("avatar_url")
	val avatarUrl: String,
):Parcelable
