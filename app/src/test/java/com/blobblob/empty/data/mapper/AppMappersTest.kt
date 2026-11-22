package com.blobblob.empty.data.mapper

import com.blobblob.empty.AppCategory
import com.blobblob.empty.AppItem
import com.blobblob.empty.appdetails.AppDetails
import com.blobblob.empty.data.dto.AppDetailsDto
import com.blobblob.empty.data.dto.AppDto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class AppMappersTest {

    private lateinit var appMapper: AppMapper
    private lateinit var appDetailsMapper: AppDetailsMapper

    @Before
    fun setUp() {
        appMapper = AppMapper()
        appDetailsMapper = AppDetailsMapper()
    }

    @Test
    fun appMapper_toDomain_mapsAllFieldsCorrectly() {
        val dto = AppDto(
            id = "test-1",
            name = "Test App",
            description = "Test Description",
            category = "SOCIAL",
            iconRes = 100,
            iconUrl = "https://example.com/icon.png",
            rating = 4.7f
        )

        val domain = appMapper.toDomain(dto)

        assertEquals("test-1", domain.id)
        assertEquals("Test App", domain.name)
        assertEquals("Test Description", domain.description)
        assertEquals(AppCategory.SOCIAL, domain.category)
        assertEquals(100, domain.iconRes)
        assertEquals("https://example.com/icon.png", domain.iconUrl)
        assertEquals(4.7f, domain.rating)
    }

    @Test
    fun appMapper_toDto_mapsAllFieldsCorrectly() {
        val domain = AppItem(
            id = "test-2",
            name = "Game App",
            description = "Fun game description",
            category = AppCategory.GAME,
            iconRes = 200,
            iconUrl = "https://example.com/game.png",
            rating = 4.3f
        )

        val dto = appMapper.toDto(domain)

        assertEquals("test-2", dto.id)
        assertEquals("Game App", dto.name)
        assertEquals("Fun game description", dto.description)
        assertEquals("GAME", dto.category)
        assertEquals(200, dto.iconRes)
        assertEquals("https://example.com/game.png", dto.iconUrl)
        assertEquals(4.3f, dto.rating)
    }

    @Test
    fun appMapper_toDomain_handlesUnknownCategoryFallback() {
        val dto = AppDto(
            id = "test-3",
            name = "Unknown Category App",
            description = "Description",
            category = "NON_EXISTENT_CATEGORY",
            iconRes = null,
            iconUrl = null,
            rating = null
        )

        val domain = appMapper.toDomain(dto)

        assertEquals(AppCategory.APP, domain.category)
    }

    @Test
    fun appMapper_handlesNullOptionalFields() {
        val dto = AppDto(
            id = "test-4",
            name = "Minimal App",
            description = "Description",
            category = "TOOLS",
            iconRes = null,
            iconUrl = null,
            rating = null
        )

        val domain = appMapper.toDomain(dto)
        assertNull(domain.iconRes)
        assertNull(domain.iconUrl)
        assertNull(domain.rating)

        val mappedDto = appMapper.toDto(domain)
        assertNull(mappedDto.iconRes)
        assertNull(mappedDto.iconUrl)
        assertNull(mappedDto.rating)
    }

    @Test
    fun appMapper_listBidirectionalMapping_preservesElements() {
        val dtos = listOf(
            AppDto("1", "App 1", "Desc 1", "GAME"),
            AppDto("2", "App 2", "Desc 2", "SOCIAL"),
            AppDto("3", "App 3", "Desc 3", "PRODUCTIVITY")
        )

        val domainList = appMapper.toDomainList(dtos)
        assertEquals(3, domainList.size)
        assertEquals("App 1", domainList[0].name)
        assertEquals(AppCategory.GAME, domainList[0].category)

        val convertedBackDtos = appMapper.toDtoList(domainList)
        assertEquals(dtos, convertedBackDtos)
    }

    @Test
    fun appDetailsMapper_toDomain_mapsAllFieldsCorrectly() {
        val dto = AppDetailsDto(
            id = "details-1",
            name = "VK Video",
            developer = "VK LLC",
            category = "ENTERTAINMENT",
            ageRating = 12,
            size = 54.3f,
            iconUrl = "https://example.com/icon.png",
            screenshotUrlList = listOf("https://example.com/s1.png", "https://example.com/s2.png"),
            description = "Detailed video player app"
        )

        val domain = appDetailsMapper.toDomain(dto)

        assertEquals("details-1", domain.id)
        assertEquals("VK Video", domain.name)
        assertEquals("VK LLC", domain.developer)
        assertEquals(AppCategory.ENTERTAINMENT, domain.category)
        assertEquals(12, domain.ageRating)
        assertEquals(54.3f, domain.size, 0.001f)
        assertEquals("https://example.com/icon.png", domain.iconUrl)
        assertEquals(2, domain.screenshotUrlList.size)
        assertEquals("Detailed video player app", domain.description)
    }

    @Test
    fun appDetailsMapper_toDto_mapsAllFieldsCorrectly() {
        val domain = AppDetails(
            id = "details-2",
            name = "VK Messenger",
            developer = "VK",
            category = AppCategory.SOCIAL,
            ageRating = 6,
            size = 32.1f,
            iconUrl = "https://example.com/msg.png",
            screenshotUrlList = listOf("https://example.com/m1.png"),
            description = "Messaging app"
        )

        val dto = appDetailsMapper.toDto(domain)

        assertEquals("details-2", dto.id)
        assertEquals("VK Messenger", dto.name)
        assertEquals("VK", dto.developer)
        assertEquals("SOCIAL", dto.category)
        assertEquals(6, dto.ageRating)
        assertEquals(32.1f, dto.size, 0.001f)
        assertEquals("https://example.com/msg.png", dto.iconUrl)
        assertEquals(listOf("https://example.com/m1.png"), dto.screenshotUrlList)
        assertEquals("Messaging app", dto.description)
    }

    @Test
    fun appDetailsMapper_toDomain_handlesUnknownCategoryFallback() {
        val dto = AppDetailsDto(
            id = "details-3",
            name = "Fallback App",
            developer = "Dev",
            category = "UNKNOWN_VAL",
            ageRating = 0,
            size = 10f,
            iconUrl = "",
            screenshotUrlList = emptyList(),
            description = ""
        )

        val domain = appDetailsMapper.toDomain(dto)
        assertEquals(AppCategory.APP, domain.category)
    }
}
