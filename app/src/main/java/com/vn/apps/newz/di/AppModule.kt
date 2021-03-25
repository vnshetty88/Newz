package com.vn.apps.newz.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vn.apps.newz.data.local.AppDatabase
import com.vn.apps.newz.data.local.ArticlesDao
import com.vn.apps.newz.data.remote.NewsRemoteDataSource
import com.vn.apps.newz.data.remote.NewsService
import com.vn.apps.newz.data.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val API_KEY = "a0d3ed25d07742adbc1b585780f79580"
const val BASE_URL = "https://newsapi.org/v2/"

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(
            OkHttpClient.Builder()
                .addInterceptor(Interceptor { chain ->
                    val url = chain.request()
                        .url()
                        .newBuilder()
                        .addQueryParameter("apiKey", API_KEY)
                        .build()
                    val request = chain.request()
                        .newBuilder()
                        .url(url)
                        .build()

                    return@Interceptor chain.proceed(request)
                })
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideNewsService(retrofit: Retrofit): NewsService =
        retrofit.create(NewsService::class.java)

    @Singleton
    @Provides
    fun provideNewsRemoteDataSource(newsService: NewsService) =
        NewsRemoteDataSource(newsService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideNewsDao(db: AppDatabase) = db.articlesDao()

    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: NewsRemoteDataSource,
        localDataSource: ArticlesDao
    ) = NewsRepository(remoteDataSource, localDataSource)
}