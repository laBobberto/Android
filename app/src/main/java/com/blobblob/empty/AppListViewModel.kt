package com.blobblob.empty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AppListViewModel(
    private val dataSource: AppDataSource = AppDataSource
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
                dataSource.getApps()
            }.onSuccess { apps ->
                _state.value = AppListState.Content(apps = apps)
            }.onFailure {
                _state.value = AppListState.Error(it.message)
            }
        }
    }

    fun onToolbarClick(message: String) {
        viewModelScope.launch {
            _events.send(AppListEvent.ShowSnackbar(message))
        }
    }
}
