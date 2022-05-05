package org.jdc.template.ux.acknowledgement

import android.app.Application
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.jdc.template.ui.navigation.ViewModelNav
import org.jdc.template.ui.navigation.ViewModelNavImpl
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AcknowledgementViewModel
@Inject constructor(
    private val application: Application
) : ViewModel(), ViewModelNav by ViewModelNavImpl() {

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
            Timber.e(expected, "Failed to render Acknowledgments html")
            acknowledgementHtmlStateFlow.value = "Failed to load licenses:\n [${expected.message}]"
        }
    }
}