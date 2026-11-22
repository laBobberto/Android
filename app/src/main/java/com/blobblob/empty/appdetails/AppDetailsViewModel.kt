package com.blobblob.empty.appdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blobblob.empty.domain.AppsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppDetailsViewModel @Inject constructor(
    private val repository: AppsRepository
) : ViewModel() {

    private val _state = MutableStateFlow<AppDetailsState>(AppDetailsState.Loading)
    val state = _state.asStateFlow()

    private var installJob: Job? = null

    fun loadApp(appId: String) {
        viewModelScope.launch {
            _state.value = AppDetailsState.Loading
            runCatching {
                val appDetails = repository.getAppDetails(appId)
                val showcaseApps = repository.getShowcaseApps()
                AppDetailsState.Content(
                    appDetails = appDetails,
                    showcaseApps = showcaseApps
                )
            }.onSuccess { content ->
                _state.value = content
            }.onFailure {
                _state.value = AppDetailsState.Error
            }
        }
    }

    fun onInstallClick() {
        val current = _state.value
        if (current !is AppDetailsState.Content) return

        if (current.installState is InstallState.Installing) {
            installJob?.cancel()
            _state.update { state ->
                if (state is AppDetailsState.Content) {
                    state.copy(installState = InstallState.NotInstalled)
                } else state
            }
        } else {
            installJob = viewModelScope.launch {
                emulateInstall()
            }
        }
    }

    private suspend fun emulateInstall() {
        try {
            for (progress in 0..100 step 5) {
                delay(150)
                _state.update { state ->
                    if (state is AppDetailsState.Content) {
                        state.copy(installState = InstallState.Installing(progress))
                    } else state
                }
            }
            _state.update { state ->
                if (state is AppDetailsState.Content) {
                    state.copy(installState = InstallState.Installed)
                } else state
            }
        } catch (e: CancellationException) {
            _state.update { state ->
                if (state is AppDetailsState.Content) {
                    state.copy(installState = InstallState.NotInstalled)
                } else state
            }
            throw e
        }
    }

    fun collapseDescription() {
        _state.update { state ->
            if (state is AppDetailsState.Content) {
                state.copy(descriptionCollapsed = true)
            } else state
        }
    }
}
