package com.maju.lib.model

import com.google.gson.annotations.SerializedName

data class Book(
    val title: String,
    @SerializedName("author_name") val authors: List<String>?,
    val cover_i: Long?,
    val first_publish_year: Int?,
    val ratings_average: Double?
)
