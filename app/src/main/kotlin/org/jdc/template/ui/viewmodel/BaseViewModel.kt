package org.jdc.template.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<T> : ViewModel() {

    private val eventChannel = Channel<T>()
    val eventFlow: Flow<T>
        get() { return eventChannel.consumeAsFlow() }

    protected fun sendEvent(event: T) = viewModelScope.launch {
        eventChannel.send(event)
    }

    override fun onCleared() {
        eventChannel.close()
        super.onCleared()
    }
}