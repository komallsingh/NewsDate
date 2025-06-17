package com.komal.newsdate.repository

import com.komal.newsdate.model.Article
import com.komal.newsdate.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.util.Log

class NewsRepository {

    suspend fun getTopHeadlines(apiKey: String): List<Article> {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.getTopHeadlines(apiKey = apiKey)
                if (response.isSuccessful) {
                    response.body()?.articles ?: emptyList()
                } else {
                    Log.e("NewsRepository", "API Error: ${response.code()} - ${response.message()}")
                    emptyList()
                }
            } catch (e: Exception) {
                Log.e("NewsRepository", "Exception: ${e.localizedMessage}")
                emptyList()
            }
        }
    }
}


//Repository Layer separates data sources from ViewModel/UI.

//Future References
//Will replace getFakeNews() with Retrofit API call.
//You can add caching with Room here later.