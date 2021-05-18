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
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.R
import org.jdc.template.databinding.AcknowledgmentsFragmentBinding
import timber.log.Timber

@AndroidEntryPoint
class AcknowledgmentsFragment : Fragment() {
    private lateinit var binding: AcknowledgmentsFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = AcknowledgmentsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.setTitle(R.string.about_title)

        setupWebView()

        loadLicenses()
    }

    private fun setupWebView() {
        binding.webview.webViewClient = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NewAcknowledgmentsWebViewClient()
        } else {
            AcknowledgmentsWebViewClient()
        }
    }

    private fun loadLicenses() {
        // read and load html
        try {
            val htmlFilename = "licenses.html"

            // read the file
            val html = requireContext().assets.open(htmlFilename).bufferedReader().use { it.readText() }

            binding.webview.loadData(html, "text/html", "utf-8")
        } catch (expected: Exception) {
            Timber.e(expected, "Failed to render Acknowledgments html")
            binding.webview.loadData("Failed to load licenses:\n [${expected.message}]", "text/html", "utf-8")
        }
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