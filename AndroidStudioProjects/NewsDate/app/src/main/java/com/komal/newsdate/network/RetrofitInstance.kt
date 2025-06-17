package com.komal.newsdate.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://newsapi.org/"

    val api: newsApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(newsApiService::class.java)
    }
}
//gives a retrofit object
//t sets the base URL, connects Moshi to convert JSON → Kotlin objects, and gives access to NewsApiService.
//You call RetrofitInstance.api.getTopHeadlines() to hit the API.