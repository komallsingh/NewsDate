package com.komal.newsdate



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komal.newsdate.model.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() { //business logic and state holder

    private val repository = NewsRepository() //CONNECTS VIEW MODEL & REPOSITORY

    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>> = _articles  //MUTABLE STATE HOLDING ARTICLE

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _savedArticles = MutableStateFlow<List<Article>>(emptyList())
    val savedArticles: StateFlow<List<Article>> = _savedArticles

    fun loadNews() {
        viewModelScope.launch {
            _isLoading.value = true
            _articles.value = repository.getFakeNews()
            _isLoading.value = false
        }
    }

    fun searchNews(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _articles.value = repository.searchFakeNews(query)
            _isLoading.value = false
        }
    }

    fun saveArticle(article: Article) {
        _savedArticles.value = _savedArticles.value + article
    }
    fun toggleBookmark(title: String) {
        _articles.value = _articles.value.map { article ->
            if (article.title == title) {
                article.copy(isBookmarked = !article.isBookmarked)
            } else article
        }

        // Optional: Update saved articles list based on bookmarked articles
        _savedArticles.value = _articles.value.filter { it.isBookmarked }
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



