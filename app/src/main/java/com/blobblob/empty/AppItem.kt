package com.blobblob.empty

data class AppItem(
    val id: String,
    val name: String,
    val description: String,
    val category: AppCategory,
    val iconRes: Int? = null,
    val iconUrl: String? = null
)
