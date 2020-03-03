package com.assem.globofly.services

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    private const val BASE_URL = "http://10.0.2.2:9000/"
    // Create OkHttp Client
    private val okHttp: OkHttpClient.Builder = OkHttpClient.Builder()
    // Create Retrofit Builder
    private val builder: Retrofit.Builder = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())
    // Create Retrofit Instance
    private val retrofit: Retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }
}