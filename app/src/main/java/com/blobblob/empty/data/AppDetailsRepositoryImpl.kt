package com.blobblob.empty.data

import com.blobblob.empty.AppDataSource
import com.blobblob.empty.appdetails.AppDetails
import com.blobblob.empty.data.db.AppDetailsDao
import com.blobblob.empty.data.mapper.AppDetailsEntityMapper
import com.blobblob.empty.domain.AppDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDetailsRepositoryImpl @Inject constructor(
    private val appDetailsDao: AppDetailsDao,
    private val entityMapper: AppDetailsEntityMapper,
    private val dataSource: AppDataSource
) : AppDetailsRepository {

    override fun observeAppDetails(id: String): Flow<AppDetails> {
        return appDetailsDao.getAppDetails(id)
            .filterNotNull()
            .map { entityMapper.toDomain(it) }
    }

    override suspend fun toggleWishlist(id: String) {
        val currentEntity = appDetailsDao.getAppDetails(id).first()
        currentEntity?.let {
            appDetailsDao.updateWishlistStatus(id, !it.isInWishlist)
        }
    }

    override suspend fun loadAppDetails(id: String) {
        val currentEntity = appDetailsDao.getAppDetails(id).first()
        val domain = dataSource.getAppDetails(id)
        val entity = entityMapper.toEntity(domain).copy(
            isInWishlist = currentEntity?.isInWishlist ?: false
        )
        appDetailsDao.insertAppDetails(entity)
    }
}
