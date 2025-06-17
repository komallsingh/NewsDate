package com.komal.newsdate.network

import com.komal.newsdate.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface newsApiService {

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        //@Query("country") country: String = "us",
        @Query("category") category: String = "general", // general = world/news
        @Query("language") language: String = "en",
        @Query("apiKey") apiKey: String
    ): Response<NewsResponse>  // ✅ Use Response wrapper
}

//RETROFIT USES THE INTERFACE TO MAKE REAL NETWORK REQUESTS