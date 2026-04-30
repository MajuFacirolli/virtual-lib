package com.maju.lib.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maju.lib.model.Book
import com.maju.lib.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class UiState {
    object Empty : UiState()
    object Loading : UiState()
    data class Success(val books: List<Book>) : UiState()
    data class Error(val message: String) : UiState()
}

class BookViewModel : ViewModel() {
    private val repository = BookRepository()

    private val _uiState = MutableStateFlow<UiState>(UiState.Empty)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun searchBooks(query: String) {
        if (query.isBlank()) {
            _uiState.value = UiState.Empty
            return
        }

        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = repository.searchBooks(query)
            result.onSuccess { books ->
                if (books.isEmpty()) {
                    _uiState.value = UiState.Empty
                } else {
                    _uiState.value = UiState.Success(books)
                }
            }.onFailure { error ->
                _uiState.value = UiState.Error(error.localizedMessage ?: "Erro desconhecido")
            }
        }
    }
}
