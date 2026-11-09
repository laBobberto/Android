package com.blobblob.empty.data

import com.blobblob.empty.AppDataSource
import com.blobblob.empty.AppItem
import com.blobblob.empty.appdetails.AppDetails
import com.blobblob.empty.domain.AppsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppsRepositoryImpl @Inject constructor(
    private val dataSource: AppDataSource
) : AppsRepository {

    override fun getApps(): List<AppItem> = dataSource.getApps()

    override fun getShowcaseApps(): List<AppItem> = dataSource.getShowcaseApps()

    override fun getAppDetails(appId: String): AppDetails = dataSource.getAppDetails(appId)
}
