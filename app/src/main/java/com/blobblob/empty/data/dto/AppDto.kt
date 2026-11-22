package com.blobblob.empty.data.dto

data class AppDto(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val iconRes: Int? = null,
    val iconUrl: String? = null,
    val rating: Float? = null
)
