package com.komal.newsdate.model //all files in model folder

//class to store news article displayed data
    data class Article(
        val title: String,
        val description: String,
        val urlToImage: String? = null,
        val isBookmarked: Boolean = false
    )


//Need
//Represents one news item.

//Helps in passing data between Repository → ViewModel → UI.

//Retrofit response will map JSON to this model.

//Room Database will save this model.

//Compose UI will display this model.

