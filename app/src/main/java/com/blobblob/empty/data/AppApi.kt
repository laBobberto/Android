package com.blobblob.empty.data

import com.blobblob.empty.AppItem

interface AppApi {
    suspend fun getApps(): List<AppItem>
    suspend fun getRating(id: String): Float
}
