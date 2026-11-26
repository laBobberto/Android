package com.blobblob.empty.domain

import com.blobblob.empty.AppItem
import com.blobblob.empty.appdetails.AppDetails

interface AppsRepository {
    fun getApps(): List<AppItem>
    fun getShowcaseApps(): List<AppItem>
    fun getAppDetails(appId: String): AppDetails
    suspend fun loadRatingsSequentially(ids: List<String>): Map<String, Float>
    suspend fun loadRatingsInParallel(ids: List<String>): Map<String, Float>
}
