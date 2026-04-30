package com.maju.lib.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.maju.lib.model.Book
import com.maju.lib.ui.theme.BackgroundLight
import com.maju.lib.ui.theme.PrimaryPurple
import com.maju.lib.ui.theme.White
import com.maju.lib.viewmodel.BookViewModel
import com.maju.lib.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookSearchScreen(
    onLogout: () -> Unit,
    viewModel: BookViewModel = viewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Minha Livraria", color = White, fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "Sair", tint = White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PrimaryPurple)
            )
        },
        containerColor = BackgroundLight
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Search Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Buscar livros...") },
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryPurple,
                        focusedContainerColor = White,
                        unfocusedContainerColor = White
                    ),
                    singleLine = true
                )
                
                Button(
                    onClick = { viewModel.searchBooks(searchQuery) },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple)
                ) {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "Buscar", tint = White)
                }
            }

            // Content
            Box(modifier = Modifier.fillMaxSize()) {
                when (uiState) {
                    is UiState.Empty -> {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Search,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = Color.Gray
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Busque por livros", color = Color.Gray, fontSize = 18.sp)
                        }
                    }
                    is UiState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = PrimaryPurple
                        )
                    }
                    is UiState.Error -> {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = (uiState as UiState.Error).message,
                                color = Color.Red,
                                modifier = Modifier.padding(16.dp)
                            )
                            Button(
                                onClick = { viewModel.searchBooks(searchQuery) },
                                colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple)
                            ) {
                                Text("Tentar novamente", color = White)
                            }
                        }
                    }
                    is UiState.Success -> {
                        val books = (uiState as UiState.Success).books
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(books) { book ->
                                BookCard(book)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BookCard(book: Book) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            val imageUrl = if (book.cover_i != null) {
                "https://covers.openlibrary.org/b/id/${book.cover_i}-M.jpg"
            } else {
                null
            }
            
            AsyncImage(
                model = imageUrl,
                contentDescription = "Capa do livro",
                modifier = Modifier
                    .width(100.dp)
                    .fillMaxHeight(),
                contentScale = ContentScale.Crop,
                fallback = null // Can provide a placeholder painter here if desired
            )
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = book.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = book.authors?.joinToString(", ") ?: "Autor desconhecido",
                        fontSize = 14.sp,
                        color = Color.DarkGray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (book.ratings_average != null) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "Rating",
                                tint = Color(0xFFFFC107),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = String.format("%.1f", book.ratings_average),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    } else {
                        Text(text = "Sem nota", fontSize = 12.sp, color = Color.Gray)
                    }
                    
                    if (book.first_publish_year != null) {
                        Text(
                            text = book.first_publish_year.toString(),
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}
