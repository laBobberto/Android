package com.blobblob.empty

import androidx.compose.runtime.Immutable

@Immutable
sealed interface AppListState {
    data object Loading : AppListState
    data class Content(
        val apps: List<AppItem>,
        val isRatingsLoading: Boolean = false
    ) : AppListState
    data class Error(
        val message: String? = null
    ) : AppListState
}
