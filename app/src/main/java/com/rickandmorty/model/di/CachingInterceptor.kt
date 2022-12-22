package com.rickandmorty.model.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.rickandmorty.App
import okhttp3.Interceptor
import okhttp3.Response


class CachingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        return if (isOnline()) {
            val maxAge = 60 // read from cache for 1 minute
            originalResponse.newBuilder()
                .header("Cache-Control", "public, max-age=$maxAge")
                .build()
        } else {
            val maxStale = 60 * 60 * 24 * 28 // tolerate 4-weeks stale
            originalResponse.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                .build()
        }
    }

    fun isOnline(): Boolean {
        val cm = App.instance
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (cm.activeNetwork == null) {
            return false
        }
        val netInfo = cm.getNetworkCapabilities(cm.activeNetwork)
        return netInfo != null && (netInfo.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                || netInfo.hasTransport(NetworkCapabilities.TRANSPORT_WIFI));

    }
}