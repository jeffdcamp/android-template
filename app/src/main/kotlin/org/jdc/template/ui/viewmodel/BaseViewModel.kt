package org.jdc.template.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import org.jdc.template.util.defaultScope

abstract class BaseViewModel : ViewModel(), CoroutineScope by defaultScope() {

    override fun onCleared() {
        cancel()
        super.onCleared()
    }
}