package com.maju.lib.network

import com.maju.lib.model.BookSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApiService {
    @GET("search.json")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("limit") limit: Int = 20,
        @Query("fields") fields: String = "title,author_name,cover_i,first_publish_year,ratings_average"
    ): BookSearchResponse
}
