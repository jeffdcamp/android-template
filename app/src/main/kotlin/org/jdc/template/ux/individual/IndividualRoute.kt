package org.jdc.template.ux.individual

import androidx.navigation3.runtime.NavKey
import org.jdc.template.shared.model.domain.inline.IndividualId

data class IndividualRoute(
    val individualId: IndividualId
): NavKey

//fun IndividualRoute.Companion.deepLinks() = listOf(
//    // Deep link with path/query arguments by using uriPattern
//    // (don't use generated path in order to maintain deep link contract with other apps)
//    // ./adb shell am start -W -a android.intent.action.VIEW -d "android-template://individual/xxxx"
//    navDeepLink {
//        uriPattern = "${NavIntentFilterPart.DEFAULT_APP_SCHEME}://individual/${RouteUtil.defineArg(DeepLinkArgs.PATH_INDIVIDUAL_ID)}"
//    },
//)
//
//private object DeepLinkArgs {
//    const val PATH_INDIVIDUAL_ID = "individualId"
//}
