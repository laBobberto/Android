package com.blobblob.empty

import androidx.annotation.DrawableRes

data class AppItem(
    val id: String,
    val name: String,
    val description: String,
    val category: AppCategory,
    @DrawableRes val iconRes: Int
)
