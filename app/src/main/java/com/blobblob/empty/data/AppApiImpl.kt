package com.blobblob.empty.data

import com.blobblob.empty.AppDataSource
import com.blobblob.empty.AppItem
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppApiImpl @Inject constructor(
    private val dataSource: AppDataSource
) : AppApi {

    override suspend fun getApps(): List<AppItem> = dataSource.getApps()

    override suspend fun getRating(id: String): Float {
        delay(1500)
        return (30..50).random() / 10f
    }
}
