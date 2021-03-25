package com.vn.apps.newz.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.vn.apps.newz.data.local.ArticlesDao
import com.vn.apps.newz.data.remote.NewsRemoteDataSource
import com.vn.apps.newz.utils.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val remoteDataSource: NewsRemoteDataSource,
    private val localDataSource: ArticlesDao
) {

    fun getTopHeadLines() = accessData(
        databaseQuery = { localDataSource.getTopHeadlines() },
        networkCall = { remoteDataSource.getTopHeadLines() },
        saveCallResult = { newsResponse ->
            val articlesItems = newsResponse.articles
            val sortedBy = articlesItems.sortedBy { it.author }
            localDataSource.deleteAll()  //TODO Replace the news based on the publishedAt
            localDataSource.insertAll(sortedBy)

        }

    )

    private fun <T, A> accessData(
        databaseQuery: () -> LiveData<T>,
        networkCall: suspend () -> Resource<A>,
        saveCallResult: suspend (A) -> Unit
    ): LiveData<Resource<T>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading())
            val source = databaseQuery.invoke().map { Resource.success(it) }
            emitSource(source)

            val responseStatus = networkCall.invoke()
            if (responseStatus.status == Resource.Status.SUCCESS) {
                responseStatus.data?.let {
                    saveCallResult(it)
                }

            } else if (responseStatus.status == Resource.Status.ERROR) {
                emit(Resource.error(responseStatus.message!!))
                emitSource(source)
            }
        }
}