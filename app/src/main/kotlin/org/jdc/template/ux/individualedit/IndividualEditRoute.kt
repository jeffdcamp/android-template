package org.jdc.template.ux.individualedit

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import org.jdc.template.shared.model.domain.inline.IndividualId

@Serializable
data class IndividualEditRoute(
    val individualId: IndividualId? = null
): NavKey
