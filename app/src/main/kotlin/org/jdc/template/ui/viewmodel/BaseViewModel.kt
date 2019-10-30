package org.jdc.template.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch

abstract class BaseViewModel<T> : ViewModel() {

    private val _eventChannel = Channel<T>()
    val eventChannel: ReceiveChannel<T> = _eventChannel

    protected fun sendEvent(event: T) = viewModelScope.launch {
        _eventChannel.send(event)
    }

    override fun onCleared() {
        _eventChannel.close()
        super.onCleared()
    }
}