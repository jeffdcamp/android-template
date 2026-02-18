package org.jdc.template.ux.individual

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import org.jdc.template.shared.model.domain.inline.IndividualId
import org.jdc.template.shared.util.network.Uri
import org.jdc.template.shared.util.network.toUri
import org.jdc.template.ui.navigation.deeplink.RouteMatcher

@Serializable
data class IndividualRoute(
    val individualId: IndividualId
): NavKey

object IndividualRouteMatcher : RouteMatcher<IndividualRoute>("/individual".toUri()) {
    override fun matchesKey(route: NavKey) = route is IndividualRoute

    override fun parse(uri: Uri): IndividualRoute? {
        uri.pathSegments
        return IndividualRoute(
            individualId = uri.pathSegments.getOrNull(1)?.let { IndividualId(it) } ?: return null
        )
    }

    override fun toUri(route: IndividualRoute): String {
        return baseUri.newBuilder()
            .appendPath(route.individualId.value)
            .build().toString()
    }
}
