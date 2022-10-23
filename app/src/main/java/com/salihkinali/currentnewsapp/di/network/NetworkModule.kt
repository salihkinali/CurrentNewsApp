package com.salihkinali.currentnewsapp.di.network

import com.salihkinali.currentnewsapp.BuildConfig
import com.salihkinali.currentnewsapp.data.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun getRetrofitClient(): Retrofit = Retrofit.Builder()

        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun getCurrentNewsApi(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

}