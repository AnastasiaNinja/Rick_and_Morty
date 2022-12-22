package com.rickandmorty.model.di

import com.rickandmorty.App
import com.rickandmorty.model.api.ApiService
import com.rickandmorty.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Provides
    @Singleton
    fun provideRetrofitInstance(BASE_URL :String): ApiService =
        Retrofit.Builder()
           .baseUrl(BASE_URL)
            .client(createHttpClient())
           .addConverterFactory(GsonConverterFactory.create())
           .build()
           .create(ApiService::class.java)

    fun createHttpClient(): OkHttpClient{
        val httpCacheDirectory = File(App.instance.applicationContext.cacheDir, "responses")
        val cacheSize: Long = 10 * 1024 * 1024 // 10 MiB

        val cache = Cache(httpCacheDirectory, cacheSize)
        return OkHttpClient().newBuilder().cache(cache).addNetworkInterceptor(CachingInterceptor()).build()
    }
}