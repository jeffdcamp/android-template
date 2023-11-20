package org.jdc.template.ux.individualedit

import app.cash.turbine.turbineScope
import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.jdc.template.ui.navigation.NavigationAction
import org.jdc.template.ux.TestIndividuals
import org.jdc.template.ux.mockApplication
import org.jdc.template.ux.mockIndividualRepository
import org.junit.jupiter.api.Test

class GetIndividualEditUiStateUseCaseTest {
    @Test
    fun testNavigation() = runTest {
        turbineScope {
            val useCase = GetIndividualEditUiStateUseCase(
                application = mockApplication(),
                individualRepository = mockIndividualRepository(),
                analytics = mockk(relaxed = true)
            )
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

            uiState.onSaveIndividualClick()
            assertThat(navigationActionTurbine.awaitItem()).isEqualTo(NavigationAction.Pop())

            stateScope.cancel()
        }
    }
}