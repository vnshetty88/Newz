package com.vn.apps.newz.data.remote

import javax.inject.Inject

class NewsRemoteDataSource @Inject constructor(
    private val newsService: NewsService
): BaseDataSource() {

    suspend fun getTopHeadLines() = getResult {
        newsService.getTopHeadLines()
    }
}