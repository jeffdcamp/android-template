package org.jdc.template.ux.directory

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotEqualTo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.jdc.template.BaseCoroutineTest
import org.jdc.template.shared.model.db.main.directoryitem.DirectoryItemEntityView
import org.jdc.template.shared.model.domain.inline.FirstName
import org.jdc.template.shared.model.domain.inline.IndividualId
import org.jdc.template.shared.model.domain.inline.LastName
import org.jdc.template.shared.model.repository.IndividualRepository
import org.jdc.template.ui.navigation3.Navigation3Action
import org.jdc.template.ux.individualedit.IndividualEditRoute
import kotlin.test.Test

class DirectoryViewModelTest: BaseCoroutineTest() {
    @Test
    fun `when uiState is initialized, then is should show Loading`() {
        val viewModel = DirectoryViewModel(createIndividualRepository(emptyList()))
        assertThat(viewModel.uiStateFlow.value).isEqualTo(DirectoryUiState.Loading)
    }

    @Test
    fun `when uiState is finished loading with an Empty List`() = runTest(testDispatcher) {
        val viewModel = DirectoryViewModel(createIndividualRepository(emptyList()))
        backgroundScope.launch(testDispatcher) { viewModel.uiStateFlow.collect() }

        assertThat(viewModel.uiStateFlow.value).isEqualTo(DirectoryUiState.Empty)
    }

    @Test
    fun `when uiState is finished loading with a single item`() = runTest(testDispatcher) {
        val list = listOf(
            DirectoryItemEntityView(
                individualId = IndividualId("123"),
                firstName = FirstName("Jeff"),
                lastName = LastName("Campbell")
            )
        )

        val viewModel = DirectoryViewModel(createIndividualRepository(list))

        backgroundScope.launch(testDispatcher) { viewModel.uiStateFlow.collect() }
        assertThat(viewModel.uiStateFlow.value).isNotEqualTo(DirectoryUiState.Empty)
    }

    @Test
    fun `when onNewClick is called, then it should navigate to IndividualEditRoute`() {
        val viewModel = DirectoryViewModel(createIndividualRepository(emptyList()))

        viewModel.onNewClick()
        assertThat(viewModel.navigationActionFlow.value).isEqualTo(Navigation3Action.Navigate(IndividualEditRoute()))
    }

    private fun createIndividualRepository(list: List<DirectoryItemEntityView>): IndividualRepository {
        val individualRepository = mockk<IndividualRepository>()
        coEvery { individualRepository.getDirectoryListFlow() } returns flowOf(list)

        return individualRepository
    }
}