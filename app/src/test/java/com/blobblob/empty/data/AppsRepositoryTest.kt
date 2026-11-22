package com.blobblob.empty.data

import com.blobblob.empty.AppCategory
import com.blobblob.empty.AppDataSource
import com.blobblob.empty.data.dto.AppDto
import com.blobblob.empty.data.mapper.AppDetailsMapper
import com.blobblob.empty.data.mapper.AppMapper
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AppsRepositoryTest {

    private class FakeAppApi(
        private val ratingsMap: Map<String, Float> = emptyMap(),
        private val defaultRating: Float = 4.0f
    ) : AppApi {
        val requestedIds = mutableListOf<String>()

        override suspend fun getApps(): List<AppDto> = emptyList()

        override suspend fun getRating(id: String): Float {
            requestedIds.add(id)
            return ratingsMap[id] ?: defaultRating
        }
    }

    private lateinit var dataSource: AppDataSource
    private lateinit var appMapper: AppMapper
    private lateinit var appDetailsMapper: AppDetailsMapper
    private lateinit var fakeApi: FakeAppApi
    private lateinit var repository: AppsRepositoryImpl

    @Before
    fun setUp() {
        dataSource = AppDataSource()
        appMapper = AppMapper()
        appDetailsMapper = AppDetailsMapper()
        fakeApi = FakeAppApi(
            ratingsMap = mapOf(
                "1" to 4.9f,
                "2" to 3.8f,
                "3" to 4.2f
            )
        )
        repository = AppsRepositoryImpl(dataSource, fakeApi, appMapper, appDetailsMapper)
    }

    @Test
    fun getApps_returnsMappedAppsFromDataSource() {
        val apps = repository.getApps()

        assertEquals(10, apps.size)
        assertEquals("1", apps[0].id)
        assertEquals("ВКонтакте", apps[0].name)
        assertEquals(AppCategory.SOCIAL, apps[0].category)
    }

    @Test
    fun getShowcaseApps_returnsShowcaseAppsFromDataSource() {
        val showcaseApps = repository.getShowcaseApps()

        assertEquals(9, showcaseApps.size)
        assertEquals("sc-1", showcaseApps[0].id)
        assertEquals("VK Клипы", showcaseApps[0].name)
        assertEquals(AppCategory.ENTERTAINMENT, showcaseApps[0].category)
    }

    @Test
    fun getAppDetails_returnsCorrectAppDetails() {
        val details = repository.getAppDetails("custom-id-42")

        assertEquals("custom-id-42", details.id)
        assertEquals("Гильдия Героев: Экшен ММО РПГ", details.name)
        assertEquals("VK Play", details.developer)
        assertEquals(AppCategory.GAME, details.category)
        assertEquals(12, details.ageRating)
        assertEquals(223.7f, details.size, 0.001f)
    }

    @Test
    fun loadRatingsSequentially_returnsRatingsAndInvokesApiForEachId() = runBlocking {
        val ids = listOf("1", "2", "3")
        val ratings = repository.loadRatingsSequentially(ids)

        assertEquals(3, ratings.size)
        assertEquals(4.9f, ratings["1"])
        assertEquals(3.8f, ratings["2"])
        assertEquals(4.2f, ratings["3"])
        assertEquals(ids, fakeApi.requestedIds)
    }

    @Test
    fun loadRatingsInParallel_returnsRatingsForAllIds() = runBlocking {
        val ids = listOf("1", "2", "3")
        val ratings = repository.loadRatingsInParallel(ids)

        assertEquals(3, ratings.size)
        assertEquals(4.9f, ratings["1"])
        assertEquals(3.8f, ratings["2"])
        assertEquals(4.2f, ratings["3"])
        assertEquals(3, fakeApi.requestedIds.size)
        assertTrue(fakeApi.requestedIds.containsAll(ids))
    }

    @Test
    fun loadRatingsSequentially_withEmptyList_returnsEmptyMapAndDoesNotCallApi() = runBlocking {
        val ratings = repository.loadRatingsSequentially(emptyList())

        assertTrue(ratings.isEmpty())
        assertTrue(fakeApi.requestedIds.isEmpty())
    }

    @Test
    fun loadRatingsInParallel_withEmptyList_returnsEmptyMapAndDoesNotCallApi() = runBlocking {
        val ratings = repository.loadRatingsInParallel(emptyList())

        assertTrue(ratings.isEmpty())
        assertTrue(fakeApi.requestedIds.isEmpty())
    }

    @Test
    fun loadRatingsSequentially_withUnknownIds_usesDefaultRating() = runBlocking {
        val ids = listOf("unknown-99")
        val ratings = repository.loadRatingsSequentially(ids)

        assertEquals(1, ratings.size)
        assertEquals(4.0f, ratings["unknown-99"])
        assertEquals(listOf("unknown-99"), fakeApi.requestedIds)
    }
}
