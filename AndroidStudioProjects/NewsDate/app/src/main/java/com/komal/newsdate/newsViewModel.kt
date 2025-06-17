package com.komal.newsdate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komal.newsdate.model.Article
import com.komal.newsdate.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch//for running coroutines

class NewsViewModel : ViewModel() {

    private val repository = NewsRepository()

    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>> = _articles
    //_articles is private mutable state
    //articles is public read-only version to be observed by the UI
//We assign _articles to articles because:
//
//MutableStateFlow is-a StateFlow
//
//So Kotlin allows this upcasting:
//
//Mutable → Immutable
//
//Like saying: “Here’s a read-only version of _articles”

    private val _savedArticles = MutableStateFlow<List<Article>>(emptyList())
    val savedArticles: StateFlow<List<Article>> = _savedArticles

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    // ✅ Fetches articles from the API
    fun loadNews(apiKey: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val fetchedArticles = repository.getTopHeadlines(apiKey)
            println("DEBUG: Articles fetched: ${fetchedArticles.size}")
            _articles.value = fetchedArticles
            _isLoading.value = false
        }
    }

    // ✅ Filters articles by search text
    fun searchArticles(query: String) {
        _searchQuery.value = query
        val currentList = _articles.value
        _articles.value = if (query.isBlank()) {
            currentList
        } else {
            currentList.filter {
                it.title.contains(query, ignoreCase = true) ||
                        (it.description?.contains(query, ignoreCase = true) == true)
            }
        }
    }

    // ✅ Toggle bookmark on/off
    fun toggleBookmark(article: Article) {
        val updatedList = _articles.value.map {
            if (it == article) it.copy(isBookmarked = !it.isBookmarked) else it
        }
        _articles.value = updatedList

        _savedArticles.value = updatedList.filter { it.isBookmarked }
    }

    // ✅ For restoring original articles after search cleared
    fun restoreOriginalArticles(articlesFromApi: List<Article>) {
        _articles.value = articlesFromApi
    }
    fun saveArticle(article: Article) {
        val current = _savedArticles.value.toMutableList()
        if (!current.contains(article)) {
            current.add(article)
            _savedArticles.value = current
        }
    }
}


//Holds state/data for UI.
//
//Uses StateFlow for reactive UI updates.
//
//Uses coroutines for background tasks.
//
//🔮 Future References
//Will connect with Retrofit or Room here.
//
//Can add loading/error states later for better UX.



