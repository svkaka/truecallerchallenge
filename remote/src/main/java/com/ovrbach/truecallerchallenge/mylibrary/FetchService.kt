package com.ovrbach.truecallerchallenge.mylibrary

import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

const val BASE_URL = "https://truecaller.blog/2018/01/22/life-as-an-android-engineer/"

interface FetchService {

    @GET
    fun grab10thChar(@Url url: String = BASE_URL): Deferred<ResponseBody>

    @GET
    fun grabEvery10Char(@Url url: String = BASE_URL): Deferred<ResponseBody>

    @GET
    fun wordCountRequest(@Url url: String = BASE_URL): Deferred<ResponseBody>

}