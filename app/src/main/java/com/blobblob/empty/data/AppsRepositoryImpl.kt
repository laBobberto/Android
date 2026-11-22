package com.blobblob.empty.data

import com.blobblob.empty.AppDataSource
import com.blobblob.empty.AppItem
import com.blobblob.empty.appdetails.AppDetails
import com.blobblob.empty.domain.AppsRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppsRepositoryImpl @Inject constructor(
    private val dataSource: AppDataSource,
    private val api: AppApi
) : AppsRepository {

    override fun getApps(): List<AppItem> = dataSource.getApps()

    override fun getShowcaseApps(): List<AppItem> = dataSource.getShowcaseApps()

    override fun getAppDetails(appId: String): AppDetails = dataSource.getAppDetails(appId)

    override suspend fun loadRatingsSequentially(ids: List<String>): Map<String, Float> {
        val ratings = mutableMapOf<String, Float>()
        for (id in ids) {
            ratings[id] = api.getRating(id)
        }
        return ratings
    }

    override suspend fun loadRatingsInParallel(ids: List<String>): Map<String, Float> = coroutineScope {
        ids.map { id ->
            async { id to api.getRating(id) }
        }.awaitAll().toMap()
    }
}
