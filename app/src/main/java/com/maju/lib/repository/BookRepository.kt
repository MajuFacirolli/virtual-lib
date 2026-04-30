package com.maju.lib.repository

import com.maju.lib.model.Book
import com.maju.lib.network.RetrofitInstance

class BookRepository {
    suspend fun searchBooks(query: String): Result<List<Book>> {
        return try {
            val response = RetrofitInstance.api.searchBooks(query)
            Result.success(response.docs)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
