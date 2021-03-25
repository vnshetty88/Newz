package com.vn.apps.newz.data.remote

import com.vn.apps.newz.data.entities.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("top-headlines")
    suspend fun getTopHeadLines(
        @Query(value = "country") country: String = "us",
        @Query(value = "pageSize") pageSize: String = "100"

    ): Response<NewsResponse>
}