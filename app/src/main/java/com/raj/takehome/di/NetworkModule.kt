package com.raj.takehome.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.raj.takehome.utils.Constants
import com.raj.takehome.utils.Constants.CONN_TIMEOUT
import com.raj.takehome.utils.Constants.READ_TIMEOUT
import com.raj.takehome.service.ApiHelper
import com.raj.takehome.service.ApiHelperImpl
import com.raj.takehome.service.ApiService
import com.raj.takehome.service.HeaderInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/*
* Network Module to provide the singleton Network class for make API clAss.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideHeaderInterceptor(): HeaderInterceptor = HeaderInterceptor()

    @Singleton
    @Provides
    fun provideClient(interceptor: HeaderInterceptor): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .connectTimeout(CONN_TIMEOUT.toLong(), TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient, gson: Gson, baseUrl: String): Retrofit =
        Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper

}