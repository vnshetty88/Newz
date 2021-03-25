package com.vn.apps.newz.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vn.apps.newz.data.entities.ArticlesItem

@Dao
interface ArticlesDao {

    @Query("SELECT * FROM articles")
    fun getTopHeadlines(): LiveData<List<ArticlesItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articlesItems: List<ArticlesItem>)

    @Query("DELETE FROM articles")
    fun deleteAll()
}