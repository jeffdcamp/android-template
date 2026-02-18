package org.jdc.template.ui.navigation

import androidx.navigation3.runtime.NavKey
import org.jdc.template.ui.navigation.deeplink.BaseDeepLinkRouter
import org.jdc.template.ui.navigation.deeplink.RouteMatcher
import org.jdc.template.ux.directory.DirectoryRouteMatcher
import org.jdc.template.ux.individual.IndividualRouteMatcher
import org.jdc.template.ux.individualedit.IndividualEditRouteMatcher

object DeepLinkRouter : BaseDeepLinkRouter() {
    override fun getMatchers(): List<RouteMatcher<out NavKey>> = listOf(
        DirectoryRouteMatcher,
        IndividualRouteMatcher,
        IndividualEditRouteMatcher,
    )
}

fun NavKey.toUri(): String = DeepLinkRouter.toUri(this)
fun String.toRoute(): NavKey? = DeepLinkRouter.fromUri(this)
