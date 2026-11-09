package com.blobblob.empty.appdetails

import androidx.lifecycle.ViewModel
import com.blobblob.empty.AppItem
import com.blobblob.empty.domain.AppsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppDetailsViewModel @Inject constructor(
    private val repository: AppsRepository
) : ViewModel() {

    fun getAppDetails(appId: String): AppDetails = repository.getAppDetails(appId)

    fun getShowcaseApps(): List<AppItem> = repository.getShowcaseApps()
}
