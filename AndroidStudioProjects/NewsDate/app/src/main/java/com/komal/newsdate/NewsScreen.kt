package com.komal.newsdate.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.komal.newsdate.NewsViewModel
import com.komal.newsdate.model.Article
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun NewsDateApp(viewModel: NewsViewModel = viewModel()) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            NewsScreen(
                viewModel = viewModel,
                onArticleClick = { /* TODO: Add article details */ },
                onSaveClick = { viewModel.saveArticle(it) },
                onSavedClick = { navController.navigate("saved") }
            )
        }
        composable("saved") {
            SavedNewsScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    viewModel: NewsViewModel,
    onArticleClick: (Article) -> Unit,
    onSaveClick: (Article) -> Unit,
    onSavedClick: () -> Unit
) {
    val articles by viewModel.articles.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.loadNews()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("The Daily Buzz", fontWeight = FontWeight.Bold) },
                actions = {
                    val isDark = isSystemInDarkTheme()
                    val savedTextColor = if (isDark) Color.Yellow else Color.DarkGray

                    TextButton(onClick = onSavedClick) {
                        Text("Saved", color = savedTextColor)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(8.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    viewModel.searchNews(it)
                },
                label = { Text("Search News") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(articles) { article ->
                        ArticleItem(
                            article = article,
                            onBookmarkClick = { viewModel.toggleBookmark(it.title) }
                        )
                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedNewsScreen(viewModel: NewsViewModel, onBackClick: () -> Unit) {
    val savedArticles by viewModel.savedArticles.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Saved Articles", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).padding(8.dp)) {
            items(savedArticles) { article ->
                ArticleItem(
                    article = article,
                    onBookmarkClick = { viewModel.toggleBookmark(it.title) }
                )
            }

        }
    }
}

@Composable
fun ArticleItem(article: Article, onBookmarkClick: (Article) -> Unit) {
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(article.title, style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(article.description, style = MaterialTheme.typography.bodyMedium)
                }
                IconButton(onClick = { onBookmarkClick(article) }) {
                    Icon(
                        imageVector = if (article.isBookmarked)
                            Icons.Filled.Bookmark
                        else
                            Icons.Outlined.BookmarkBorder,
                        contentDescription = null,
                        tint = if (article.isBookmarked) MaterialTheme.colorScheme.primary else Color.Gray
                    )
                }
            }
        }
    }
}

