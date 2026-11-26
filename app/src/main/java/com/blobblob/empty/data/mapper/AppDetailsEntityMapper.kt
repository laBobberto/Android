package com.blobblob.empty.data.mapper

import com.blobblob.empty.AppCategory
import com.blobblob.empty.appdetails.AppDetails
import com.blobblob.empty.data.entity.AppDetailsEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDetailsEntityMapper @Inject constructor() {

    fun toDomain(entity: AppDetailsEntity): AppDetails {
        val category = try {
            AppCategory.valueOf(entity.category)
        } catch (e: IllegalArgumentException) {
            AppCategory.APP
        }
        val screenshots = if (entity.screenshotUrls.isEmpty()) {
            emptyList()
        } else {
            entity.screenshotUrls.split("||")
        }
        return AppDetails(
            id = entity.id,
            name = entity.name,
            developer = entity.developer,
            category = category,
            ageRating = entity.ageRating,
            size = entity.size,
            iconUrl = entity.iconUrl,
            screenshotUrlList = screenshots,
            description = entity.description,
            isInWishlist = entity.isInWishlist
        )
    }

    fun toEntity(domain: AppDetails): AppDetailsEntity {
        return AppDetailsEntity(
            id = domain.id,
            name = domain.name,
            developer = domain.developer,
            category = domain.category.name,
            ageRating = domain.ageRating,
            size = domain.size,
            iconUrl = domain.iconUrl,
            screenshotUrls = domain.screenshotUrlList.joinToString("||"),
            description = domain.description,
            isInWishlist = domain.isInWishlist
        )
    }
}
