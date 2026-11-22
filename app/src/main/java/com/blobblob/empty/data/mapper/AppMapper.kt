package com.blobblob.empty.data.mapper

import com.blobblob.empty.AppCategory
import com.blobblob.empty.AppItem
import com.blobblob.empty.data.dto.AppDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppMapper @Inject constructor() {

    fun toDomain(dto: AppDto): AppItem {
        val category = try {
            AppCategory.valueOf(dto.category)
        } catch (e: IllegalArgumentException) {
            AppCategory.APP
        }
        return AppItem(
            id = dto.id,
            name = dto.name,
            description = dto.description,
            category = category,
            iconRes = dto.iconRes,
            iconUrl = dto.iconUrl,
            rating = dto.rating
        )
    }

    fun toDto(domain: AppItem): AppDto {
        return AppDto(
            id = domain.id,
            name = domain.name,
            description = domain.description,
            category = domain.category.name,
            iconRes = domain.iconRes,
            iconUrl = domain.iconUrl,
            rating = domain.rating
        )
    }

    fun toDomainList(dtos: List<AppDto>): List<AppItem> = dtos.map { toDomain(it) }

    fun toDtoList(domains: List<AppItem>): List<AppDto> = domains.map { toDto(it) }
}
