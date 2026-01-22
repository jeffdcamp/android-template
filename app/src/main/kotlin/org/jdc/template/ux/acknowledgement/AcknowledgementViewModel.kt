package org.jdc.template.ux.acknowledgement

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import org.jdc.template.shared.util.ext.stateInDefault
import org.jdc.template.ui.navigation3.ViewModelNavigation3
import org.jdc.template.ui.navigation3.ViewModelNavigation3Impl

class AcknowledgementViewModel(
    private val application: Application
) : ViewModel(), ViewModelNavigation3 by ViewModelNavigation3Impl() {
    private val acknowledgementHtmlStateFlow = MutableStateFlow<String?>(null)

    val uiStateFlow: StateFlow<AcknowledgementUiState> = acknowledgementHtmlStateFlow.map { acknowledgementHtml: String? ->
        if (!acknowledgementHtml.isNullOrBlank()) {
            AcknowledgementUiState.Ready(acknowledgementHtml)
        } else {
            AcknowledgementUiState.Ready("")
        }
    }.stateInDefault(viewModelScope, AcknowledgementUiState.Loading)

    init {
        loadLicenses()
    }

    private fun loadLicenses() {
        try {
            val htmlFilename = "licenses.html"

            // read the file
            acknowledgementHtmlStateFlow.value = application.assets.open(htmlFilename).bufferedReader().use { it.readText() }
        } catch (expected: Exception) {
            Logger.e(expected) { "Failed to render Acknowledgments html" }
            acknowledgementHtmlStateFlow.value = "Failed to load licenses:\n [${expected.message}]"
        }
    }
}

sealed interface AcknowledgementUiState {
    data object Loading : AcknowledgementUiState

    data class Ready(
        val acknowledgementHtml: String,
    ) : AcknowledgementUiState
}
