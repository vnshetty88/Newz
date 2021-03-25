package com.vn.apps.newz.data.entities

import com.google.gson.annotations.SerializedName

data class NewsResponse(@SerializedName("totalResults")
                           val totalResults: Int = 0,
                        @SerializedName("articles")
                           val articles: List<ArticlesItem>,
                        @SerializedName("status")
                           val status: String = "")