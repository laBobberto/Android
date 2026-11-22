package com.blobblob.empty.data

import com.blobblob.empty.AppDataSource
import com.blobblob.empty.AppItem
import com.blobblob.empty.appdetails.AppDetails
import com.blobblob.empty.data.mapper.AppDetailsMapper
import com.blobblob.empty.data.mapper.AppMapper
import com.blobblob.empty.domain.AppsRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppsRepositoryImpl @Inject constructor(
    private val dataSource: AppDataSource,
    private val api: AppApi,
    private val appMapper: AppMapper,
    private val appDetailsMapper: AppDetailsMapper
) : AppsRepository {

    override fun getApps(): List<AppItem> {
        val dtos = appMapper.toDtoList(dataSource.getApps())
        return appMapper.toDomainList(dtos)
    }

    override fun getShowcaseApps(): List<AppItem> {
        val dtos = appMapper.toDtoList(dataSource.getShowcaseApps())
        return appMapper.toDomainList(dtos)
    }

    override fun getAppDetails(appId: String): AppDetails {
        val domain = dataSource.getAppDetails(appId)
        val dto = appDetailsMapper.toDto(domain)
        return appDetailsMapper.toDomain(dto)
    }

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
