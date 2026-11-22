package com.blobblob.empty.domain.usecase

import com.blobblob.empty.AppCategory
import com.blobblob.empty.AppItem
import com.blobblob.empty.appdetails.AppDetails
import com.blobblob.empty.domain.AppsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AppsUseCasesTest {

    private class FakeAppsRepository : AppsRepository {
        private val testApps = listOf(
            AppItem("1", "App One", "Desc 1", AppCategory.SOCIAL),
            AppItem("2", "App Two", "Desc 2", AppCategory.GAME)
        )

        private val testShowcaseApps = listOf(
            AppItem("s1", "Showcase One", "Showcase Desc", AppCategory.ENTERTAINMENT)
        )

        private val detailsMap = mapOf(
            "1" to AppDetails(
                id = "1",
                name = "App One",
                developer = "Developer 1",
                category = AppCategory.SOCIAL,
                ageRating = 12,
                size = 25.0f,
                iconUrl = "https://example.com/icon1.png",
                screenshotUrlList = emptyList(),
                description = "Full description"
            )
        )

        override fun getApps(): List<AppItem> = testApps

        override fun getShowcaseApps(): List<AppItem> = testShowcaseApps

        override fun getAppDetails(appId: String): AppDetails {
            return detailsMap[appId] ?: throw NoSuchElementException("App not found: $appId")
        }

        override suspend fun loadRatingsSequentially(ids: List<String>): Map<String, Float> {
            return ids.associateWith { 4.5f }
        }

        override suspend fun loadRatingsInParallel(ids: List<String>): Map<String, Float> {
            return ids.associateWith { 4.8f }
        }
    }

    private lateinit var fakeRepository: FakeAppsRepository
    private lateinit var getAppsUseCase: GetAppsUseCase
    private lateinit var getShowcaseAppsUseCase: GetShowcaseAppsUseCase
    private lateinit var getAppDetailsUseCase: GetAppDetailsUseCase
    private lateinit var loadRatingsSequentiallyUseCase: LoadRatingsSequentiallyUseCase
    private lateinit var loadRatingsInParallelUseCase: LoadRatingsInParallelUseCase

    @Before
    fun setUp() {
        fakeRepository = FakeAppsRepository()
        getAppsUseCase = GetAppsUseCase(fakeRepository)
        getShowcaseAppsUseCase = GetShowcaseAppsUseCase(fakeRepository)
        getAppDetailsUseCase = GetAppDetailsUseCase(fakeRepository)
        loadRatingsSequentiallyUseCase = LoadRatingsSequentiallyUseCase(fakeRepository)
        loadRatingsInParallelUseCase = LoadRatingsInParallelUseCase(fakeRepository)
    }

    @Test
    fun getAppsUseCase_returnsAppsFromRepository() {
        val result = getAppsUseCase()
        assertEquals(2, result.size)
        assertEquals("App One", result[0].name)
        assertEquals("App Two", result[1].name)
    }

    @Test
    fun getShowcaseAppsUseCase_returnsShowcaseAppsFromRepository() {
        val result = getShowcaseAppsUseCase()
        assertEquals(1, result.size)
        assertEquals("Showcase One", result[0].name)
    }

    @Test
    fun getAppDetailsUseCase_returnsCorrectAppDetails() {
        val result = getAppDetailsUseCase("1")
        assertEquals("1", result.id)
        assertEquals("App One", result.name)
        assertEquals("Developer 1", result.developer)
        assertEquals(25.0f, result.size, 0.001f)
    }

    @Test(expected = NoSuchElementException::class)
    fun getAppDetailsUseCase_throwsException_whenAppNotFound() {
        getAppDetailsUseCase("unknown-id")
    }

    @Test
    fun loadRatingsSequentiallyUseCase_callsRepository_returnsRatings() = runBlocking {
        val ids = listOf("1", "2")
        val ratings = loadRatingsSequentiallyUseCase(ids)
        assertEquals(2, ratings.size)
        assertEquals(4.5f, ratings["1"])
        assertEquals(4.5f, ratings["2"])
    }

    @Test
    fun loadRatingsInParallelUseCase_callsRepository_returnsRatings() = runBlocking {
        val ids = listOf("1", "2", "3")
        val ratings = loadRatingsInParallelUseCase(ids)
        assertEquals(3, ratings.size)
        assertEquals(4.8f, ratings["1"])
        assertEquals(4.8f, ratings["2"])
        assertEquals(4.8f, ratings["3"])
    }

    @Test
    fun loadRatingsSequentiallyUseCase_handlesEmptyList() = runBlocking {
        val ratings = loadRatingsSequentiallyUseCase(emptyList())
        assertTrue(ratings.isEmpty())
    }
}
