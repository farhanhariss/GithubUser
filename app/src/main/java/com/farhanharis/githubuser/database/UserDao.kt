package com.farhanharis.githubuser.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.farhanharis.githubuser.remote.response.ItemsItem
import com.farhanharis.githubuser.ui.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavoriteUser(favoriteUser: ItemsItem)

    @Update
    fun updateFavoriteUser(favoriteUser: ItemsItem)

    @Delete
    fun deleteFavoriteUser(favoriteUser: ItemsItem)

    @Query("SELECT * FROM itemUser ORDER BY login ASC") //itemUser -> nama database
    fun getAllFavoriteUser(): LiveData<List<ItemsItem>>
}