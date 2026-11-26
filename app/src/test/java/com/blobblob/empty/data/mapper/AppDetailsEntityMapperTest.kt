package com.blobblob.empty.data.mapper

import com.blobblob.empty.AppCategory
import com.blobblob.empty.appdetails.AppDetails
import com.blobblob.empty.data.entity.AppDetailsEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AppDetailsEntityMapperTest {

    private lateinit var mapper: AppDetailsEntityMapper

    @Before
    fun setUp() {
        mapper = AppDetailsEntityMapper()
    }

    @Test
    fun toDomain_mapsAllFieldsCorrectly_withWishlistTrue() {
        val entity = AppDetailsEntity(
            id = "app-1",
            name = "Test App",
            developer = "Developer",
            category = "GAME",
            ageRating = 16,
            size = 120.5f,
            iconUrl = "https://example.com/icon.png",
            screenshotUrls = "https://example.com/s1.png||https://example.com/s2.png",
            description = "Description",
            isInWishlist = true
        )

        val domain = mapper.toDomain(entity)

        assertEquals("app-1", domain.id)
        assertEquals("Test App", domain.name)
        assertEquals("Developer", domain.developer)
        assertEquals(AppCategory.GAME, domain.category)
        assertEquals(16, domain.ageRating)
        assertEquals(120.5f, domain.size, 0.001f)
        assertEquals("https://example.com/icon.png", domain.iconUrl)
        assertEquals(listOf("https://example.com/s1.png", "https://example.com/s2.png"), domain.screenshotUrlList)
        assertEquals("Description", domain.description)
        assertTrue(domain.isInWishlist)
    }

    @Test
    fun toEntity_mapsAllFieldsCorrectly_withWishlistFalse() {
        val domain = AppDetails(
            id = "app-2",
            name = "Social App",
            developer = "Team",
            category = AppCategory.SOCIAL,
            ageRating = 12,
            size = 45.0f,
            iconUrl = "https://example.com/icon2.png",
            screenshotUrlList = listOf("https://example.com/shot.png"),
            description = "Social desc",
            isInWishlist = false
        )

        val entity = mapper.toEntity(domain)

        assertEquals("app-2", entity.id)
        assertEquals("Social App", entity.name)
        assertEquals("Team", entity.developer)
        assertEquals("SOCIAL", entity.category)
        assertEquals(12, entity.ageRating)
        assertEquals(45.0f, entity.size, 0.001f)
        assertEquals("https://example.com/icon2.png", entity.iconUrl)
        assertEquals("https://example.com/shot.png", entity.screenshotUrls)
        assertEquals("Social desc", entity.description)
        assertFalse(entity.isInWishlist)
    }

    @Test
    fun toDomain_handlesUnknownCategory_fallbackToApp() {
        val entity = AppDetailsEntity(
            id = "app-3",
            name = "Unknown Cat",
            developer = "Dev",
            category = "INVALID_CATEGORY",
            ageRating = 0,
            size = 10f,
            iconUrl = "",
            screenshotUrls = "",
            description = "",
            isInWishlist = false
        )

        val domain = mapper.toDomain(entity)

        assertEquals(AppCategory.APP, domain.category)
        assertTrue(domain.screenshotUrlList.isEmpty())
    }

    @Test
    fun bidirectional_preservesWishlistStatus() {
        val original = AppDetails(
            id = "app-4",
            name = "Roundtrip App",
            developer = "Dev",
            category = AppCategory.TOOLS,
            ageRating = 3,
            size = 15.0f,
            iconUrl = "https://example.com/icon.png",
            screenshotUrlList = listOf("https://example.com/s1.png"),
            description = "Roundtrip",
            isInWishlist = true
        )

        val entity = mapper.toEntity(original)
        val domain = mapper.toDomain(entity)

        assertEquals(original, domain)
    }
}
