package com.blobblob.empty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blobblob.empty.domain.usecase.GetAppsUseCase
import com.blobblob.empty.domain.usecase.LoadRatingsInParallelUseCase
import com.blobblob.empty.domain.usecase.LoadRatingsSequentiallyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.system.measureTimeMillis

@HiltViewModel
class AppListViewModel @Inject constructor(
    private val getAppsUseCase: GetAppsUseCase,
    private val loadRatingsSequentiallyUseCase: LoadRatingsSequentiallyUseCase,
    private val loadRatingsInParallelUseCase: LoadRatingsInParallelUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<AppListState>(AppListState.Loading)
    val state = _state.asStateFlow()

    private val _events = Channel<AppListEvent>(BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        loadApps()
    }

    fun loadApps() {
        viewModelScope.launch {
            _state.value = AppListState.Loading
            runCatching {
                getAppsUseCase()
            }.onSuccess { apps ->
                _state.value = AppListState.Content(apps = apps)
            }.onFailure {
                _state.value = AppListState.Error(it.message)
            }
        }
    }

    fun loadRatings(parallel: Boolean) {
        viewModelScope.launch {
            val current = _state.value
            if (current !is AppListState.Content || current.isRatingsLoading) return@launch
            _state.value = current.copy(isRatingsLoading = true)
            val ids = current.apps.map { it.id }
            var ratings: Map<String, Float> = emptyMap()
            val time = measureTimeMillis {
                ratings = if (parallel) {
                    loadRatingsInParallelUseCase(ids)
                } else {
                    loadRatingsSequentiallyUseCase(ids)
                }
            }
            _state.update { state ->
                if (state is AppListState.Content) {
                    state.copy(
                        apps = state.apps.map { app ->
                            app.copy(rating = ratings[app.id] ?: app.rating)
                        },
                        isRatingsLoading = false
                    )
                } else state
            }
            val mode = if (parallel) "Параллельно" else "Последовательно"
            _events.send(AppListEvent.ShowSnackbar("$mode: $time мс"))
        }
    }

    fun onToolbarClick(message: String) {
        viewModelScope.launch {
            _events.send(AppListEvent.ShowSnackbar(message))
        }
    }
}
