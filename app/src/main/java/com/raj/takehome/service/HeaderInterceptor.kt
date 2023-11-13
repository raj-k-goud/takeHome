package com.raj.takehome.service

import okhttp3.Interceptor
import okhttp3.Response

/*
* HeaderInterceptor class to add common headers to API class
 */
class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("app-id", "TakeHome")
                .addHeader("device-platform", "android")
                .addHeader("User-Agent", "android Mobile")
                .build()
        )
    }
}