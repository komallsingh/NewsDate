package com.komal.newsdate


import com.komal.newsdate.model.Article
import kotlinx.coroutines.delay

class NewsRepository {
//FOR ADDING COROUTINES
    suspend fun getFakeNews(): List<Article> {
        delay(1000)  // Simulate network delay
        return listOf(
            Article("Android 15 Launched!", "Check out the new features in Android 15"),
            Article("Jetpack Compose Rocks!", "Building UIs has never been easier"),
            Article("Room Database Simplified", "Local caching for offline access")
        )
    }

    suspend fun searchFakeNews(query: String): List<Article> {
        delay(500)
        return getFakeNews().filter { it.title.contains(query, ignoreCase = true) }
    }
}
//Repository Layer separates data sources from ViewModel/UI.

//Future References
//Will replace getFakeNews() with Retrofit API call.
//You can add caching with Room here later.