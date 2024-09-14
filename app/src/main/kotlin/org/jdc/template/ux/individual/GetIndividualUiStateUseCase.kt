package org.jdc.template.ux.individual

import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.jdc.template.R
import org.jdc.template.analytics.Analytics
import org.jdc.template.model.domain.inline.IndividualId
import org.jdc.template.model.repository.IndividualRepository
import org.jdc.template.ui.compose.dialog.DialogUiState
import org.jdc.template.ui.compose.dialog.dismissDialog
import org.jdc.template.ui.compose.dialog.showMessageDialog
import org.jdc.template.ui.navigation.NavigationAction
import org.jdc.template.util.ext.stateInDefault
import org.jdc.template.ux.individualedit.IndividualEditRoute
import javax.inject.Inject

class GetIndividualUiStateUseCase
@Inject constructor(
    private val individualRepository: IndividualRepository,
    private val analytics: Analytics,
) {
    private val dialogUiStateFlow = MutableStateFlow<DialogUiState<*>?>(null)

    init {
        analytics.logEvent(Analytics.EVENT_VIEW_INDIVIDUAL)
    }

    operator fun invoke(
        individualId: IndividualId,
        coroutineScope: CoroutineScope,
        navigate: (NavigationAction) -> Unit,
    ): IndividualUiState {
        return IndividualUiState(
            dialogUiStateFlow = dialogUiStateFlow,

            individualFlow = individualRepository.getIndividualFlow(individualId).stateInDefault(coroutineScope, null),

            onEditClick = { editIndividual(individualId, navigate) },
            onDeleteClick = { onDeleteClick(individualId, coroutineScope, navigate) },
            deleteIndividual = { deleteIndividual(individualId, coroutineScope, navigate) },
        )
    }

    private fun onDeleteClick(individualId: IndividualId, coroutineScope: CoroutineScope, navigate: (NavigationAction) -> Unit) {
        showMessageDialog(
            dialogUiStateFlow,
            text = { stringResource(R.string.delete_individual_confirm) },
            onConfirm = { deleteIndividual(individualId, coroutineScope, navigate) },
            onDismiss = { dismissDialog(dialogUiStateFlow) }
        )
    }

    private fun deleteIndividual(individualId: IndividualId, coroutineScope: CoroutineScope, navigate: (NavigationAction) -> Unit) = coroutineScope.launch {
        analytics.logEvent(Analytics.EVENT_DELETE_INDIVIDUAL)
        individualRepository.deleteIndividual(individualId)
        navigate(NavigationAction.Pop())
    }

    private fun editIndividual(individualId: IndividualId, navigate: (NavigationAction) -> Unit) {
        analytics.logEvent(Analytics.EVENT_EDIT_INDIVIDUAL)
        navigate(NavigationAction.Navigate(IndividualEditRoute(individualId)))
    }
}