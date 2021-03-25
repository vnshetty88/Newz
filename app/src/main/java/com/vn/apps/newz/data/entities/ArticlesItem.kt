package com.vn.apps.newz.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "articles")
data class ArticlesItem(
    @PrimaryKey(autoGenerate = true)
    var articleId: Int,
    @SerializedName("author")
    val author: String?,
    @SerializedName("urlToImage")
    val urlToImage: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("description")
    val content: String?
)
