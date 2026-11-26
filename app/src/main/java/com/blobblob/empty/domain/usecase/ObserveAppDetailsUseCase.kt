package com.blobblob.empty.domain.usecase

import com.blobblob.empty.appdetails.AppDetails
import com.blobblob.empty.domain.AppDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveAppDetailsUseCase @Inject constructor(
    private val repository: AppDetailsRepository
) {
    operator fun invoke(id: String): Flow<AppDetails> = repository.observeAppDetails(id)
}
