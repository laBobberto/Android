package com.blobblob.empty.data

import com.blobblob.empty.data.dto.AppDto

interface AppApi {
    suspend fun getApps(): List<AppDto>
    suspend fun getRating(id: String): Float
}
