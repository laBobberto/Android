package com.blobblob.empty.domain.usecase

import com.blobblob.empty.AppCategory
import com.blobblob.empty.appdetails.AppDetails
import com.blobblob.empty.domain.AppDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class WishlistUseCasesTest {

    private class FakeAppDetailsRepository : AppDetailsRepository {
        var toggledId: String? = null
        val flow = MutableStateFlow(
            AppDetails(
                id = "app-1",
                name = "App 1",
                developer = "Dev 1",
                category = AppCategory.APP,
                ageRating = 3,
                size = 10f,
                iconUrl = "https://example.com/icon.png",
                screenshotUrlList = emptyList(),
                description = "Desc",
                isInWishlist = false
            )
        )

        override fun observeAppDetails(id: String): Flow<AppDetails> = flow

        override suspend fun toggleWishlist(id: String) {
            toggledId = id
            val current = flow.value
            flow.value = current.copy(isInWishlist = !current.isInWishlist)
        }

        override suspend fun loadAppDetails(id: String) {}
    }

    private lateinit var fakeRepo: FakeAppDetailsRepository
    private lateinit var observeUseCase: ObserveAppDetailsUseCase
    private lateinit var toggleUseCase: ToggleWishlistUseCase

    @Before
    fun setUp() {
        fakeRepo = FakeAppDetailsRepository()
        observeUseCase = ObserveAppDetailsUseCase(fakeRepo)
        toggleUseCase = ToggleWishlistUseCase(fakeRepo)
    }

    @Test
    fun observeAppDetailsUseCase_returnsFlow_fromRepository() = runBlocking {
        val app = observeUseCase("app-1").first()

        assertEquals("app-1", app.id)
        assertFalse(app.isInWishlist)
    }

    @Test
    fun toggleWishlistUseCase_callsRepository_andUpdatesFlow() = runBlocking {
        toggleUseCase("app-1")

        assertEquals("app-1", fakeRepo.toggledId)
        val updated = observeUseCase("app-1").first()
        assertTrue(updated.isInWishlist)
    }
}
