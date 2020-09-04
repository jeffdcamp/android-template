package org.jdc.template.ux.directory

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import org.jdc.template.model.db.main.directoryitem.DirectoryItem
import org.jdc.template.model.repository.IndividualRepository

class DirectoryViewModel
@ViewModelInject constructor(
    private val individualRepository: IndividualRepository
) : ViewModel() {
    val directoryListFlow: Flow<List<DirectoryItem>> // change to ShareFlow.shareIn(viewModelScope) when available (in coroutine library https://github.com/Kotlin/kotlinx.coroutines/issues/2069)
        get() = individualRepository.getDirectoryListFlow()
}