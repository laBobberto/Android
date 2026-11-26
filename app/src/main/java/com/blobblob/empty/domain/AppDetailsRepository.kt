package com.blobblob.empty.domain

import com.blobblob.empty.appdetails.AppDetails
import kotlinx.coroutines.flow.Flow

interface AppDetailsRepository {
    fun observeAppDetails(id: String): Flow<AppDetails>
    suspend fun toggleWishlist(id: String)
    suspend fun loadAppDetails(id: String)
}
