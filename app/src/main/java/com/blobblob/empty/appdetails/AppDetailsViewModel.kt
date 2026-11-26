package com.blobblob.empty.appdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blobblob.empty.AppItem
import com.blobblob.empty.domain.AppDetailsRepository
import com.blobblob.empty.domain.usecase.GetShowcaseAppsUseCase
import com.blobblob.empty.domain.usecase.ObserveAppDetailsUseCase
import com.blobblob.empty.domain.usecase.ToggleWishlistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppDetailsViewModel @Inject constructor(
    private val appDetailsRepository: AppDetailsRepository,
    private val observeAppDetailsUseCase: ObserveAppDetailsUseCase,
    private val toggleWishlistUseCase: ToggleWishlistUseCase,
    private val getShowcaseAppsUseCase: GetShowcaseAppsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<AppDetailsState>(AppDetailsState.Loading)
    val state = _state.asStateFlow()

    private var installJob: Job? = null
    private var observeJob: Job? = null
    private var currentAppId: String? = null
    private var showcaseAppsList: List<AppItem> = emptyList()

    fun loadApp(appId: String) {
        currentAppId = appId
        _state.value = AppDetailsState.Loading
        observeAppDetails(appId)
        viewModelScope.launch {
            runCatching {
                showcaseAppsList = getShowcaseAppsUseCase()
                appDetailsRepository.loadAppDetails(appId)
            }.onFailure {
                if (_state.value !is AppDetailsState.Content) {
                    _state.value = AppDetailsState.Error
                }
            }
        }
    }

    private fun observeAppDetails(appId: String) {
        observeJob?.cancel()
        observeJob = viewModelScope.launch {
            observeAppDetailsUseCase(appId)
                .catch { _state.value = AppDetailsState.Error }
                .collect { appDetails ->
                    val current = _state.value
                    val currentInstallState = if (current is AppDetailsState.Content) {
                        current.installState
                    } else {
                        InstallState.NotInstalled
                    }
                    val currentCollapsed = if (current is AppDetailsState.Content) {
                        current.descriptionCollapsed
                    } else {
                        false
                    }
                    _state.value = AppDetailsState.Content(
                        appDetails = appDetails,
                        showcaseApps = showcaseAppsList,
                        installState = currentInstallState,
                        descriptionCollapsed = currentCollapsed,
                        isInWishlist = appDetails.isInWishlist
                    )
                }
        }
    }

    fun toggleWishlist() {
        val appId = currentAppId ?: return
        viewModelScope.launch {
            toggleWishlistUseCase(appId)
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
