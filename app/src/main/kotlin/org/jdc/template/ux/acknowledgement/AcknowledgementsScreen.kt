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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jdc.template.R
import org.jdc.template.ui.navigation.HandleNavigation3
import org.jdc.template.ui.navigation.navigator.Navigation3Navigator
import org.jdc.template.ux.MainAppScaffoldWithNavBar

@Composable
fun AcknowledgementScreen(
    navigator: Navigation3Navigator,
    viewModel: AcknowledgementViewModel
) {
    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()

    MainAppScaffoldWithNavBar(
        navigator = navigator,
        title = stringResource(R.string.acknowledgments),
        onNavigationClick = { navigator.pop() }
    ) {
        when (val uiState = uiState) {
            AcknowledgementUiState.Loading -> {}
            is AcknowledgementUiState.Ready -> AcknowledgementWebview(uiState.acknowledgementHtml)
        }
    }

    HandleNavigation3(viewModel, navigator)
}

@Composable
private fun AcknowledgementWebview(acknowledgementHtml: String) {
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
            webview.loadData(acknowledgementHtml, "text/html", "utf-8")
        }
    )
}

private fun getWebviewClient(): WebViewClient {
    return if (Build.VERSION.SDK_INT >= 24) {
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
