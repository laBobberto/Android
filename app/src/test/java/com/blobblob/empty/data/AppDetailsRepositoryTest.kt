package com.blobblob.empty.data

import com.blobblob.empty.AppCategory
import com.blobblob.empty.AppDataSource
import com.blobblob.empty.appdetails.AppDetails
import com.blobblob.empty.data.db.AppDetailsDao
import com.blobblob.empty.data.entity.AppDetailsEntity
import com.blobblob.empty.data.mapper.AppDetailsEntityMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AppDetailsRepositoryTest {

    private class FakeAppDetailsDao : AppDetailsDao {
        private val db = mutableMapOf<String, AppDetailsEntity>()
        private val flows = mutableMapOf<String, MutableStateFlow<AppDetailsEntity?>>()

        private fun getOrCreateFlow(id: String): MutableStateFlow<AppDetailsEntity?> {
            return flows.getOrPut(id) { MutableStateFlow(db[id]) }
        }

        override fun getAppDetails(id: String): Flow<AppDetailsEntity?> {
            return getOrCreateFlow(id)
        }

        override suspend fun insertAppDetails(appDetails: AppDetailsEntity) {
            db[appDetails.id] = appDetails
            getOrCreateFlow(appDetails.id).value = appDetails
        }

        override suspend fun updateWishlistStatus(id: String, isInWishlist: Boolean) {
            val existing = db[id] ?: return
            val updated = existing.copy(isInWishlist = isInWishlist)
            db[id] = updated
            getOrCreateFlow(id).value = updated
        }
    }

    private lateinit var fakeDao: FakeAppDetailsDao
    private lateinit var entityMapper: AppDetailsEntityMapper
    private lateinit var dataSource: AppDataSource
    private lateinit var repository: AppDetailsRepositoryImpl

    @Before
    fun setUp() {
        fakeDao = FakeAppDetailsDao()
        entityMapper = AppDetailsEntityMapper()
        dataSource = AppDataSource()
        repository = AppDetailsRepositoryImpl(fakeDao, entityMapper, dataSource)
    }

    @Test
    fun loadAppDetails_insertsIntoDao_andObserveEmits() = runBlocking {
        repository.loadAppDetails("test-1")

        val result = repository.observeAppDetails("test-1").first()

        assertEquals("test-1", result.id)
        assertFalse(result.isInWishlist)
    }

    @Test
    fun toggleWishlist_invertsWishlistStatusInDb_andEmitsUpdatedDomain() = runBlocking {
        repository.loadAppDetails("test-2")

        val before = repository.observeAppDetails("test-2").first()
        assertFalse(before.isInWishlist)

        repository.toggleWishlist("test-2")

        val after = repository.observeAppDetails("test-2").first()
        assertTrue(after.isInWishlist)

        repository.toggleWishlist("test-2")

        val reverted = repository.observeAppDetails("test-2").first()
        assertFalse(reverted.isInWishlist)
    }

    @Test
    fun loadAppDetails_preservesWishlistStatus_ifAlreadyInDb() = runBlocking {
        repository.loadAppDetails("test-3")
        repository.toggleWishlist("test-3")

        val inWishlist = repository.observeAppDetails("test-3").first()
        assertTrue(inWishlist.isInWishlist)

        repository.loadAppDetails("test-3")

        val stillInWishlist = repository.observeAppDetails("test-3").first()
        assertTrue(stillInWishlist.isInWishlist)
    }
}
