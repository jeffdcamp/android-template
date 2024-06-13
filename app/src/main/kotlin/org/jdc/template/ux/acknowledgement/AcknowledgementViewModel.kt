package org.jdc.template.ux.acknowledgement

import android.app.Application
import androidx.lifecycle.ViewModel
import co.touchlab.kermit.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.jdc.template.ui.navigation.ViewModelNavigation
import org.jdc.template.ui.navigation.ViewModelNavigationImpl
import javax.inject.Inject

@HiltViewModel
class AcknowledgementViewModel
@Inject constructor(
    private val application: Application
) : ViewModel(), ViewModelNavigation by ViewModelNavigationImpl() {

    private val acknowledgementHtmlStateFlow = MutableStateFlow<String?>(null)

    val uiState = AcknowledgementUiState(
        acknowledgementHtmlFlow = acknowledgementHtmlStateFlow
    )

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