package org.jdc.template.ux.individual

import androidx.navigation.navDeepLink
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jdc.template.model.domain.inline.IndividualId
import org.jdc.template.ui.navigation.NavTypeMaps
import org.jdc.template.ui.navigation.NavigationRoute
import org.jdc.template.ui.navigation.RouteUtil
import org.jdc.template.ux.NavIntentFilterPart
import kotlin.reflect.typeOf

@Serializable
data class IndividualRoute(
    @SerialName(DeepLinkArgs.PATH_INDIVIDUAL_ID)
    val individualId: IndividualId
): NavigationRoute

fun IndividualRoute.Companion.typeMap() = mapOf(
    typeOf<IndividualId>() to NavTypeMaps.IndividualIdNavType,
)

fun IndividualRoute.Companion.deepLinks() = listOf(
    // Deep link with path/query arguments by using uriPattern
    // (don't use generated path in order to maintain deep link contract with other apps)
    // ./adb shell am start -W -a android.intent.action.VIEW -d "android-template://individual/xxxx"
    navDeepLink {
        uriPattern = "${NavIntentFilterPart.DEFAULT_APP_SCHEME}://individual/${RouteUtil.defineArg(DeepLinkArgs.PATH_INDIVIDUAL_ID)}"
    },
)

private object DeepLinkArgs {
    const val PATH_INDIVIDUAL_ID = "individualId"
}
