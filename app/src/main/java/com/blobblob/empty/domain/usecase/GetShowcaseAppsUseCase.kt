package com.blobblob.empty.domain.usecase

import com.blobblob.empty.AppItem
import com.blobblob.empty.domain.AppsRepository
import javax.inject.Inject

class GetShowcaseAppsUseCase @Inject constructor(
    private val repository: AppsRepository
) {
    operator fun invoke(): List<AppItem> = repository.getShowcaseApps()
}
