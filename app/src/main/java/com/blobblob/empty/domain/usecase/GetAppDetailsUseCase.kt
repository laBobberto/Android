package com.blobblob.empty.domain.usecase

import com.blobblob.empty.appdetails.AppDetails
import com.blobblob.empty.domain.AppsRepository
import javax.inject.Inject

class GetAppDetailsUseCase @Inject constructor(
    private val repository: AppsRepository
) {
    operator fun invoke(appId: String): AppDetails = repository.getAppDetails(appId)
}
