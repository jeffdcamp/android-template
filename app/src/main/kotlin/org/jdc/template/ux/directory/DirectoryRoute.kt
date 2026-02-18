package org.jdc.template.ux.directory

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import org.jdc.template.shared.util.network.toUri
import org.jdc.template.ui.navigation.deeplink.SimpleRouteMatcher

@Serializable
object DirectoryRoute: NavKey

object DirectoryRouteMatcher : SimpleRouteMatcher<DirectoryRoute>(DirectoryRoute, "/directory".toUri())
