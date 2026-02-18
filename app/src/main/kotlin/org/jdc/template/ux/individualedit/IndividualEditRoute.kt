package org.jdc.template.ux.individualedit

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import org.jdc.template.shared.model.domain.inline.IndividualId
import org.jdc.template.shared.util.network.Uri
import org.jdc.template.shared.util.network.toUri
import org.jdc.template.ui.navigation.deeplink.RouteMatcher

@Serializable
data class IndividualEditRoute(
    val individualId: IndividualId? = null
): NavKey

object IndividualEditRouteMatcher : RouteMatcher<IndividualEditRoute>("/individual/edit".toUri()) {
    override fun matchesKey(route: NavKey) = route is IndividualEditRoute

    override fun parse(uri: Uri): IndividualEditRoute? {
        uri.pathSegments
        return IndividualEditRoute(
            individualId = uri.pathSegments.getOrNull(1)?.let { IndividualId(it) } ?: return null
        )
    }

    override fun toUri(route: IndividualEditRoute): String {
        return when (route.individualId) {
            null -> baseUri.toString()
            else -> baseUri.newBuilder()
                .appendPath(route.individualId.value)
                .build().toString()
        }
    }
}
