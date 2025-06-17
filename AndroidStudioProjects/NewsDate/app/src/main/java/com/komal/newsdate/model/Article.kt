package com.komal.newsdate.model //all files in model folder

//class to store news article displayed data


data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)

data class Article(
    val source: Source,
    val author: String?,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?,
    var isBookmarked: Boolean = false
)

data class Source(
    val id: String?,
    val name: String
)


//Need
//Represents one news item.

//Helps in passing data between Repository → ViewModel → UI.

//Retrofit response will map JSON to this model.

//Room Database will save this model.

//Compose UI will display this model.

