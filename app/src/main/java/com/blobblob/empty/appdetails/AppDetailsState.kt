package com.blobblob.empty.appdetails

import androidx.compose.runtime.Immutable
import com.blobblob.empty.AppItem

sealed interface InstallState {
    data object NotInstalled : InstallState
    data class Installing(val progress: Int) : InstallState
    data object Installed : InstallState
}

@Immutable
sealed interface AppDetailsState {
    data object Loading : AppDetailsState
    data class Content(
        val appDetails: AppDetails,
        val showcaseApps: List<AppItem>,
        val installState: InstallState = InstallState.NotInstalled,
        val descriptionCollapsed: Boolean = false
    ) : AppDetailsState
    data object Error : AppDetailsState
}
