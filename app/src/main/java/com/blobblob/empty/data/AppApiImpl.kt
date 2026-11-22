package com.blobblob.empty.data

import com.blobblob.empty.AppDataSource
import com.blobblob.empty.data.dto.AppDto
import com.blobblob.empty.data.mapper.AppMapper
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppApiImpl @Inject constructor(
    private val dataSource: AppDataSource,
    private val appMapper: AppMapper
) : AppApi {

    override suspend fun getApps(): List<AppDto> = appMapper.toDtoList(dataSource.getApps())

    override suspend fun getRating(id: String): Float {
        delay(1500)
        return (30..50).random() / 10f
    }
}
