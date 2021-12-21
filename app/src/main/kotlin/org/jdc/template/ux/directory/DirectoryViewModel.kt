package org.jdc.template.ux.directory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import org.jdc.template.model.db.main.directoryitem.DirectoryItem
import org.jdc.template.model.repository.IndividualRepository
import org.jdc.template.ui.ViewModelNav
import org.jdc.template.ui.ViewModelNavImpl
import org.jdc.template.util.ext.stateInDefault
import org.jdc.template.ux.individual.IndividualRoute
import org.jdc.template.ux.individualedit.IndividualEditRoute
import javax.inject.Inject

@HiltViewModel
class DirectoryViewModel
@Inject constructor(
    individualRepository: IndividualRepository
) :  ViewModel(), ViewModelNav by ViewModelNavImpl() {
    val directoryListFlow: StateFlow<List<DirectoryItem>> = individualRepository.getDirectoryListFlow().stateInDefault(viewModelScope, emptyList())

    fun addIndividual() {
        navigate(IndividualEditRoute.createRoute())
    }

    fun onDirectoryIndividualClicked(directoryListItem: DirectoryItem) {
        navigate(IndividualRoute.createRoute(directoryListItem.individualId))
    }
}