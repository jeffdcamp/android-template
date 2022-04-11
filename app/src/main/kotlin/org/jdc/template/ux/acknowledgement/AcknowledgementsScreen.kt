package org.jdc.template.ux.acknowledgement

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.StateFlow
import org.jdc.template.R
import org.jdc.template.ui.compose.LocalNavController
import org.jdc.template.ui.compose.appbar.AppScaffold
import org.jdc.template.ui.navigation.HandleNavigation

@Composable
fun AcknowledgementScreen(viewModel: AcknowledgementViewModel = hiltViewModel()) {
    val navController = LocalNavController.current

    val acknowledgementHtmlFlow = viewModel.acknowledgementHtmlFlow

    TemplateAppScaffoldWithNavBar(
        title = stringResource(R.string.acknowledgments),
        onNavigationClick = { navController.popBackStack() }
    ) {
        AcknowledgementWebview(acknowledgementHtmlFlow)
    }

    HandleNavigation(viewModel, navController, viewModel.navigatorFlow)
}

@Composable
private fun AcknowledgementWebview(acknowledgementHtmlFlow: StateFlow<String?>) {
    val acknowledgementHtml: String? by acknowledgementHtmlFlow.collectAsState()
    val acknowledgementWebViewClient = remember { getWebviewClient() }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                )

                webViewClient = acknowledgementWebViewClient
            }
        },
        update = { webview ->
            acknowledgementHtml?.let {
                webview.loadData(it, "text/html", "utf-8")
            }
        }
    )
}

private fun getWebviewClient(): WebViewClient {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        NewAcknowledgmentsWebViewClient()
    } else {
        AcknowledgmentsWebViewClient()
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
