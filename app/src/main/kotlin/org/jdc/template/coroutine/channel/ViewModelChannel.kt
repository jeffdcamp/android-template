package org.jdc.template.coroutine.channel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.launch

class ViewModelChannel<E>(
    private val viewModel: ViewModel,
    private val channel: Channel<E> = Channel()
) : ReceiveChannel<E> by channel, SendChannel<E> by channel {
    fun sendAsync(element: E) = viewModel.viewModelScope.launch {
        channel.send(element)
    }
}