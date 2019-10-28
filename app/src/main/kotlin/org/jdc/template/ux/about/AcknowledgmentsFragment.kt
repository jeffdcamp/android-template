package org.jdc.template.ux.about

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.intellij.lang.annotations.Language
import org.jdc.template.R
import org.jdc.template.databinding.AcknowledgmentsFragmentBinding
import timber.log.Timber

class AcknowledgmentsFragment : Fragment() {

    private lateinit var binding: AcknowledgmentsFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.acknowledgments_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = this@AcknowledgmentsFragment
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.setTitle(R.string.about_title)

        setupWebview()

        loadLicenses()
    }

    private fun setupWebview() {
        binding.webview.webViewClient = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NewAcknowledgmentsWebViewClient()
        } else {
            AcknowledgmentsWebViewClient()
        }
    }

    private fun loadLicenses() {
        // read and load html
        try {
            val jsonFilename = "licenses.json"

            // read the file
            val json = requireContext().assets.open(jsonFilename).bufferedReader().use { it.readText() }

            val licensesDto = Json.nonstrict.parse(LicensesDto.serializer(), json)

            val html = renderHtml(licensesDto.dependencies)
            binding.webview.loadData(html, "text/html", "utf-8")
        } catch (e: Exception) {
            Timber.e(e, "Failed to render Acknowledgments html")
            binding.webview.loadData("Failed to load licenses:\n [$e]", "text/html", "utf-8")
        }
    }

    private fun renderHtml(dependencies: List<LicenseDto>): String {
        val dependenciesHtml = StringBuffer()
        val notAvailableText = getString(R.string.not_available)
        dependencies.forEach { dependency ->
            @Language("HTML")
            val dependencyHtml = """
                <p>
                    <strong>${dependency.moduleName ?: notAvailableText}</strong><br/>
                    <strong>URL: </strong>${if (dependency.moduleUrl != null) "<a href='${dependency.moduleUrl}'>${dependency.moduleUrl}</a>" else notAvailableText} <br/>
                    <strong>License: </strong>${dependency.moduleLicense ?: notAvailableText} - ${if (dependency.moduleLicenseUrl != null) "<a href='${dependency.moduleLicenseUrl}'>${dependency.moduleLicenseUrl}" else notAvailableText}</a>
                </p>
                <hr/>
            """.trimIndent()

            dependenciesHtml.append(dependencyHtml)
        }

        @Suppress("UnnecessaryVariable")
        @Language("HTML")
        val html = """
                <html>
                    <style>
                        a { word-wrap: break-word;}
                        strong { word-wrap: break-word;}
                    </style>
                    <body>
                        $dependenciesHtml
                    </body>
                </html>
        """.trimIndent()

        return html
    }
}

private class AcknowledgmentsWebViewClient : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
        view.context.startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(url))
        )

        return true
    }
}

@RequiresApi(Build.VERSION_CODES.N)
private class NewAcknowledgmentsWebViewClient : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest?): Boolean {
        val url = request?.url?.toString() ?: return super.shouldOverrideUrlLoading(view, request)

        view.context.startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(url))
        )
        return true
    }
}

@Serializable
private data class LicenseDto(
        val moduleName: String? = null,
        val moduleUrl: String? = null,
        val moduleVersion: String? = null,
        val moduleLicense: String? = null,
        val moduleLicenseUrl: String? = null
)

@Serializable
private data class LicensesDto(val dependencies: List<LicenseDto>)