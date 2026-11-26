package com.blobblob.empty.domain.usecase

import com.blobblob.empty.domain.AppDetailsRepository
import javax.inject.Inject

class ToggleWishlistUseCase @Inject constructor(
    private val repository: AppDetailsRepository
) {
    suspend operator fun invoke(id: String) = repository.toggleWishlist(id)
}
