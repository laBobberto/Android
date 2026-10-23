package com.blobblob.empty

sealed interface AppListEvent {
    data class ShowSnackbar(val message: String) : AppListEvent
}
