package org.jdc.template.domain.individual

import app.cash.turbine.turbineScope
import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.jdc.template.domain.TestIndividuals
import org.jdc.template.domain.mockIndividualRepository
import org.jdc.template.ui.compose.dialog.MessageDialogUiState
import org.jdc.template.ui.navigation.NavigationAction
import org.jdc.template.ux.individualedit.IndividualEditRoute
import org.junit.jupiter.api.Test

class IndividualUseCaseTest {
    @Test
    fun testNavigation() = runTest {
        turbineScope {
            val useCase = IndividualUseCase(mockIndividualRepository(), mockk(relaxed = true))
            val stateScope = CoroutineScope(Job())
            val lastNavigationActionFlow = MutableStateFlow<NavigationAction?>(null)
            val navigationActionTurbine = lastNavigationActionFlow.testIn(stateScope)
            navigationActionTurbine.awaitItem() // consume default value

            val selectedIndividualId = TestIndividuals.individualId1
            val uiState = useCase(
                individualId = selectedIndividualId,
                coroutineScope = stateScope,
                navigate = { lastNavigationActionFlow.value = it }
            )

            val dialogUiStateFlowTurbine = uiState.dialogUiStateFlow.testIn(stateScope)
            dialogUiStateFlowTurbine.awaitItem() // consume default value

            uiState.onEdit()
            assertThat(navigationActionTurbine.awaitItem()).isEqualTo(NavigationAction.Navigate(IndividualEditRoute.createRoute(selectedIndividualId)))

            uiState.onDelete()
            val deleteDialog = dialogUiStateFlowTurbine.awaitItem()
            if (deleteDialog is MessageDialogUiState) {
                deleteDialog.onConfirm?.invoke(Unit)
            } else {
                error("Expected error Delete MessageDialogUiState")
            }

            assertThat(navigationActionTurbine.awaitItem()).isEqualTo(NavigationAction.Pop())

            stateScope.cancel()
        }
    }

    @Test
    fun testDelete() = runTest {
        turbineScope {
            val individualRepository = mockIndividualRepository()
            val useCase = IndividualUseCase(individualRepository, mockk(relaxed = true))
            val stateScope = CoroutineScope(Job())
            val lastNavigationActionFlow = MutableStateFlow<NavigationAction?>(null)
            val navigationActionTurbine = lastNavigationActionFlow.testIn(stateScope)
            navigationActionTurbine.awaitItem() // consume default value

            val selectedIndividualId = TestIndividuals.individualId1
            val uiState = useCase(
                individualId = selectedIndividualId,
                coroutineScope = stateScope,
                navigate = { lastNavigationActionFlow.value = it }
            )

            val dialogUiStateFlowTurbine = uiState.dialogUiStateFlow.testIn(stateScope)
            dialogUiStateFlowTurbine.awaitItem() // consume default value

            uiState.onDelete()
            val deleteDialog = dialogUiStateFlowTurbine.awaitItem()
            if (deleteDialog is MessageDialogUiState) {
                // test cancel pressed (no delete)
                deleteDialog.onDismiss?.invoke()
                coVerify(exactly = 0) { individualRepository.deleteIndividual(any()) }

                // test OK pressed (delete)
                deleteDialog.onConfirm?.invoke(Unit)
                coVerify(exactly = 1) { individualRepository.deleteIndividual(any()) }
            } else {
                error("Expected error Delete MessageDialogUiState")
            }

            stateScope.cancel()
        }
    }
}