package com.blobblob.empty.data.mapper

import com.blobblob.empty.AppCategory
import com.blobblob.empty.appdetails.AppDetails
import com.blobblob.empty.data.dto.AppDetailsDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDetailsMapper @Inject constructor() {

    fun toDomain(dto: AppDetailsDto): AppDetails {
        val category = try {
            AppCategory.valueOf(dto.category)
        } catch (e: IllegalArgumentException) {
            AppCategory.APP
        }
        return AppDetails(
            id = dto.id,
            name = dto.name,
            developer = dto.developer,
            category = category,
            ageRating = dto.ageRating,
            size = dto.size,
            iconUrl = dto.iconUrl,
            screenshotUrlList = dto.screenshotUrlList,
            description = dto.description
        )
    }

    fun toDto(domain: AppDetails): AppDetailsDto {
        return AppDetailsDto(
            id = domain.id,
            name = domain.name,
            developer = domain.developer,
            category = domain.category.name,
            ageRating = domain.ageRating,
            size = domain.size,
            iconUrl = domain.iconUrl,
            screenshotUrlList = domain.screenshotUrlList,
            description = domain.description
        )
    }
}
