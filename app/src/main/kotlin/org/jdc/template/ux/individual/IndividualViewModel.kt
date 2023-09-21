package org.jdc.template.ux.individual

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.jdc.template.R
import org.jdc.template.analytics.Analytics
import org.jdc.template.model.repository.IndividualRepository
import org.jdc.template.ui.compose.PreviewDefault
import org.jdc.template.ui.compose.dialog.DialogUiState
import org.jdc.template.ui.compose.dialog.dismissDialog
import org.jdc.template.ui.compose.dialog.showMessageDialog
import org.jdc.template.ui.navigation.ViewModelNav
import org.jdc.template.ui.navigation.ViewModelNavImpl
import org.jdc.template.ui.theme.AppTheme
import org.jdc.template.util.ext.requireIndividualId
import org.jdc.template.util.ext.stateInDefault
import org.jdc.template.ux.individualedit.IndividualEditRoute
import javax.inject.Inject

@HiltViewModel
class IndividualViewModel
@Inject constructor(
    private val analytics: Analytics,
    private val individualRepository: IndividualRepository,
//    private val individualCardUiStateModel: IndividualCardUiStateModel,
    savedStateHandle: SavedStateHandle
) : ViewModel(), ViewModelNav by ViewModelNavImpl() {
    private val individualId = savedStateHandle.requireIndividualId(IndividualRoute.Arg.INDIVIDUAL_ID)

    private val dialogUiStateFlow = MutableStateFlow<DialogUiState<*>?>(null)

//    val uiState2: IndividualUiState = individualCardUiStateModel.uiState(viewModelScope) { navigate(it) }
//    val uiState3: IndividualUiState = IndividualCardUiStateModel(individualRepository).uiState(viewModelScope) { navigate(it) }

    val uiState: IndividualUiState = IndividualUiState(
        dialogUiStateFlow = dialogUiStateFlow,

        individualFlow = individualRepository.getIndividualFlow(individualId).stateInDefault(viewModelScope, null),

        onEdit = { editIndividual() },
        onDelete = { onDeleteClicked() },
        deleteIndividual = { deleteIndividual() },
    )

    init {
        analytics.logEvent(Analytics.EVENT_VIEW_INDIVIDUAL)
    }

    private fun onDeleteClicked() {
        showMessageDialog(dialogUiStateFlow, text = { stringResource(R.string.delete_individual_confirm) }, onConfirm = { deleteIndividual() }, onDismiss = { dismissDialog(dialogUiStateFlow) })
    }

    private fun deleteIndividual() = viewModelScope.launch {
        analytics.logEvent(Analytics.EVENT_DELETE_INDIVIDUAL)
        individualRepository.deleteIndividual(individualId)
        popBackStack()
    }

    private fun editIndividual() {
        analytics.logEvent(Analytics.EVENT_EDIT_INDIVIDUAL)
        navigate(IndividualEditRoute.createRoute(individualId))
    }
}

@PreviewDefault
@Composable
private fun Preview() {
    AppTheme {
        Surface {
            Row(modifier = Modifier.fillMaxSize()) {
                OutlinedButton(
                    onClick = { /*TODO*/ }
                ) {
                    Text("View All Hymns 1")
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedButton(
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 8.dp)
                        .height(30.dp),

                    onClick = { /*TODO*/ },
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        top = 4.dp,
                        end = 16.dp,
                        bottom = 4.dp
                    )
                ) {
                    Text("View All Hymns",
                        fontSize = 12.sp
                    )
                }

            }
        }
    }

}


//class IndividualCardUiStateModel
//@Inject constructor(
//    private val individualRepository: IndividualRepository
//) {
//
//
//    fun uiState(
//        coroutineScope: CoroutineScope,
//        navigate: (NavigationAction) -> Unit,
//        setDialogState: (DialogUiState<*>?) -> Unit,
//    ): IndividualUiState {
//        navigate(NavigationAction.Navigate(""))
//
//
//
//
//
//        setDialogState(MessageDialogUiState("Hello"))
//        setDialogState(null)
//
//
//
//
//
//        return IndividualUiState(
//            onDelete = { onDelete(setDialogState) }
//        )
//    }
//
//    fun onDelete(
//        navigate: (NavigationAction) -> Unit,
//        setDialogState: (DialogUiState<*>?) -> Unit
//    ) {
//        setDialogState(MessageDialogUiState({ "Hello" }, onConfirm = {
//            // delete
//            individualRepository.deleteIndividual()
//            navigate(NavigationAction.Pop())
//            setDialogState(null)
//        }))
//    }
//}
//
//
//class IndividualCardUiStateModel2 (
//    application: Application,
//    private val individualRepository: IndividualRepository
//) {
//
//
//    fun uiState(
//        coroutineScope: CoroutineScope,
//        navigate: (NavigationAction) -> Unit,
//        setDialogState: (DialogUiState<*>?) -> Unit,
//    ): IndividualUiState {
//        navigate(NavigationAction.Navigate(""))
//
//
//
//
//
//        setDialogState(MessageDialogUiState("Hello"))
//        setDialogState(null)
//
//
//
//
//
//        return IndividualUiState(
//            onDelete = { onDelete(setDialogState) }
//        )
//    }
//
//    fun onDelete(
//        navigate: (NavigationAction) -> Unit,
//        setDialogState: (DialogUiState<*>?) -> Unit
//    ) {
//        setDialogState(MessageDialogUiState({ "Hello" }, onConfirm = {
//            // delete
//            individualRepository.deleteIndividual()
//            navigate(NavigationAction.Pop())
//            setDialogState(null)
//        }))
//    }
//}
