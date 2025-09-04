package org.jdc.template.ux.individualedit

import androidx.navigation3.runtime.NavKey
import org.jdc.template.shared.model.domain.inline.IndividualId

data class IndividualEditRoute(
    val individualId: IndividualId? = null
): NavKey
