package com.blobblob.empty.domain.usecase

import com.blobblob.empty.domain.AppsRepository
import javax.inject.Inject

class LoadRatingsInParallelUseCase @Inject constructor(
    private val repository: AppsRepository
) {
    suspend operator fun invoke(ids: List<String>): Map<String, Float> =
        repository.loadRatingsInParallel(ids)
}
