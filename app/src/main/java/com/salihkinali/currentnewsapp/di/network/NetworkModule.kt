package com.salihkinali.currentnewsapp.di.network

import com.salihkinali.currentnewsapp.data.service.ApiService
import com.salihkinali.currentnewsapp.util.BASE_URL
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

        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun getCurrentNewsApi(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

}