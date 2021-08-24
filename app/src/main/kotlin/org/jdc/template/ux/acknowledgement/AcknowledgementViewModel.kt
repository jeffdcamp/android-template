package org.jdc.template.ux.acknowledgement

import android.app.Application
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AcknowledgementViewModel
@Inject constructor(
    private val application: Application
) : ViewModel() {

    private val _acknowledgementHtmlStateFlow = MutableStateFlow<String?>(null)
    val acknowledgementHtmlFlow: StateFlow<String?> = _acknowledgementHtmlStateFlow

    init {
        loadLicenses()
    }

    private fun loadLicenses() {
        try {
            val htmlFilename = "licenses.html"

            // read the file
            _acknowledgementHtmlStateFlow.value = application.assets.open(htmlFilename).bufferedReader().use { it.readText() }
        } catch (expected: Exception) {
            Timber.e(expected, "Failed to render Acknowledgments html")
            _acknowledgementHtmlStateFlow.value = "Failed to load licenses:\n [${expected.message}]"
        }
    }
}