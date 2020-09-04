package org.jdc.template.ux.about

import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.R
import org.jdc.template.ui.compose.setContent

@AndroidEntryPoint
class AboutFragment : Fragment() {
    private val viewModel: AboutViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return setContent {
            AboutPage()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.logAnalytics()
    }
}

@Composable
fun AboutPage() {
    val viewModel: AboutViewModel = viewModel()
    val restEnabled = viewModel.resetServiceEnabledFlow.collectAsState(initial = null)
    ConstraintLayout {
        val (appNameTextView, versionTextView, versionDateTextView, createDatabaseButton, restTestButton,
            work1TestButton, work2TestButton, testTableChangeButton, testButton,
            restServiceLabelTextView, restServiceEnabledTextView) = createRefs()
        Text(text = stringResource(R.string.app_name), style = MaterialTheme.typography.h4, modifier = Modifier.constrainAs(appNameTextView) {
            top.linkTo(parent.top, margin = 8.dp)
            centerHorizontallyTo(parent)
        })
        Text(text = viewModel.appVersion, style = MaterialTheme.typography.body1, modifier = Modifier.constrainAs(versionTextView) {
            top.linkTo(appNameTextView.bottom, margin = 4.dp)
            end.linkTo(appNameTextView.end)
        })
        val dateText = DateUtils.formatDateTime(ContextAmbient.current, viewModel.appBuildDateTime, DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_TIME or DateUtils.FORMAT_SHOW_YEAR)
        Text(text = dateText, style = MaterialTheme.typography.body1, modifier = Modifier.constrainAs(versionDateTextView) {
            top.linkTo(versionTextView.bottom, margin = 4.dp)
            end.linkTo(versionTextView.end)
        })
        Button(onClick = { viewModel.createSampleDataWithInjection() }, content = { Text("Create Database") }, modifier = Modifier.constrainAs(createDatabaseButton) {
            top.linkTo(versionDateTextView.bottom, margin = 16.dp)
            centerHorizontallyTo(parent)
        })
        Button(onClick = { viewModel.testQueryWebServiceCall() }, content = { Text("Test REST Call") }, modifier = Modifier.constrainAs(restTestButton) {
            top.linkTo(restServiceLabelTextView.bottom, margin = 16.dp)
            centerHorizontallyTo(parent)
        })
        Button(onClick = { viewModel.workManagerSimpleTest() }, content = { Text("Test Simple Work") }, modifier = Modifier.constrainAs(work1TestButton) {
            top.linkTo(restTestButton.bottom, margin = 16.dp)
            centerHorizontallyTo(parent)
        })
        Button(onClick = { viewModel.workManagerSyncTest() }, content = { Text("Test Sync Work") }, modifier = Modifier.constrainAs(work2TestButton) {
            top.linkTo(work1TestButton.bottom, margin = 16.dp)
            centerHorizontallyTo(parent)
        })
        Button(onClick = { viewModel.testTableChange() }, content = { Text("Test Table Change") }, modifier = Modifier.constrainAs(testTableChangeButton) {
            top.linkTo(work2TestButton.bottom, margin = 16.dp)
            centerHorizontallyTo(parent)
        })
        Button(onClick = { viewModel.testStuff() }, content = { Text("Test") }, modifier = Modifier.constrainAs(testButton) {
            top.linkTo(testTableChangeButton.bottom, margin = 16.dp)
            centerHorizontallyTo(parent)
        })
        Text(text = "Rest Services Enabled:", style = MaterialTheme.typography.body1, modifier = Modifier.constrainAs(restServiceLabelTextView) {
            top.linkTo(createDatabaseButton.bottom, margin = 16.dp)
            start.linkTo(parent.start)
            end.linkTo(restServiceEnabledTextView.start)
        })
        val enabledState = when (restEnabled.value) {
            null -> "Unknown"
            true -> "True"
            false -> "False"
        }
        Text(text = enabledState, style = MaterialTheme.typography.body1, modifier = Modifier.constrainAs(restServiceEnabledTextView) {
            top.linkTo(restServiceLabelTextView.top)
            start.linkTo(restServiceLabelTextView.end, margin = 8.dp)
            end.linkTo(parent.end)
        })
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAbout() {
    AboutPage()
}