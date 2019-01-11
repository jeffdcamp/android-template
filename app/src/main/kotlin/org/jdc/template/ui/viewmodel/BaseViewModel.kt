package org.jdc.template.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import org.jdc.template.util.DefaultScope

abstract class BaseViewModel : ViewModel(), CoroutineScope by DefaultScope() {

    override fun onCleared() {
        cancel()
        super.onCleared()
    }
}