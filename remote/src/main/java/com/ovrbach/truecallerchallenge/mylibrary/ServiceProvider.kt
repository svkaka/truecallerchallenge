package com.ovrbach.truecallerchallenge.mylibrary

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit

class ServiceProvider  {
    companion object {
        val instance by lazy {
            ServiceProvider()
        }
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    val fetchService: FetchService =
        retrofit.create(FetchService::class.java)

}